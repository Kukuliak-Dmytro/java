package effects;

/**
 * Ефект щита, що зменшує вхідний урон на певний відсоток
 */
public class ShieldEffect extends Effect {
    private double damageReduction; // зменшення урону (0.5 = 50% зменшення)
    
    public ShieldEffect(int duration, double damageReduction) {
        super("Щит", duration, EffectType.SHIELD);
        this.damageReduction = damageReduction;
    }
    
    @Override
    public boolean apply() {
        // Ефект застосовується автоматично через modifyIncomingDamage
        return true;
    }
    
    @Override
    public int modifyIncomingDamage(int incomingDamage) {
        // Зменшуємо вхідний урон
        return (int) (incomingDamage * (1 - damageReduction));
    }
    
    public double getDamageReduction() {
        return damageReduction;
    }
    
    @Override
    public String toString() {
        return String.format("Щит -%.0f%% урону (%d ходів)", 
                           damageReduction * 100, duration);
    }
}