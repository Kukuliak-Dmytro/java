package droids;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Абстрактний базовий клас для всіх Support дроїдів.
 * Support дроїди мають менше урону, але можуть лікувати, підсилювати та захищати союзників.
 */
public abstract class SupportDroid extends Droid {
    protected int supportRange = 1; // дальність підтримки
    protected int manaCost = 20;    // базова вартість здібностей
    protected int currentMana = 100; // поточна мана
    protected int maxMana = 100;    // максимальна мана
    protected Random random = new Random();
    
    /**
     * Конструктор для всіх Support дроїдів
     * @param name ім'я дроїда
     * @param health максимальне здоров'я
     * @param damage базовий урон
     * @param accuracy точність атак (0.0 - 1.0)
     * @param speed швидкість для системи ініціативи
     */
    public SupportDroid(String name, int health, int damage, double accuracy, int speed) {
        super(name, health, damage, accuracy, speed);
        this.currentMana = maxMana;
    }
    
    /**
     * Абстрактні методи, які ПОВИННІ реалізувати ВСІ підкласи
     */
    public abstract void useSpecialAbility(List<Droid> allies, List<Droid> enemies);
    public abstract boolean canUseAbility();
    public abstract String getAbilityDescription();
    public abstract int getAbilityManaCost();
    
    /**
     * Загальні методи для всіх Support дроїдів
     */
    
    /**
     * Вибирає найкращого союзника для підтримки (з найменшим HP)
     */
    protected Droid selectBestAlly(List<Droid> allies) {
        return allies.stream()
            .filter(Droid::isAlive)
            .filter(droid -> droid != this) // не вибираємо себе
            .min((d1, d2) -> Integer.compare(d1.getHealth(), d2.getHealth()))
            .orElse(null);
    }
    
    /**
     * Вибирає найкращого атакера серед союзників для баффів
     */
    protected Droid selectBestAttacker(List<Droid> allies) {
        return allies.stream()
            .filter(Droid::isAlive)
            .filter(droid -> droid != this)
            .max((d1, d2) -> Integer.compare(d1.getDamage(), d2.getDamage()))
            .orElse(null);
    }
    
    /**
     * Вибирає найнебезпечнішого ворога (з найбільшим уроном)
     */
    protected Droid selectMostDangerous(List<Droid> enemies) {
        return enemies.stream()
            .filter(Droid::isAlive)
            .max((d1, d2) -> Integer.compare(d1.getDamage(), d2.getDamage()))
            .orElse(null);
    }
    
    /**
     * Вибирає випадкового живого ворога
     */
    protected Droid selectRandomEnemy(List<Droid> enemies) {
        List<Droid> aliveEnemies = enemies.stream()
            .filter(Droid::isAlive)
            .collect(Collectors.toList());
            
        if (aliveEnemies.isEmpty()) {
            return null;
        }
        
        return aliveEnemies.get(random.nextInt(aliveEnemies.size()));
    }
    
    /**
     * Споживає ману для використання здібностей
     */
    protected void consumeMana(int cost) {
        this.currentMana = Math.max(0, this.currentMana - cost);
        System.out.println(name + " використовує " + cost + " мани (залишилось: " + currentMana + "/" + maxMana + ")");
    }
    
    /**
     * Відновлює ману
     */
    protected void restoreMana(int amount) {
        this.currentMana = Math.min(maxMana, this.currentMana + amount);
        System.out.println(name + " відновлює " + amount + " мани (поточна: " + currentMana + "/" + maxMana + ")");
    }
    
    /**
     * Відновлює ману кожен хід (пасивна здібність Support дроїдів)
     */
    protected void regenerateMana() {
        int manaRegen = 5; // відновлюється 5 мани за хід
        restoreMana(manaRegen);
    }
    
    /**
     * Перевіряє, чи вистачає мани для використання стандартної здібності
     */
    public boolean hasEnoughMana() {
        return currentMana >= getAbilityManaCost();
    }
    
    @Override
    public void updateEffects() {
        super.updateEffects(); // викликаємо батьківський метод
        regenerateMana(); // відновлюємо ману кожен хід
    }
    
    /**
     * Перевизначаємо метод атаки для Support дроїдів
     * Спочатку намагаємося використати спеціальну здібність
     */
    @Override
    public int attack(Droid target) {
        // Support дроїди мають слабкі атаки, тому краще використовувати здібності
        System.out.println(name + " намагається використати підтримуючі здібності замість атаки...");
        return super.attack(target); // якщо здібності не працюють, атакуємо звичайно
    }
    
    /**
     * Отримує розширену інформацію про Support дроїда включаючи ману та здібності
     * @return детальна інформація про дроїда, ману та його здібності
     */
    @Override
    public String getInfo() {
        return super.getInfo() + 
               String.format("\n  └─ Мана: %d/%d", currentMana, maxMana) +
               "\n  └─ Здібність: " + getAbilityDescription() +
               String.format(" (Вартість: %d мани)", getAbilityManaCost());
    }
    
    /**
     * Отримує поточну кількість мани
     * @return поточна мана дроїда
     */
    public int getCurrentMana() { return currentMana; }
    
    /**
     * Отримує максимальну кількість мани
     * @return максимальна мана дроїда
     */
    public int getMaxMana() { return maxMana; }
    
    /**
     * Отримує дальність підтримки
     * @return дальність дії підтримуючих здібностей
     */
    public int getSupportRange() { return supportRange; }
}