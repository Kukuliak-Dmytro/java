package droids;

import effects.PoisonEffect;
import effects.FireEffect;
import effects.SpeedBuffEffect;

/**
 * Клас маг-асасин дроїда з комбінованими магічними та асасинськими здібностями:
 * - Може застосовувати як отруту, так і вогонь
 * - Висока швидкість та ухиляння
 * - Спеціальна комбо-атака з підсиленням швидкості
 */
public class MageAssassinDroid extends Droid {
    private static final double POISON_CHANCE = 0.20; // 20% шанс отруїти
    private static final double FIRE_CHANCE = 0.15; // 15% шанс підпалити
    private static final double DODGE_CHANCE = 0.25; // 25% ухиляння
    private static final double COMBO_THRESHOLD = 0.3; // При 30% HP
    private static final int SPEED_BUFF_TURNS = 3;
    private static final int SPEED_BONUS = 8;
    
    private boolean comboActivated = false;
    
    public MageAssassinDroid(String name) {
        // Характеристики: середній урон, низьке HP, висока точність, дуже висока швидкість
        super(name, 85, 32, 0.90, 18);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // Активація комбо при низькому HP
        if (!comboActivated && health < maxHealth * COMBO_THRESHOLD) {
            activateComboMode();
        }
        
        // Шанси на застосування ефектів
        boolean appliedEffect = false;
        
        // Спочатку перевіряємо отруту (вища пріоритетність)
        if (random.nextDouble() < POISON_CHANCE) {
            PoisonEffect poison = new PoisonEffect(4, 8);
            target.applyEffect(poison);
            System.out.println(name + " отруює " + target.getName() + " ТОКСИЧНОЮ МАГІЄЮ!");
            appliedEffect = true;
        }
        // Якщо отрута не спрацювала, перевіряємо вогонь
        else if (random.nextDouble() < FIRE_CHANCE) {
            FireEffect fire = new FireEffect(3, 10);
            target.applyEffect(fire);
            System.out.println(name + " підпалює " + target.getName() + " МАГІЧНИМ ВОГНЕМ!");
            appliedEffect = true;
        }
        
        // Додатковий урон, якщо застосований будь-який ефект
        if (appliedEffect) {
            damage = (int) (damage * 1.15); // +15% урону при застосуванні ефекту
            System.out.println("Магічна атака посилена! (+15% урону)");
        }
        
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Перевірка на ухиляння
        if (random.nextDouble() < DODGE_CHANCE) {
            System.out.println(name + " МАГІЧНО УХИЛЯЄТЬСЯ від атаки!");
            return 0; // Повне уникнення урону
        }
        
        return incomingDamage;
    }
    
    /**
     * Активує комбо-режим - підвищує швидкість і відновлює деякі здібності
     */
    private void activateComboMode() {
        SpeedBuffEffect speedBuff = new SpeedBuffEffect(SPEED_BUFF_TURNS, SPEED_BONUS);
        this.applyEffect(speedBuff);
        comboActivated = true;
        System.out.println(name + " активує МАГІЧНЕ КОМБО! Швидкість збільшена на " + SPEED_BONUS + "!");
    }
    
    /**
     * Спеціальна атака - подвійний удар з токсичним вогнем
     */
    public void toxicFireStrike(Droid target) {
        if (!canUseToxicFireStrike()) {
            System.out.println(name + " не може використати подвійний удар!");
            return;
        }
        
        System.out.println(name + " використовує ТОКСИЧНИЙ ВОГНЯНИЙ УДАР!");
        
        // Перший удар - звичайний з отрутою
        double accuracyRoll1 = random.nextDouble();
        double effectiveAccuracy1 = Math.max(accuracy, accuracyRoll1);
        int damage1 = (int) (getModifiedDamage() * 0.8 * effectiveAccuracy1);
        
        PoisonEffect poison = new PoisonEffect(5, 12);
        target.applyEffect(poison);
        
        int actualDamage1 = target.takeDamage(damage1);
        System.out.println("Перший удар: " + actualDamage1 + " урону + сильна отрута!");
        
        // Другий удар - з вогнем (якщо ціль ще жива)
        if (target.isAlive()) {
            double accuracyRoll2 = random.nextDouble();
            double effectiveAccuracy2 = Math.max(accuracy, accuracyRoll2);
            int damage2 = (int) (getModifiedDamage() * 0.9 * effectiveAccuracy2);
            
            FireEffect fire = new FireEffect(4, 15, 0.3); // Високий шанс поширення
            target.applyEffect(fire);
            
            int actualDamage2 = target.takeDamage(damage2);
            System.out.println("Другий удар: " + actualDamage2 + " урону + сильний вогонь!");
        }
    }
    
    /**
     * Перевіряє, чи може використати спеціальну атаку
     */
    public boolean canUseToxicFireStrike() {
        return isAlive() && health > 15;
    }
    
    /**
     * Скидає стан комбо для нових боїв
     */
    public void resetCombo() {
        comboActivated = false;
    }
    
    @Override
    public String getInfo() {
        String baseInfo = super.getInfo();
        StringBuilder additionalInfo = new StringBuilder(baseInfo);
        
        additionalInfo.append("\n  Спеціальні здібності:");
        additionalInfo.append(String.format("\n  - Токсична магія: %.0f%% шанс отруїти", POISON_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Магічний вогонь: %.0f%% шанс підпалити", FIRE_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Магічне ухиляння: %.0f%% шанс уникнути урону", DODGE_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Магічне комбо: +%d швидкості при HP < %.0f%%", 
                            SPEED_BONUS, COMBO_THRESHOLD * 100));
        additionalInfo.append("\n  - Подвійний удар: токсичний вогняний удар");
        
        if (!comboActivated && health < maxHealth * COMBO_THRESHOLD) {
            additionalInfo.append("\n  ⚡ ГОТОВИЙ ДО МАГІЧНОГО КОМБО!");
        }
        
        if (comboActivated) {
            additionalInfo.append("\n  🔮 КОМБО АКТИВНЕ!");
        }
        
        return additionalInfo.toString();
    }
}