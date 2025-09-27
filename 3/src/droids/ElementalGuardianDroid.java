package droids;

import effects.ShieldEffect;
import effects.DamageBuffEffect;
import effects.FireEffect;

/**
 * Клас елементального захисника з потужними захисними та підтримуючими здібностями:
 * - Адаптивний щит, що змінюється залежно від типу атак
 * - Може надавати бафи союзникам
 * - Елементальна контратака при блокуванні
 */
public class ElementalGuardianDroid extends Droid {
    private static final double BLOCK_CHANCE = 0.35; // 35% шанс заблокувати
    private static final double COUNTER_CHANCE = 0.20; // 20% шанс контратаки при блоці
    private static final double ADAPTIVE_SHIELD_THRESHOLD = 0.6; // При 60% HP
    private static final int SHIELD_REDUCTION = 25; // 25% зменшення урону
    private static final int SHIELD_DURATION = 3; // Тривалість щита в хода
    
    private boolean adaptiveShieldActive = false;
    private int blockedAttacksCount = 0;
    
    public ElementalGuardianDroid(String name) {
        // Характеристики: низький урон, дуже високе HP, середня точність, низька швидкість
        super(name, 150, 22, 0.82, 10);
    }
    
    @Override
    protected int applySpecialAttackAbility(int damage, Droid target) {
        // Активація адаптивного щита при потребі
        if (!adaptiveShieldActive && health < maxHealth * ADAPTIVE_SHIELD_THRESHOLD) {
            activateAdaptiveShield();
        }
        
        // Елементальне підсилення атаки залежно від накопичених блоків
        if (blockedAttacksCount > 0) {
            double multiplier = 1.0 + (blockedAttacksCount * 0.1); // +10% за кожен заблокований удар
            damage = (int) (damage * multiplier);
            System.out.println(name + " використовує накопичену енергію! (+" + 
                             (int)((multiplier - 1) * 100) + "% урону)");
            blockedAttacksCount = Math.max(0, blockedAttacksCount - 2); // Витрачаємо енергію
        }
        
        return damage;
    }
    
    @Override
    protected int applySpecialDefenseAbility(int incomingDamage) {
        // Перевірка блокування
        if (random.nextDouble() < BLOCK_CHANCE) {
            blockedAttacksCount++;
            System.out.println(name + " БЛОКУЄ атаку елементальним щитом!");
            
            // Шанс контратаки при блокуванні
            if (random.nextDouble() < COUNTER_CHANCE) {
                System.out.println(name + " здійснює ЕЛЕМЕНТАЛЬНУ КОНТРАТАКУ!");
                // Контратака буде реалізована в системі бою
                // Тут тільки повідомляємо про неї
            }
            
            // Блок зменшує урон на 70%
            return (int) (incomingDamage * 0.3);
        }
        
        return incomingDamage;
    }
    
    /**
     * Активує адаптивний щит при низькому HP
     */
    private void activateAdaptiveShield() {
        ShieldEffect shield = new ShieldEffect(SHIELD_DURATION, SHIELD_REDUCTION);
        this.applyEffect(shield);
        adaptiveShieldActive = true;
        System.out.println(name + " активує АДАПТИВНИЙ ЕЛЕМЕНТАЛЬНИЙ ЩИТ! (-" + SHIELD_REDUCTION + "% урону)");
    }
    
    /**
     * Спеціальна здібність - елементальна підтримка союзника
     * Надає союзнику щит та підсилення урону
     */
    public void elementalSupport(Droid ally) {
        if (!canUseElementalSupport()) {
            System.out.println(name + " не може надати елементальну підтримку!");
            return;
        }
        
        System.out.println(name + " надає ЕЛЕМЕНТАЛЬНУ ПІДТРИМКУ " + ally.getName() + "!");
        
        // Надаємо щит
        ShieldEffect allyShield = new ShieldEffect(4, 20); // 20% зменшення урону
        ally.applyEffect(allyShield);
        
        // Надаємо підсилення урону
        DamageBuffEffect allyBuff = new DamageBuffEffect(3, 0.3); // +30% урону
        ally.applyEffect(allyBuff);
        
        System.out.println(ally.getName() + " отримує елементальний щит та підсилення!");
        
        // Витрачаємо енергію захисника
        health = Math.max(1, health - 15); // Жертвуємо 15 HP для підтримки
    }
    
