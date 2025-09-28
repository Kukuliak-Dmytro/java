package droids;

import effects.DamageReductionEffect;
import java.util.List;

/**
 * DefenderDroid - спеціаліст з контролю та ослаблення ворогів
 * Спеціалізується на зменшенні ефективності ворожої команди
 */
public class DefenderDroid extends SupportDroid {
    
    public DefenderDroid(String name) {
        super(name, 135, 9, 0.90, 16); // найбільше HP, найменший урон
        this.maxMana = 100;
        this.currentMana = 100;
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (!canUseAbility()) {
            System.out.println(name + " не має достатньо мани для ослаблення!");
            return;
        }
        
        // Вибираємо найнебезпечнішого ворога для ослаблення
        Droid target = selectMostDangerous(enemies);
        if (target == null) {
            System.out.println(name + " не знайшов ворогів для ослаблення!");
            return;
        }
        
        // Ослаблюємо урон ворога
        DamageReductionEffect debuff = new DamageReductionEffect(3, 0.4); // -40% урону на 3 ходи
        target.applyEffect(debuff);
        
        System.out.println(name + " ослаблює " + target.getName() + 
                          " зменшуючи його урон на 40% на 3 ходи!");
        
        consumeMana(getAbilityManaCost());
    }
    
    /**
     * Масове ослаблення - зменшує урон всіх ворогів
     */
    public void massWeaken(List<Droid> enemies) {
        if (currentMana < 55) {
            System.out.println(name + " не має мани для масового ослаблення!");
            return;
        }
        
        System.out.println(name + " активує МАСОВЕ ОСЛАБЛЕННЯ!");
        
        for (Droid enemy : enemies) {
            if (enemy.isAlive()) {
                DamageReductionEffect debuff = new DamageReductionEffect(2, 0.25); // -25% урону на 2 ходи
                enemy.applyEffect(debuff);
                System.out.println("  → " + enemy.getName() + " ослаблений");
            }
        }
        
        consumeMana(55);
    }
    
    /**
     * Паралізуючий удар - тимчасово зупиняє ворога
     */
    public void paralyze(Droid target) {
        if (currentMana < 40) {
            System.out.println(name + " не має мани для паралізу!");
            return;
        }
        
        // Зменшуємо швидкість та урон
        DamageReductionEffect paralysis = new DamageReductionEffect(4, 0.6); // -60% урону на 4 ходи
        target.applyEffect(paralysis);
        
        System.out.println(name + " паралізує " + target.getName() + 
                          "! (-60% урону на 4 ходи)");
        
        consumeMana(40);
    }
    
    /**
     * Провокація - змушує ворогів атакувати цього дроїда
     */
    public void provoke(List<Droid> enemies) {
        if (currentMana < 25) {
            System.out.println(name + " не має мани для провокації!");
            return;
        }
        
        System.out.println(name + " ПРОВОКУЄ ворогів атакувати себе!");
        
        // Тут можна реалізувати механіку примушення атакувати DefenderDroid
        // Поки що просто повідомляємо
        for (Droid enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println("  → " + enemy.getName() + " змушений атакувати " + name);
            }
        }
        
        consumeMana(25);
    }
    
    /**
     * Контратака - відповідь на отриманий урон
     */
    public void counterAttack(Droid attacker) {
        if (currentMana < 15) {
            return; // Мовчки не використовуємо, якщо немає мани
        }
        
        if (random.nextDouble() < 0.3) { // 30% шанс контратаки
            int counterDamage = damage / 2; // половина нашого урону
            System.out.println(name + " контратакує " + attacker.getName() + 
                              " завдаючи " + counterDamage + " урону!");
            
            attacker.takeDamage(counterDamage);
            consumeMana(15);
        }
    }
    
    /**
     * Захисна стійка - збільшує захист на кілька ходів
     */
    public void defensiveStance() {
        if (currentMana < 35) {
            System.out.println(name + " не має мани для захисної стійки!");
            return;
        }
        
        System.out.println(name + " приймає ЗАХИСНУ СТІЙКУ!");
        
        // Тут можна додати спеціальний ефект для себе
        // Поки що просто повідомляємо про підвищений захист
        System.out.println(name + " отримує підвищений захист на наступні ходи!");
        
        consumeMana(35);
    }
    
    @Override
    public boolean canUseAbility() {
        return hasEnoughMana();
    }
    
    @Override
    public String getAbilityDescription() {
        return "Ослаблення ворога (-40% урону на 3 ходи)";
    }
    
    @Override
    public int getAbilityManaCost() {
        return 30;
    }
    
    @Override
    protected void regenerateMana() {
        int manaRegen = 5; // повільна швидкість відновлення мани
        restoreMana(manaRegen);
    }
    
    /**
     * Автоматична підтримка команди
     */
    public void autoSupport(List<Droid> allies, List<Droid> enemies) {
        // Перевіряємо, чи варто використати масове ослаблення
        if (enemies.size() >= 2 && currentMana >= 55) {
            massWeaken(enemies);
            return;
        }
        
        // Шукаємо найнебезпечнішого ворога для паралізу
        Droid mostDangerous = selectMostDangerous(enemies);
        if (mostDangerous != null && mostDangerous.getHealth() > mostDangerous.getMaxHealth() * 0.7 
            && currentMana >= 40) {
            paralyze(mostDangerous);
            return;
        }
        
        // Інакше використовуємо звичайне ослаблення
        useSpecialAbility(allies, enemies);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // DefenderDroid має слабкі атаки, але може ослаблювати під час атаки
        if (random.nextDouble() < 0.2) { // 20% шанс
            System.out.println(name + " ослаблює ворога під час атаки!");
            DamageReductionEffect quickDebuff = new DamageReductionEffect(2, 0.2);
            target.applyEffect(quickDebuff);
        }
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // DefenderDroid має найкращий захист серед Support дроїдів
        if (random.nextDouble() < 0.3) { // 30% шанс зменшити урон
            int reducedDamage = (int)(incomingDamage * 0.6);
            System.out.println(name + " використовує захисні протоколи! Урон зменшено з " 
                              + incomingDamage + " до " + reducedDamage);
            
            // Можливість контратаки
            // counterAttack(attacker); // Тут потрібен посилання на атакера
            
            return reducedDamage;
        }
        return incomingDamage;
    }
    
    @Override
    public String getInfo() {
        return super.getInfo() + 
               "\n  └─ Спеціальні здібності:" +
               "\n    • Масове ослаблення (55 мани)" +
               "\n    • Паралізуючий удар (40 мани)" +
               "\n    • Провокація (25 мани)" +
               "\n    • Захисна стійка (35 мани)";
    }
}