package droids;

/**
 * Клас танка-дроїда з високим HP та спеціальними здібностями:
 * - Блокування атак (25% шанс зменшити урон наполовину)
 * - Провокація (може змусити ворога атакувати себе - для командних боїв)
 */
public class TankDroid extends Droid {
    private static final double BLOCK_CHANCE = 0.25; // 25% шанс блокувати атаку
    private static final double BLOCK_REDUCTION = 0.5; // Зменшує урон наполовину
    
    /**
     * Створює новий екземпляр танк-дроїда з заданим ім'ям
     * Характеристики: низький урон (20), дуже високе HP (225), середня точність (0.75), низька швидкість (9)
     * @param name ім'я дроїда
     */
    public TankDroid(String name) {
        // Характеристики згідно з планом: високе HP, низький урон, середня точність, низька швидкість
        super(name, 225, 20, 0.75, 9);
    }
    
    /**
     * Застосовує спеціальні атакуючі здібності танка
     * У танка немає спеціальних атакуючих здібностей
     * @param damage базовий урон
     * @param target ціль атаки
     * @return незмінений урон
     */
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // У танка немає спеціальних атакуючих здібностей
        return damage;
    }
    
    /**
     * Застосовує спеціальні захисні здібності танка
     * Має 25% шанс заблокувати атаку та зменшити урон наполовину
     * @param incomingDamage урон, що надходить
     * @return зменшений урон при блокуванні або повний урон
     */
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Перевіряємо блокування
        if (random.nextDouble() < BLOCK_CHANCE) {
            int reducedDamage = (int) (incomingDamage * BLOCK_REDUCTION);
            System.out.println(name + " БЛОКУЄ атаку! Урон зменшено з " + incomingDamage + " до " + reducedDamage);
            return reducedDamage;
        }
        
        return incomingDamage;
    }
    
    /**
     * Спеціальна здібність провокації для командних боїв
     * Примушує всіх ворогів в команді атакувати цього танка
     * @param enemies список ворожих дроїдів
     */
    public void provoke(java.util.List<Droid> enemies) {
        System.out.println(name + " використовує ПРОВОКАЦІЮ! Всі вороги тепер атакуватимуть танка!");
        // Логіка провокації буде реалізована в системі командних боїв
    }
    
    /**
     * Перевіряє, чи може танк використати провокацію
     * @return true якщо танк може використати провокацію
     */
    public boolean canProvoke() {
        // Провокацію можна використовувати, якщо танк живий і має достатньо HP
        return isAlive() && health > maxHealth * 0.2; // Потребує мінімум 20% HP
    }
    
    /**
     * Отримує розширену інформацію про танк-дроїда включаючи спеціальні здібності
     * @return детальна інформація про дроїда та його здібності
     */
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