package battle;

import droids.Droid;
import java.util.*;

/**
 * Система ініціативи для покрокової атаки на основі швидкості дроїдів.
 * 
 * Принцип роботи:
 * - Кожен дроїд має швидкість (speed)
 * - Швидші дроїди ходять частіше
 * - Система використовує "часову лінію" для визначення черги ходів
 * 
 * Приклади:
 * - Швидкість 20 vs 10: співвідношення 2:1 (швидший ходить двічі)
 * - Швидкість 15 vs 10: співвідношення 3:2 
 */
public class InitiativeSystem {
    /** Базовий час для розрахунків черги ходів */
    private static final int BASE_TIME = 100;
    /** Карта часу наступного ходу для кожного дроїда */
    private Map<Droid, Integer> nextTurnTime;
    /** Поточний час у грі */
    private int currentTime;
    /** Список всіх учасників бою */
    private List<Droid> participants;
    
    /**
     * Створює нову систему ініціативи
     * Ініціалізує всі необхідні структури даних
     */
    public InitiativeSystem() {
        this.nextTurnTime = new HashMap<>();
        this.currentTime = 0;
        this.participants = new ArrayList<>();
    }
    
    /**
     * Додає учасника до системи ініціативи
     */
    public void addParticipant(Droid droid) {
        if (!participants.contains(droid)) {
            participants.add(droid);
            // Розраховуємо початковий час першого ходу на основі швидкості
            // Швидші дроїди ходять раніше
            int speed = droid.getModifiedSpeed();
            int initialDelay = Math.max(1, BASE_TIME / speed);
            nextTurnTime.put(droid, initialDelay);
        }
    }
    
    /**
     * Додає список учасників
     */
    public void addParticipants(List<Droid> droids) {
        for (Droid droid : droids) {
            addParticipant(droid);
        }
    }
    
    /**
     * Видаляє дроїда з системи ініціативи (коли він помирає)
     */
    public void removeDroid(Droid droid) {
        participants.remove(droid);
        nextTurnTime.remove(droid);
    }
    
    /**
     * Визначає наступного дроїда для ходу
     * @return дроїд, який повинен ходити наступним, або null якщо бій закінчений
     */
    public Droid getNextDroid() {
        // Видаляємо мертвих дроїдів
        participants.removeIf(droid -> !droid.isAlive());
        nextTurnTime.entrySet().removeIf(entry -> !entry.getKey().isAlive());
        
        if (participants.isEmpty()) {
            return null; // Бій закінчений
        }
        
        // Знаходимо дроїда з найменшим часом наступного ходу
        Droid nextDroid = participants.stream()
            .filter(Droid::isAlive)
            .min((d1, d2) -> Integer.compare(nextTurnTime.get(d1), nextTurnTime.get(d2)))
            .orElse(null);
            
        if (nextDroid == null) {
            return null;
        }
        
        // Оновлюємо поточний час до часу ходу цього дроїда
        currentTime = nextTurnTime.get(nextDroid);
        
        // Розраховуємо час наступного ходу для цього дроїда
        int timeToNextTurn = calculateTimeToNextTurn(nextDroid);
        nextTurnTime.put(nextDroid, currentTime + timeToNextTurn);
        
        return nextDroid;
    }
    
    /**
     * Розраховує час до наступного ходу дроїда на основі його швидкості
     */
    private int calculateTimeToNextTurn(Droid droid) {
        int speed = droid.getSpeed();
        
        // Застосовуємо модифікатори швидкості від ефектів
        speed = droid.getModifiedSpeed();
        
        // Базовий час поділений на швидкість
        // Мінімальний час між ходами - 1, щоб уникнути нескінченних циклів
        return Math.max(1, BASE_TIME / speed);
    }
    
