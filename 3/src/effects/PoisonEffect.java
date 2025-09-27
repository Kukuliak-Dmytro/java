package effects;

/**
 * Ефект отруєння, що завдає урон кожен хід протягом певної тривалості.
 */
public class PoisonEffect extends Effect {
    private int damagePerTurn;
    
    public PoisonEffect(int duration, int damagePerTurn) {
        super("Отруєння", duration, EffectType.POISON);
        this.damagePerTurn = damagePerTurn;
    }
    
    @Override
    public boolean apply() {
        // Цей метод буде викликаний в класі Droid для завдання урону
        // Повертаємо true, оскільки урон наноситься автоматично
        return true;
    }
    
    /**
     * Отримує урон від отрути за хід
     */
    public int getDamagePerTurn() {
        return damagePerTurn;
    }
}