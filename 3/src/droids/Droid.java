package droids;

import effects.Effect;
import effects.PoisonEffect;
import effects.FireEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Абстрактний базовий клас для всіх дроїдів у грі "Битва дроїдів".
 * Містить основні характеристики та методи, спільні для всіх типів дроїдів.
 */
public abstract class Droid {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected double accuracy;
    protected int speed;
    protected List<Effect> activeEffects;
    protected Random random;
    
    /**
     * Створює новий екземпляр дроїда з заданими характеристиками
     * @param name ім'я дроїда
     * @param health максимальне здоров'я
     * @param damage базовий урон
     * @param accuracy точність атак (0.0 - 1.0)
     * @param speed швидкість для системи ініціативи
     */
    public Droid(String name, int health, int damage, double accuracy, int speed) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.accuracy = accuracy;
        this.speed = speed;
        this.activeEffects = new ArrayList<>();
        this.random = new Random();
    }
    
    /**
     * Атакує іншого дроїда
     * @param target ціль атаки
     * @return урон, що був завданий
     */
    public int attack(Droid target) {
        // Розраховуємо базовий урон з урахуванням ефектів
        int baseDamage = getModifiedDamage();
        
        // Викликаємо спеціальну здібність перед атакою (якщо є)
        baseDamage = applySpecialAttackAbility(baseDamage, target);
        
        // Розраховуємо точність атаки (accuracy% до 100%)
        double accuracyModifier = calculateAccuracyModifier();
        int finalDamage = (int) Math.round(baseDamage * accuracyModifier);
        
        // Наносимо урон цілі
        int actualDamage = target.takeDamage(finalDamage);
        
        // Виводимо інформацію про атаку
        if (accuracyModifier < getModifiedAccuracy() + 0.05) { // Якщо точність низька
            System.out.println(name + " атакує " + target.getName() + " неточно і наносить лише " + actualDamage + " урону (" + Math.round(accuracyModifier * 100) + "% точності)");
        } else {
            System.out.println(name + " атакує " + target.getName() + " і наносить " + actualDamage + " урону!");
        }
        
        return actualDamage;
    }
    
    /**
     * Розраховує модифікатор точності для поточної атаки
     * При точності 0.85 урон буде від 85% до 100%
     * При точності 0.95 урон буде від 95% до 100%
     */
    private double calculateAccuracyModifier() {
        double accuracy = getModifiedAccuracy();
        // Генеруємо випадкове число від accuracy до 1.0
        double modifier = accuracy + (1.0 - accuracy) * random.nextDouble();
        return Math.min(modifier, 1.0); // Обмежуємо максимум 100%
    }
    
    /**
     * Отримує урон з урахуванням ефектів та спеціальних здібностей
     * @param incomingDamage урон, що надходить
     * @return фактичний урон, що був отриманий
     */
    public int takeDamage(int incomingDamage) {
        // Застосовуємо спеціальні здібності захисту
        incomingDamage = applySpecialDefenseAbility(incomingDamage);
        
        // Модифікуємо урон ефектами
        for (Effect effect : activeEffects) {
            incomingDamage = effect.modifyIncomingDamage(incomingDamage);
        }
        
        // Наносимо урон
        health = Math.max(0, health - incomingDamage);
        
        return incomingDamage;
    }
    
    /**
     * Лікує дроїда на вказану кількість HP
     * @param amount кількість HP для відновлення
     */
    public void heal(int amount) {
        int oldHealth = health;
        health = Math.min(maxHealth, health + amount);
        int actualHeal = health - oldHealth;
        
        if (actualHeal > 0) {
            System.out.println(name + " відновлює " + actualHeal + " HP!");
        }
    }
    
    /**
     * Перевіряє, чи живий дроїд
     * @return true якщо дроїд живий (HP > 0), інакше false
     */
    public boolean isAlive() {
        return health > 0;
    }
    
    /**
     * Застосовує ефект до дроїда
     * @param effect ефект для застосування
     */
    public void applyEffect(Effect effect) {
        // Видаляємо попередні ефекти того ж типу (якщо потрібно)
        activeEffects.removeIf(e -> e.getName().equals(effect.getName()));
        activeEffects.add(effect);
        System.out.println(name + " отримує ефект: " + effect.getName());
    }
    
    /**
     * Видаляє ефект з дроїда
     * @param effect ефект для видалення
     */
    public void removeEffect(Effect effect) {
        activeEffects.remove(effect);
    }
    
    /**
     * Оновлює всі активні ефекти (викликається на початку ходу дроїда)
     */
    public void updateEffects() {
        List<Effect> expiredEffects = new ArrayList<>();
        
        for (Effect effect : activeEffects) {
            // Особлива обробка для отрути
            if (effect instanceof PoisonEffect) {
                PoisonEffect poison = (PoisonEffect) effect;
                int poisonDamage = poison.getDamagePerTurn();
                health = Math.max(0, health - poisonDamage);
                System.out.println(name + " отримує " + poisonDamage + " урону від отрути!");
            }
            // Особлива обробка для вогню
            else if (effect instanceof FireEffect) {
                FireEffect fire = (FireEffect) effect;
                int fireDamage = fire.getDamagePerTurn();
                health = Math.max(0, health - fireDamage);
                System.out.println(name + " отримує " + fireDamage + " урону від вогню!");
            }
            
            // Застосовуємо ефект
            if (!effect.apply()) {
                expiredEffects.add(effect);
            }
            
            // Зменшуємо тривалість
            if (!effect.decreaseDuration()) {
                expiredEffects.add(effect);
            }
        }
        
        // Видаляємо закінчені ефекти
        for (Effect effect : expiredEffects) {
            activeEffects.remove(effect);
            System.out.println("Ефект " + effect.getName() + " закінчився у " + name);
        }
    }
    
    /**
     * Отримує модифікований урон з урахуванням ефектів
     */
    public int getModifiedDamage() {
        int modifiedDamage = damage;
        for (Effect effect : activeEffects) {
            modifiedDamage = effect.modifyDamage(modifiedDamage);
        }
        return modifiedDamage;
    }
    
    /**
     * Отримує модифіковану точність з урахуванням ефектів
     */
    public double getModifiedAccuracy() {
        return accuracy; // Поки що без модифікацій від ефектів
    }
    
    /**
     * Отримує модифіковану швидкість з урахуванням ефектів
     */
    public int getModifiedSpeed() {
        int modifiedSpeed = speed;
        for (Effect effect : activeEffects) {
            modifiedSpeed = effect.modifySpeed(modifiedSpeed);
        }
        return Math.max(1, modifiedSpeed); // Мінімальна швидкість = 1
    }
    
    /**
     * Абстрактний метод для спеціальної здібності атаки (реалізується у підкласах)
     * @param damage базовий урон атаки
     * @param target ціль атаки
     * @return модифікований урон після застосування спеціальних здібностей
     */
    protected abstract int applySpecialAttackAbility(int damage, Droid target);
    
    /**
     * Абстрактний метод для спеціальної здібності захисту (реалізується у підкласах)
     * @param incomingDamage урон, що надходить до дроїда
     * @return модифікований урон після застосування захисних здібностей
     */
    protected abstract int applySpecialDefenseAbility(int incomingDamage);
    
    /**
     * Отримує інформацію про дроїда
     */
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(String.format("%s [%s] - HP: %d/%d, Урон: %d, Точність: %.2f, Швидкість: %d", 
                    name, getClass().getSimpleName(), health, maxHealth, damage, accuracy, speed));
        
        if (!activeEffects.isEmpty()) {
            info.append("\n  Активні ефекти: ");
            for (Effect effect : activeEffects) {
                info.append(effect.toString()).append(" ");
            }
        }
        
        return info.toString();
    }
    
    /**
     * Отримує короткий статус дроїда для бою
     */
    public String getStatus() {
        return String.format("%s: %d/%d HP", name, health, maxHealth);
    }
    
    /**
     * Отримує ім'я дроїда
     * @return ім'я дроїда
     */
    public String getName() { return name; }
    
    /**
     * Отримує поточне здоров'я дроїда
     * @return поточне значення HP
     */
    public int getHealth() { return health; }
    
    /**
     * Отримує максимальне здоров'я дроїда
     * @return максимальне значення HP
     */
    public int getMaxHealth() { return maxHealth; }
    
    /**
     * Отримує базовий урон дроїда
     * @return значення базового урону
     */
    public int getDamage() { return damage; }
    
    /**
     * Отримує точність атак дроїда
     * @return значення точності (0.0 - 1.0)
     */
    public double getAccuracy() { return accuracy; }
    
    /**
     * Отримує швидкість дроїда
     * @return значення швидкості для системи ініціативи
     */
    public int getSpeed() { return speed; }
    
    /**
     * Отримує копію списку активних ефектів
     * @return список активних ефектів дроїда
     */
    public List<Effect> getActiveEffects() { return new ArrayList<>(activeEffects); }
}