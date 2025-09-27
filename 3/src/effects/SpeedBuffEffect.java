package effects;

/**
 * Ефект підвищення швидкості, що збільшує швидкість дроїда
 */
public class SpeedBuffEffect extends Effect {
    private int speedBonus; // бонус до швидкості
    
    public SpeedBuffEffect(int duration, int speedBonus) {
        super("Прискорення", duration, EffectType.BUFF);
        this.speedBonus = speedBonus;
    }
    
    @Override
    public boolean apply() {
        // Ефект застосовується автоматично через modifySpeed
        return true;
    }
    
    @Override
    public int modifySpeed(int baseSpeed) {
        // Збільшуємо швидкість на фіксовану кількість
        return baseSpeed + speedBonus;
    }
    
    public int getSpeedBonus() {
        return speedBonus;
    }
    
    @Override
    public String toString() {
        return String.format("Прискорення +%d швидкості (%d ходів)", 
                           speedBonus, duration);
    }
}