    /**
     * Отримує черговість ходів на найближчі N ходів для передбачення
     */
    public List<Droid> predictNextTurns(int numberOfTurns) {
        List<Droid> prediction = new ArrayList<>();
        
        // Створюємо копію для симуляції
        Map<Droid, Integer> simulatedTimes = new HashMap<>(nextTurnTime);
        int simulatedCurrentTime = currentTime;
        
        for (int i = 0; i < numberOfTurns && !participants.isEmpty(); i++) {
            // Знаходимо наступного дроїда
            Droid nextDroid = participants.stream()
                .filter(Droid::isAlive)
                .min((d1, d2) -> Integer.compare(simulatedTimes.get(d1), simulatedTimes.get(d2)))
                .orElse(null);
                
            if (nextDroid == null) break;
            
            prediction.add(nextDroid);
            
            // Оновлюємо симуляцію
            simulatedCurrentTime = simulatedTimes.get(nextDroid);
            int timeToNext = calculateTimeToNextTurn(nextDroid);
            simulatedTimes.put(nextDroid, simulatedCurrentTime + timeToNext);
        }
        
        return prediction;
    }
    
    /**
     * Перезапускає систему ініціативи для нового бою
     */
    /**
     * Скидає систему ініціативи до початкового стану
     * Очищає всіх учасників та скидає час
     */
    public void reset() {
        nextTurnTime.clear();
        participants.clear();
        currentTime = 0;
    }
    
    /**
     * Отримує інформацію про черговість ходів
     */
    public String getTurnOrderInfo() {
        if (participants.isEmpty()) {
            return "Немає учасників бою";
        }
        
        StringBuilder info = new StringBuilder("Черговість найближчих ходів:\n");
        List<Droid> nextTurns = predictNextTurns(5);
        
        for (int i = 0; i < nextTurns.size(); i++) {
            Droid droid = nextTurns.get(i);
            info.append(String.format("%d. %s (швидкість: %d)\n", 
                       i + 1, droid.getName(), droid.getSpeed()));
        }
        
        return info.toString();
    }
    
    /**
     * Отримує статистику швидкості учасників
     */
    public String getSpeedStatistics() {
        if (participants.isEmpty()) {
            return "Немає учасників бою";
        }
        
        StringBuilder stats = new StringBuilder("Статистика швидкості:\n");
        
        // Сортуємо за швидкістю (найшвидші спочатку)
        participants.stream()
            .filter(Droid::isAlive)
            .sorted((d1, d2) -> Integer.compare(d2.getSpeed(), d1.getSpeed()))
            .forEach(droid -> {
                int nextTurn = nextTurnTime.get(droid) - currentTime;
                stats.append(String.format("  %s: швидкість %d, наступний хід через %d тіків\n",
                           droid.getName(), droid.getSpeed(), nextTurn));
            });
            
        return stats.toString();
    }
    
    /**
     * Перевіряє, чи є живі учасники бою
     */
    public boolean hasAliveParticipants() {
        return participants.stream().anyMatch(Droid::isAlive);
    }
    
    /**
     * Отримує кількість живих учасників
     */
    public int getAliveParticipantsCount() {
        return (int) participants.stream().filter(Droid::isAlive).count();
    }
    
    /**
     * Отримує поточний час гри
     */
    public int getCurrentTime() {
        return currentTime;
    }
    
    /**
     * Отримує всіх живих учасників
     */
    public List<Droid> getAliveParticipants() {
        return participants.stream()
            .filter(Droid::isAlive)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Форсує наступний хід для конкретного дроїда (для спеціальних здібностей)
     */
    public void giveExtraTurn(Droid droid) {
        if (participants.contains(droid) && droid.isAlive()) {
            // Дає дроїду хід раніше за всіх інших
            nextTurnTime.put(droid, currentTime - 1);
        }
    }
    
    /**
     * Затримує хід дроїда (для дебаффів швидкості)
     */
    public void delayTurn(Droid droid, int delay) {
        if (participants.contains(droid) && droid.isAlive()) {
            int currentTurnTime = nextTurnTime.get(droid);
            nextTurnTime.put(droid, currentTurnTime + delay);
        }
    }
}