    /**
     * Спеціальна атака - елементальний вибух
     * Завдає урон та може підпалити ціль
     */
    public void elementalBurst(Droid target) {
        if (!canUseElementalBurst()) {
            System.out.println(name + " не може використати елементальний вибух!");
            return;
        }
        
        System.out.println(name + " використовує ЕЛЕМЕНТАЛЬНИЙ ВИБУХ!");
        
        // Потужна атака з підвищеним уроном
        double accuracyRoll = random.nextDouble();
        double effectiveAccuracy = Math.max(accuracy, accuracyRoll);
        int burstDamage = (int) (getModifiedDamage() * 2.0 * effectiveAccuracy); // Подвійний урон
        
        // Додаємо елементальний ефект
        if (random.nextDouble() < 0.6) { // 60% шанс
            FireEffect elementalFire = new FireEffect(3, 18, 0.2);
            target.applyEffect(elementalFire);
            System.out.println("Вибух створює елементальний вогонь!");
        }
        
        int actualDamage = target.takeDamage(burstDamage);
        System.out.println(name + " завдає " + actualDamage + " елементального урону!");
        
        // Скидаємо накопичені блоки після потужної атаки
        blockedAttacksCount = 0;
    }
    
    /**
     * Перевіряє, чи може використати підтримку
     */
    public boolean canUseElementalSupport() {
        return isAlive() && health > 20;
    }
    
    /**
     * Перевіряє, чи може використати вибух
     */
    public boolean canUseElementalBurst() {
        return isAlive() && health > 25 && blockedAttacksCount >= 2;
    }
    
    /**
     * Скидає стан для нових боїв
     */
    public void resetGuardianState() {
        adaptiveShieldActive = false;
        blockedAttacksCount = 0;
    }
    
    @Override
    public String getInfo() {
        String baseInfo = super.getInfo();
        StringBuilder additionalInfo = new StringBuilder(baseInfo);
        
        additionalInfo.append("\n  Спеціальні здібності:");
        additionalInfo.append(String.format("\n  - Елементальний блок: %.0f%% шанс заблокувати (-70%% урону)", 
                            BLOCK_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Контратака: %.0f%% шанс при блоці", COUNTER_CHANCE * 100));
        additionalInfo.append(String.format("\n  - Адаптивний щит: -%d%% урону при HP < %.0f%%", 
                            SHIELD_REDUCTION, ADAPTIVE_SHIELD_THRESHOLD * 100));
        additionalInfo.append("\n  - Елементальна підтримка: щит + баф союзнику");
        additionalInfo.append("\n  - Елементальний вибух: потужна атака (потребує 2+ блоки)");
        
        if (blockedAttacksCount > 0) {
            additionalInfo.append(String.format("\n  ⚡ Накопичено енергії: %d блоків (+" + 
                                (blockedAttacksCount * 10) + "%% урону)", blockedAttacksCount));
        }
        
        if (!adaptiveShieldActive && health < maxHealth * ADAPTIVE_SHIELD_THRESHOLD) {
            additionalInfo.append("\n  🛡️ ГОТОВИЙ ДО АКТИВАЦІЇ ЩИТА!");
        }
        
        if (canUseElementalBurst()) {
            additionalInfo.append("\n  💥 ГОТОВИЙ ДО ЕЛЕМЕНТАЛЬНОГО ВИБУХУ!");
        }
        
        return additionalInfo.toString();
    }
}