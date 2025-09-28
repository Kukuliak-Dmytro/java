package droids;

import effects.PoisonEffect;

/**
 * Клас асасина-дроїда з високим уроном, низьким HP та спеціальними здібностями:
 * - Уникнення атак (20% шанс повністю уникнути урону)
 * - Отруєння (атаки завдають додатковий урон протягом 3 ходів)
 */
public class AssassinDroid extends Droid {
    private static final double DODGE_CHANCE = 0.20; // 20% шанс уникнути атаку
    private static final double POISON_CHANCE = 0.30; // 30% шанс отруїти ціль
    private static final int POISON_DAMAGE = 10; // Урон від отрути за хід
    private static final int POISON_DURATION = 3; // Тривалість отрути
    
    /**
     * Створює новий екземпляр асасина-дроїда з заданим ім'ям
     * Характеристики: дуже високий урон (50), низьке HP (90), дуже висока точність (0.95), дуже висока швидкість (22)
     * @param name ім'я дроїда
     */
    public AssassinDroid(String name) {
        // Характеристики згідно з планом: високий урон, низьке HP, висока точність, висока швидкість
        super(name, 90, 50, 0.95, 22);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // Перевіряємо отруєння
        if (random.nextDouble() < POISON_CHANCE) {
            PoisonEffect poison = new PoisonEffect(POISON_DURATION, POISON_DAMAGE);
            target.applyEffect(poison);
            System.out.println(name + " ОТРУЮЄ " + target.getName() + "!");
        }
        
        return damage;
    }
    
    /**
     * Застосовує спеціальні захисні здібності асасина
     * Має 20% шанс повністю уникнути атаки
     * @param incomingDamage урон, що надходить
     * @return 0 якщо атака була уникнена, інакше повний урон
     */
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Перевіряємо уникнення
        if (random.nextDouble() < DODGE_CHANCE) {
            System.out.println(name + " УНИКАЄ атаки! Урон повністю заблоковано!");
            return 0; // Повністю уникає урону
        }
        
        return incomingDamage;
    }
    
    /**
     * Спеціальна атака з підвищеним шансом отруєння
     */
    public void venomStrike(Droid target) {
        System.out.println(name + " використовує Отруйний Удар!");
        
        // Розраховуємо урон з новою системою точності
        int baseDamage = getModifiedDamage();
        
        // Для спеціальної атаки використовуємо підвищену точність
        double enhancedAccuracy = Math.min(getModifiedAccuracy() + 0.1, 0.98); // +10% точності, але не більше 98%
        double accuracyModifier = enhancedAccuracy + (1.0 - enhancedAccuracy) * random.nextDouble();
        int finalDamage = (int) Math.round(baseDamage * accuracyModifier);
        
        // Гарантоване отруєння при спеціальній атаці
        PoisonEffect poison = new PoisonEffect(POISON_DURATION + 1, POISON_DAMAGE + 5);
        target.applyEffect(poison);
        
        int actualDamage = target.takeDamage(finalDamage);
        System.out.println(name + " завдає " + actualDamage + " урону та СИЛЬНО ОТРУЮЄ " + target.getName() + "! (Точність: " + Math.round(accuracyModifier * 100) + "%)");
    }
    
    /**
     * Перевіряє, чи може асасин використати спеціальну атаку
     */
    public boolean canUseVenomStrike() {
        // Спеціальну атаку можна використовувати раз на 3 ходи (спрощена логіка)
        return isAlive();
    }
    
    @Override
    public String getInfo() {
        String baseInfo = super.getInfo();
        StringBuilder additionalInfo = new StringBuilder(baseInfo);
        
        additionalInfo.append("\n  Спеціальні здібності:");
        additionalInfo.append(String.format("\n  - Уникнення: %.0f%% шанс повністю уникнути урону", 
                            DODGE_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Отруєння: %.0f%% шанс отруїти ціль (%d урону за %d ходи)", 
                            POISON_CHANCE * 100, POISON_DAMAGE, POISON_DURATION));
        additionalInfo.append("\n  - Отруйний удар: спеціальна атака з гарантованим отруєнням");
        
        return additionalInfo.toString();
    }
}