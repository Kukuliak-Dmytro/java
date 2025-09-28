package droids;

import effects.DamageBuffEffect;
import effects.SpeedBuffEffect;
import java.util.List;

/**
 * BufferDroid - тактичний підсилювач команди
 * Спеціалізується на підсиленні урону та швидкості союзників
 */
public class BufferDroid extends SupportDroid {
    
    public BufferDroid(String name) {
        super(name, 110, 16, 0.90, 19); // більше урону і швидкості
        this.maxMana = 100;
        this.currentMana = 100;
    }
    
    @Override
    public void useSpecialAbility(List<Droid> allies, List<Droid> enemies) {
        if (!canUseAbility()) {
            System.out.println(name + " не має достатньо мани для підсилення!");
            return;
        }
        
        // Вибираємо найкращого атакера для підсилення урону
        Droid target = selectBestAttacker(allies);
        if (target == null) {
            System.out.println(name + " не знайшов союзників для підсилення!");
            return;
        }
        
        // Підсилюємо урон союзника
        DamageBuffEffect damageBuff = new DamageBuffEffect(4, 0.3); // +30% урону на 4 ходи
        target.applyEffect(damageBuff);
        
        System.out.println(name + " підсилює урон " + target.getName() + 
                          " на 30% на 4 ходи!");
        
        consumeMana(getAbilityManaCost());
    }
    
    /**
     * Підсилення швидкості - збільшує швидкість союзника або команди
     */
    public void speedBoost(Droid target) {
        if (currentMana < 25) {
            System.out.println(name + " не має мани для підсилення швидкості!");
            return;
        }
        
        int speedBonus = (int)(target.getSpeed() * 0.25); // +25% швидкості
        SpeedBuffEffect speedBuff = new SpeedBuffEffect(3, speedBonus);
        target.applyEffect(speedBuff);
        
        System.out.println(name + " підвищує швидкість " + target.getName() + 
                          " на 25% на 3 ходи!");
        
        consumeMana(25);
    }
    
    /**
     * Командне підсилення - підвищує характеристики всієї команди
     */
    public void teamBoost(List<Droid> allies) {
        if (currentMana < 70) {
            System.out.println(name + " не має мани для командного підсилення!");
            return;
        }
        
        System.out.println(name + " активує КОМАНДНЕ ПІДСИЛЕННЯ!");
        
        for (Droid ally : allies) {
            if (ally.isAlive()) {
                // Даємо меншій підсилення, але всім
                DamageBuffEffect buff = new DamageBuffEffect(3, 0.2); // +20% урону на 3 ходи
                int allySpeedBonus = (int)(ally.getSpeed() * 0.15); // +15% швидкості
                SpeedBuffEffect speedBuff = new SpeedBuffEffect(3, allySpeedBonus);
                
                ally.applyEffect(buff);
                ally.applyEffect(speedBuff);
                
                System.out.println("  → " + ally.getName() + " отримує підсилення урону та швидкості");
            }
        }
        
        consumeMana(70);
    }
    
    /**
     * Берсерк - потужне підсилення одного союзника
     */
    public void berserk(Droid target) {
        if (currentMana < 50) {
            System.out.println(name + " не має мани для берсерка!");
            return;
        }
        
        // Потужне, але короткочасне підсилення
        DamageBuffEffect berserkBuff = new DamageBuffEffect(2, 0.6); // +60% урону на 2 ходи
        int berserkSpeedBonus = (int)(target.getSpeed() * 0.4); // +40% швидкості
        SpeedBuffEffect speedBerserk = new SpeedBuffEffect(2, berserkSpeedBonus);
        
        target.applyEffect(berserkBuff);
        target.applyEffect(speedBerserk);
        
        System.out.println(name + " вводить " + target.getName() + 
                          " в БЕРСЕРК (+60% урону, +40% швидкості на 2 ходи)!");
        
        consumeMana(50);
    }
    
    /**
     * Аура лідерства - постійний пасивний ефект для команди
     */
    public void leadershipAura(List<Droid> allies) {
        if (currentMana < 30) {
            System.out.println(name + " не має мани для ауры лідерства!");
            return;
        }
        
        System.out.println(name + " випромінює АУРУ ЛІДЕРСТВА!");
        
        for (Droid ally : allies) {
            if (ally.isAlive() && ally != this) {
                // Слабке, але тривале підсилення
                DamageBuffEffect leaderBuff = new DamageBuffEffect(5, 0.1); // +10% урону на 5 ходів
                ally.applyEffect(leaderBuff);
                System.out.println("  → " + ally.getName() + " надихається присутністю лідера");
            }
        }
        
        consumeMana(30);
    }
    
    @Override
    public boolean canUseAbility() {
        return hasEnoughMana();
    }
    
    @Override
    public String getAbilityDescription() {
        return "Підсилення урону (+30% урону на 4 ходи)";
    }
    
    @Override
    public int getAbilityManaCost() {
        return 35;
    }
    
    @Override
    protected void regenerateMana() {
        int manaRegen = 7; // хороша швидкість відновлення мани
        restoreMana(manaRegen);
    }
    
    /**
     * Автоматична підтримка команди
     */
    public void autoSupport(List<Droid> allies, List<Droid> enemies) {
        // Перевіряємо, чи варто використати командне підсилення
        if (allies.size() >= 3 && currentMana >= 70) {
            teamBoost(allies);
            return;
        }
        
        // Шукаємо найкращого кандидата для берсерка
        Droid bestAttacker = selectBestAttacker(allies);
        if (bestAttacker != null && bestAttacker.getHealth() > bestAttacker.getMaxHealth() * 0.6 
            && currentMana >= 50) {
            berserk(bestAttacker);
            return;
        }
        
        // Інакше використовуємо звичайне підсилення
        useSpecialAbility(allies, enemies);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // BufferDroid може самопідсилюватися під час атаки
        if (random.nextDouble() < 0.15) { // 15% шанс
            System.out.println(name + " самопідсилюється перед атакою!");
            return (int)(damage * 1.3); // +30% урону
        }
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // BufferDroid може ухилитися завдяки підвищеній швидкості
        if (random.nextDouble() < 0.2) { // 20% шанс ухилення
            System.out.println(name + " швидко ухиляється від атаки!");
            return (int)(incomingDamage * 0.5); // -50% урону
        }
        return incomingDamage;
    }
    
    @Override
    public String getInfo() {
        return super.getInfo() + 
               "\n  └─ Спеціальні здібності:" +
               "\n    • Підсилення швидкості (25 мани)" +
               "\n    • Командне підсилення (70 мани)" +
               "\n    • Берсерк (50 мани)" +
               "\n    • Аура лідерства (30 мани)";
    }
}