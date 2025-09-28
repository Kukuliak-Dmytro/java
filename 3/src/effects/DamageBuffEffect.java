package effects;

/**
 * Ефект підсилення урону, що збільшує базовий урон дроїда на певний відсоток
 */
public class DamageBuffEffect extends Effect {
    private double damageMultiplier; // множник урону (наприклад, 0.3 = +30%)
    
    public DamageBuffEffect(int duration, double damageMultiplier) {
        super("Підсилення урону", duration, EffectType.BUFF);
        this.damageMultiplier = damageMultiplier;
    }
    
    @Override
    public boolean apply() {
        // Ефект застосовується автоматично через modifyDamage
        return true;
    }
    
    @Override
    public int modifyDamage(int baseDamage) {
        // Збільшуємо урон на вказаний відсоток
        return (int) (baseDamage * (1 + damageMultiplier));
    }
    
    /**
     * Отримує множник підсилення урону
     * @return множник урону (наприклад, 0.3 = +30%)
     */
    public double getDamageMultiplier() {
        return damageMultiplier;
    }
    
    @Override
    public String toString() {
        return String.format("Підсилення урону +%.0f%% (%d ходів)", 
                           damageMultiplier * 100, duration);
    }
}