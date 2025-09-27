package droids;

/**
 * Клас танка-дроїда з високим HP та спеціальними здібностями:
 * - Блокування атак (25% шанс зменшити урон наполовину)
 * - Провокація (може змусити ворога атакувати себе - для командних боїв)
 */
public class TankDroid extends Droid {
    private static final double BLOCK_CHANCE = 0.25; // 25% шанс блокувати атаку
    private static final double BLOCK_REDUCTION = 0.5; // Зменшує урон наполовину
    
    public TankDroid(String name) {
        // Характеристики згідно з планом: високе HP, низький урон, середня точність, низька швидкість
        super(name, 225, 20, 0.75, 9);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // У танка немає спеціальних атакуючих здібностей
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Перевіряємо блокування
        if (random.nextDouble() < BLOCK_CHANCE) {
            int blockedDamage = (int) (incomingDamage * BLOCK_REDUCTION);
            System.out.println(name + " БЛОКУЄ атаку! Урон зменшено з " + incomingDamage + " до " + blockedDamage);
            return blockedDamage;
        }
        
        return incomingDamage;
    }
    
    /**
     * Спеціальна здібність провокації (для командних боїв)
     * Примушує ворога атакувати цього танка
     */
    public void provoke(Droid enemy) {
        System.out.println(name + " провокує " + enemy.getName() + "! Наступна атака буде спрямована на танка!");
        // Логіка провокації буде реалізована в системі бою
    }
    
    /**
     * Перевіряє, чи може танк використати провокацію
     */
    public boolean canProvoke() {
        // Провокацію можна використовувати, якщо танк живий і має достатньо HP
        return isAlive() && health > maxHealth * 0.2; // Потребує мінімум 20% HP
    }
    
    @Override
    public String getInfo() {
        String baseInfo = super.getInfo();
        StringBuilder additionalInfo = new StringBuilder(baseInfo);
        
        additionalInfo.append("\n  Спеціальні здібності:");
        additionalInfo.append(String.format("\n  - Блокування: %.0f%% шанс зменшити урон на %.0f%%", 
                            BLOCK_CHANCE * 100, (1 - BLOCK_REDUCTION) * 100));
        additionalInfo.append("\n  - Провокація: примушує ворогів атакувати себе");
        
        if (canProvoke()) {
            additionalInfo.append("\n  🛡️ Готовий до провокації!");
        }
        
        return additionalInfo.toString();
    }
}