package droids;

import effects.HealOverTimeEffect;
import java.util.List;

/**
 * MedicDroid - лікар команди
 * Спеціалізується на лікуванні союзників та підтримці їхнього здоров'я
 */
public class MedicDroid extends SupportDroid {
    
    /**
     * Створює новий екземпляр медик-дроїда з заданим ім'ям
     * Характеристики: низький урон (14), середнє HP (115), висока точність (0.90), висока швидкість (18)
     * Має підвищену ману (120) для частого лікування
     * @param name ім'я дроїда
     */
    public MedicDroid(String name) {
        super(name, 115, 14, 0.90, 18); // середні характеристики з акцентом на швидкість
        this.maxMana = 120;
        this.currentMana = 120;
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (!canUseAbility()) {
            System.out.println(name + " не має достатньо мани для лікування!");
            return;
        }
        
        // Вибираємо найбільш поранених союзників
        Droid target = selectBestAlly(allies);
        if (target == null) {
            System.out.println(name + " не знайшов союзників для лікування!");
            return;
        }
        
        // Використовуємо основну здібність лікування
        int healAmount = 25 + random.nextInt(16); // 25-40 HP
        target.heal(healAmount);
        
        System.out.println(name + " лікує " + target.getName() + " на " + healAmount + " HP!");
        
        // Додаємо ефект регенерації на 3 ходи
        HealOverTimeEffect regenEffect = new HealOverTimeEffect(3, 8);
        target.applyEffect(regenEffect);
        
        consumeMana(getAbilityManaCost());
    }
    
    /**
     * Масове лікування - лікує всіх союзників на меншу кількість HP
     */
    public void massHeal(List<Droid> allies) {
        if (currentMana < 50) {
            System.out.println(name + " не має мани для масового лікування!");
            return;
        }
        
        System.out.println(name + " використовує МАСОВЕ ЛІКУВАННЯ!");
        
        for (Droid ally : allies) {
            if (ally.isAlive() && ally != this) {
                int healAmount = 15 + random.nextInt(11); // 15-25 HP
                ally.heal(healAmount);
                System.out.println("  → " + ally.getName() + " отримує " + healAmount + " HP");
            }
        }
        
        consumeMana(50);
    }
    
    /**
     * Екстрене лікування - потужне одиночне лікування при критичному стані союзника
     */
    public void emergencyHeal(Droid target) {
        if (currentMana < 40) {
            System.out.println(name + " не має мани для екстреного лікування!");
            return;
        }
        
        if (target.getHealth() > target.getMaxHealth() * 0.3) {
            System.out.println(target.getName() + " не потребує екстреного лікування!");
            return;
        }
        
        int healAmount = 50 + random.nextInt(21); // 50-70 HP
        target.heal(healAmount);
        
        System.out.println(name + " проводить ЕКСТРЕНЕ ЛІКУВАННЯ " + target.getName() + " на " + healAmount + " HP!");
        
        consumeMana(40);
    }
    
    @Override
    public boolean canUseAbility() {
        return hasEnoughMana();
    }
    
    @Override
    public String getAbilityDescription() {
        return "Лікування (25-40 HP + регенерація)";
    }
    
    @Override
    public int getAbilityManaCost() {
        return 25;
    }
    
    @Override
    protected void regenerateMana() {
        int manaRegen = 8; // медики відновлюють ману швидше
        restoreMana(manaRegen);
    }
    
    /**
     * Перевизначаємо метод атаки - медики намагаються лікувати замість атаки
     */
    @Override
    public int attack(Droid target) {
        System.out.println(name + " (медик) намагається лікувати замість атаки, але не знайшов союзників...");
        return super.attack(target);
    }
    
    /**
     * Спеціальний метод для автоматичної підтримки команди
     */
    public void autoSupport(List<Droid> allies, List<Droid> enemies) {
        // Перевіряємо, чи потрібне екстрене лікування
        for (Droid ally : allies) {
            if (ally.isAlive() && ally != this && ally.getHealth() < ally.getMaxHealth() * 0.25) {
                emergencyHeal(ally);
                return;
            }
        }
        
        // Перевіряємо, чи варто використати масове лікування
        long woundedAllies = allies.stream()
            .filter(d -> d.isAlive() && d != this && d.getHealth() < d.getMaxHealth() * 0.7)
            .count();
            
        if (woundedAllies >= 2 && currentMana >= 50) {
            massHeal(allies);
            return;
        }
        
        // Інакше використовуємо звичайне лікування
        useSpecialAbility(allies, enemies);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // Медики не мають спеціальних атакуючих здібностей
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Медики мають трохи кращий захист завдяки медичному обладнанню
        if (random.nextDouble() < 0.15) { // 15% шанс зменшити урон
            int reducedDamage = (int)(incomingDamage * 0.8);
            System.out.println(name + " використовує медичне обладнання для захисту! Урон зменшено з " 
                              + incomingDamage + " до " + reducedDamage);
            return reducedDamage;
        }
        return incomingDamage;
    }
    
    @Override
    public String getInfo() {
        return super.getInfo() + 
               "\n  └─ Спеціальні здібності:" +
               "\n    • Масове лікування (50 мани)" +
               "\n    • Екстрене лікування (40 мани)";
    }
}