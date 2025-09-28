package effects;

/**
 * Ефект зменшення урону - зменшує урон ворогів на певний відсоток
 * Використовується DefenderDroid для ослаблення ворожих атак
 */
public class DamageReductionEffect extends Effect {
    private double reductionPercent; // відсоток зменшення урону (0.0 - 1.0)
    
    public DamageReductionEffect(int duration, double reductionPercent) {
        super("Ослаблення", duration, EffectType.DEBUFF);
        this.reductionPercent = Math.max(0.0, Math.min(1.0, reductionPercent)); // обмежуємо 0-100%
    }
    
    @Override
    public boolean apply() {
        duration--;
        return duration > 0;
    }
    
    @Override
    public int modifyDamage(int baseDamage) {
        // Зменшуємо урон дроїда, на якого накладено цей ефект
        return (int) Math.round(baseDamage * (1.0 - reductionPercent));
    }
    
    /**
     * Отримати відсоток зменшення урону
     * @return відсоток зменшення (0.0 - 1.0)
     */
    public double getReductionPercent() {
        return reductionPercent;
    }
    
    /**
     * Розрахувати зменшений урон
     * @param originalDamage оригінальний урон
     * @return зменшений урон
     */
    public int calculateReducedDamage(int originalDamage) {
        return (int) Math.round(originalDamage * (1.0 - reductionPercent));
    }
}