package effects;

/**
 * Ефект вогню, що завдає урон кожен хід та має шанс поширитися на сусідів
 */
public class FireEffect extends Effect {
    private int damagePerTurn;
    private double spreadChance; // шанс поширення на сусідів
    
    public FireEffect(int duration, int damagePerTurn) {
        super("Горіння", duration, EffectType.POISON); // Використовуємо POISON як тип урону за хід
        this.damagePerTurn = damagePerTurn;
        this.spreadChance = 0.15; // 15% шанс поширення
    }
    
    public FireEffect(int duration, int damagePerTurn, double spreadChance) {
        super("Горіння", duration, EffectType.POISON);
        this.damagePerTurn = damagePerTurn;
        this.spreadChance = spreadChance;
    }
    
    @Override
    public boolean apply() {
        // Цей метод буде викликаний в класі Droid для завдання урону
        return true;
    }
    
    /**
     * Отримує урон від вогню за хід
     */
    public int getDamagePerTurn() {
        return damagePerTurn;
    }
    
    /**
     * Отримує шанс поширення вогню
     */
    public double getSpreadChance() {
        return spreadChance;
    }
    
    /**
     * Перевіряє, чи поширюється вогонь (для командних боїв)
     */
    public boolean shouldSpread() {
        return Math.random() < spreadChance;
    }
    
    @Override
    public String toString() {
        return String.format("Горіння %d урону/хід (%d ходів)", 
                           damagePerTurn, duration);
    }
}