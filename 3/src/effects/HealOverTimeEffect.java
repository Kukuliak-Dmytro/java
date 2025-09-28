package effects;

/**
 * Ефект лікування з часом - відновлює здоров'я кожен хід
 * Використовується MedicDroid для тривалого лікування
 */
public class HealOverTimeEffect extends Effect {
    private int healAmount; // кількість HP для відновлення кожен хід
    
    public HealOverTimeEffect(int duration, int healAmount) {
        super("Регенерація", duration, EffectType.HEAL);
        this.healAmount = Math.max(1, healAmount); // мінімум 1 HP
    }
    
    @Override
    public boolean apply() {
        // Ця функція буде викликана в Droid.updateEffects()
        // Лікування відбувається автоматично
        System.out.println("Регенерація лікує на " + healAmount + " HP");
        
        duration--;
        return duration > 0;
    }
    
    /**
     * Отримати кількість лікування за хід
     * @return кількість HP
     */
    public int getHealAmount() {
        return healAmount;
    }
    
    /**
     * Встановити нову кількість лікування
     * @param healAmount нова кількість HP
     */
    public void setHealAmount(int healAmount) {
        this.healAmount = Math.max(1, healAmount);
    }
}