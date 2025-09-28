package droids;

import effects.ShieldEffect;
import java.util.List;

/**
 * ShieldDroid - захисний спеціаліст
 * Спеціалізується на створенні щитів та бар'єрів для союзників
 */
public class ShieldDroid extends SupportDroid {
    
    public ShieldDroid(String name) {
        super(name, 125, 11, 0.90, 17); // більше HP, менше урону
        this.maxMana = 110;
        this.currentMana = 110;
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (!canUseAbility()) {
            System.out.println(name + " не має достатньо мани для створення щита!");
            return;
        }
        
        // Вибираємо союзника, який найбільше потребує захисту
        Droid target = selectBestAlly(allies);
        if (target == null) {
            // Якщо немає союзників, створюємо щит собі
            target = this;
        }
        
        // Створюємо енергетичний щит
        ShieldEffect shield = new ShieldEffect(3, 0.5); // 50% захист на 3 ходи
        target.applyEffect(shield);
        
        System.out.println(name + " створює енергетичний щит для " + target.getName() + 
                          " (50% захист на 3 ходи)!");
        
        consumeMana(getAbilityManaCost());
    }
    
    /**
     * Посилений щит - більш потужний захист для критичних ситуацій
     */
    public void reinforcedShield(Droid target) {
        if (currentMana < 45) {
            System.out.println(name + " не має мани для посиленого щита!");
            return;
        }
        
        ShieldEffect strongShield = new ShieldEffect(4, 0.7); // 70% захист на 4 ходи
        target.applyEffect(strongShield);
        
        System.out.println(name + " створює ПОСИЛЕНИЙ ЩИТ для " + target.getName() + 
                          " (70% захист на 4 ходи)!");
        
        consumeMana(45);
    }
    
    /**
     * Масовий бар'єр - захищає всю команду на короткий час
     */
    public void teamBarrier(List<Droid> allies) {
        if (currentMana < 60) {
            System.out.println(name + " не має мани для командного бар'єра!");
            return;
        }
        
        System.out.println(name + " активує КОМАНДНИЙ БАР'ЄР!");
        
        for (Droid ally : allies) {
            if (ally.isAlive()) {
                ShieldEffect barrier = new ShieldEffect(2, 0.4); // 40% захист на 2 ходи для всіх
                ally.applyEffect(barrier);
                System.out.println("  → " + ally.getName() + " отримує захисний бар'єр");
            }
        }
        
        consumeMana(60);
    }
    
    /**
     * Відбиваючий щит - має шанс відбити урон назад до атакера
     */
    public void reflectiveShield(Droid target) {
        if (currentMana < 35) {
            System.out.println(name + " не має мани для відбиваючого щита!");
            return;
        }
        
        // Створюємо спеціальний щит з відбиттям
        ShieldEffect reflectShield = new ShieldEffect(3, 0.3) {
            @Override
            public int modifyIncomingDamage(int incomingDamage) {
                int reducedDamage = super.modifyIncomingDamage(incomingDamage);
                
                // Відбиваємо частину урону (тут потрібно передати атакера)
                int reflectedDamage = (incomingDamage - reducedDamage) / 2;
                System.out.println("Відбиваючий щит повертає " + reflectedDamage + " урону атакеру!");
                
                return reducedDamage;
            }
        };
        
        target.applyEffect(reflectShield);
        
        System.out.println(name + " створює ВІДБИВАЮЧИЙ ЩИТ для " + target.getName() + "!");
        
        consumeMana(35);
    }
    
    @Override
    public boolean canUseAbility() {
        return hasEnoughMana();
    }
    
    @Override
    public String getAbilityDescription() {
        return "Енергетичний щит (50% захист на 3 ходи)";
    }
    
    @Override
    public int getAbilityManaCost() {
        return 30;
    }
    
    @Override
    protected void regenerateMana() {
        int manaRegen = 6; // середня швидкість відновлення мани
        restoreMana(manaRegen);
    }
    
    /**
     * Автоматична підтримка команди
     */
    public void autoSupport(List<Droid> allies, List<Droid> enemies) {
        // Перевіряємо, чи потрібен командний бар'єр
        long woundedAllies = allies.stream()
            .filter(d -> d.isAlive() && d.getHealth() < d.getMaxHealth() * 0.5)
            .count();
            
        if (woundedAllies >= 2 && currentMana >= 60) {
            teamBarrier(allies);
            return;
        }
        
        // Шукаємо найбільш вразливого союзника для посиленого щита
        for (Droid ally : allies) {
            if (ally.isAlive() && ally != this && 
                ally.getHealth() < ally.getMaxHealth() * 0.3 && currentMana >= 45) {
                reinforcedShield(ally);
                return;
            }
        }
        
        // Інакше використовуємо звичайний щит
        useSpecialAbility(allies, enemies);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // ShieldDroid має слабкі атаки, але може "атакувати" щитом
        if (random.nextDouble() < 0.1) { // 10% шанс
            System.out.println(name + " атакує енергетичним щитом!");
            return (int)(damage * 1.2); // +20% урону
        }
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // ShieldDroid має природну стійкість до урону
        if (random.nextDouble() < 0.25) { // 25% шанс зменшити урон
            int reducedDamage = (int)(incomingDamage * 0.7);
            System.out.println(name + " використовує вбудовані щити! Урон зменшено з " 
                              + incomingDamage + " до " + reducedDamage);
            return reducedDamage;
        }
        return incomingDamage;
    }
    
    @Override
    public String getInfo() {
        return super.getInfo() + 
               "\n  └─ Спеціальні здібності:" +
               "\n    • Посилений щит (45 мани)" +
               "\n    • Командний бар'єр (60 мани)" +
               "\n    • Відбиваючий щит (35 мани)";
    }
}