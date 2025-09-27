package effects;

/**
 * Абстрактний базовий клас для всіх ефектів, що можуть бути застосовані до дроїдів.
 * Ефекти можуть бути баффами, дебаффами, щитами тощо.
 */
public abstract class Effect {
    protected String name;
    protected int duration;
    protected EffectType type;
    
    public enum EffectType {
        BUFF,        // позитивний ефект
        DEBUFF,      // негативний ефект
        SHIELD,      // захисний ефект
        POISON,      // отруйний ефект
        HEAL         // ефект лікування
    }
    
    public Effect(String name, int duration, EffectType type) {
        this.name = name;
        this.duration = duration;
        this.type = type;
    }
    
    /**
     * Застосовує ефект до дроїда на початку його ходу
     * @return true, якщо ефект все ще активний, false - якщо закінчився
     */
    public abstract boolean apply();
    
    /**
     * Модифікує урон, що наноситься дроїдом
     */
    public int modifyDamage(int baseDamage) {
        return baseDamage;
    }
    
    /**
     * Модифікує урон, що отримується дроїдом
     */
    public int modifyIncomingDamage(int incomingDamage) {
        return incomingDamage;
    }
    
    /**
     * Модифікує швидкість дроїда
     */
    public int modifySpeed(int baseSpeed) {
        return baseSpeed;
    }
    
    /**
     * Зменшує тривалість ефекту на 1 хід
     * @return true, якщо ефект все ще активний
     */
    public boolean decreaseDuration() {
        duration--;
        return duration > 0;
    }
    
    // Геттери
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public EffectType getType() { return type; }
    
    @Override
    public String toString() {
        return String.format("%s (%d ходів)", name, duration);
    }
}