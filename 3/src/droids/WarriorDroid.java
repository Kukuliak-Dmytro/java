package droids;

/**
 * Клас воїна-дроїда з високим уроном та спеціальними здібностями:
 * - Критичний удар (15% шанс подвійного урону)
 * - Берсерк (при HP < 30% отримує +50% урону)
 */
public class WarriorDroid extends Droid {
    private static final double CRIT_CHANCE = 0.15; // 15% шанс критичного удару
    private static final double BERSERK_THRESHOLD = 0.3; // При 30% HP
    private static final double BERSERK_DAMAGE_BONUS = 0.5; // +50% урону
    
    public WarriorDroid(String name) {
        // Характеристики згідно з планом: високий урон, середнє HP, висока точність, середня швидкість
        super(name, 135, 35, 0.85, 13);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        int finalDamage = damage;
        
        // Перевіряємо берсерк (при низькому HP)
        if (health < maxHealth * BERSERK_THRESHOLD) {
            finalDamage = (int) (finalDamage * (1 + BERSERK_DAMAGE_BONUS));
            System.out.println(name + " входить в БЕРСЕРК! (+50% урону)");
        }
        
        // Перевіряємо критичний удар
        if (random.nextDouble() < CRIT_CHANCE) {
            finalDamage *= 2;
            System.out.println(name + " завдає КРИТИЧНИЙ УДАР! (урон подвоєний)");
        }
        
        return finalDamage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // У воїна немає спеціальних захисних здібностей
        return incomingDamage;
    }
    
    /**
     * Перевіряє, чи активний режим берсерка
     */
    public boolean isBerserk() {
        return health < maxHealth * BERSERK_THRESHOLD;
    }
    
    @Override
    public String getInfo() {
        String baseInfo = super.getInfo();
        StringBuilder additionalInfo = new StringBuilder(baseInfo);
        
        additionalInfo.append("\n  Спеціальні здібності:");
        additionalInfo.append(String.format("\n  - Критичний удар: %.0f%% шанс подвійного урону", CRIT_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Берсерк: +%.0f%% урону при HP < %.0f%%", 
                            BERSERK_DAMAGE_BONUS * 100, BERSERK_THRESHOLD * 100));
        
        if (isBerserk()) {
            additionalInfo.append("\n  🔥 РЕЖИМ БЕРСЕРКА АКТИВНИЙ!");
        }
        
        return additionalInfo.toString();
    }
}