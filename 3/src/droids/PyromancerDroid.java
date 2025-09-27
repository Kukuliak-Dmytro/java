package droids;

import effects.FireEffect;
import effects.DamageBuffEffect;

/**
 * Клас піромант-дроїда з вогняними атаками та підсиленням урону:
 * - Вогняні атаки (30% шанс підпалити ціль)
 * - Самопідсилення (може підвищити свій урон на 25% на 4 ходи)
 * - Вогняний щит (при низькому HP отримує захист від вогню)
 */
public class PyromancerDroid extends Droid {
    private static final double FIRE_CHANCE = 0.30; // 30% шанс підпалити
    private static final int FIRE_DAMAGE = 12; // Урон від вогню за хід
    private static final int FIRE_DURATION = 4; // Тривалість горіння
    private static final double SELF_BUFF_THRESHOLD = 0.5; // При 50% HP
    private static final double SELF_BUFF_POWER = 0.25; // +25% урону
    private static final int SELF_BUFF_DURATION = 4;
    
    private boolean selfBuffUsed = false; // Чи використано самопідсилення
    
    public PyromancerDroid(String name) {
        // Характеристики: середній урон, середнє HP, висока точність, середня швидкість
        super(name, 110, 28, 0.88, 14);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // Перевіряємо самопідсилення при низькому HP
        if (!selfBuffUsed && health < maxHealth * SELF_BUFF_THRESHOLD) {
            DamageBuffEffect buff = new DamageBuffEffect(SELF_BUFF_DURATION, SELF_BUFF_POWER);
            this.applyEffect(buff);
            selfBuffUsed = true;
            System.out.println(name + " входить в ВОГНЯНУ ЛЮТЬ! (+25% урону)");
        }
        
        // Перевіряємо підпалення цілі
        if (random.nextDouble() < FIRE_CHANCE) {
            FireEffect fire = new FireEffect(FIRE_DURATION, FIRE_DAMAGE);
            target.applyEffect(fire);
            System.out.println(name + " ПІДПАЛЮЄ " + target.getName() + "!");
        }
        
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Піромант стійкий до вогню - зменшує урон від вогняних ефектів
        // Ця логіка буде реалізована пізніше в системі бою
        return incomingDamage;
    }
    
    /**
     * Спеціальна атака - вогняний вибух
     * Гарантовано підпалює ціль та завдає додатковий урон
     */
    public void fireBlast(Droid target) {
        System.out.println(name + " використовує ВОГНЯНИЙ ВИБУХ!");
        
        // Обчислюємо урон з урахуванням точності
        double accuracyRoll = random.nextDouble();
        double effectiveAccuracy = Math.max(accuracy, accuracyRoll);
        int finalDamage = (int) (getModifiedDamage() * 1.3 * effectiveAccuracy); // +30% урону
        
        // Гарантоване підпалення з більшим уроном
        FireEffect strongFire = new FireEffect(FIRE_DURATION + 1, FIRE_DAMAGE + 5, 0.25);
        target.applyEffect(strongFire);
        
        int actualDamage = target.takeDamage(finalDamage);
        System.out.println(name + " завдає " + actualDamage + " вогняного урону та СИЛЬНО ПІДПАЛЮЄ " + target.getName() + "!");
    }
    
    /**
     * Перевіряє, чи може піромант використати спеціальну атаку
     */
    public boolean canUseFireBlast() {
        // Спеціальну атаку можна використовувати якщо є мана/енергія (спрощена логіка)
        return isAlive() && health > 20;
    }
    
    /**
     * Скидає стан самопідсилення (для нових боїв)
     */
    public void resetSelfBuff() {
        selfBuffUsed = false;
    }
    
    @Override
    public String getInfo() {
        String baseInfo = super.getInfo();
        StringBuilder additionalInfo = new StringBuilder(baseInfo);
        
        additionalInfo.append("\n  Спеціальні здібності:");
        additionalInfo.append(String.format("\n  - Підпалення: %.0f%% шанс підпалити ціль (%d урону за %d ходи)", 
                            FIRE_CHANCE * 100, FIRE_DAMAGE, FIRE_DURATION));
        additionalInfo.append(String.format("\n  - Вогняна лють: +%.0f%% урону при HP < %.0f%% (одноразово)", 
                            SELF_BUFF_POWER * 100, SELF_BUFF_THRESHOLD * 100));
        additionalInfo.append("\n  - Вогняний вибух: спеціальна атака з гарантованим підпаленням");
        
        if (!selfBuffUsed && health < maxHealth * SELF_BUFF_THRESHOLD) {
            additionalInfo.append("\n  🔥 ГОТОВИЙ ДО ВОГНЯНОЇ ЛЮТІ!");
        }
        
        return additionalInfo.toString();
    }
}