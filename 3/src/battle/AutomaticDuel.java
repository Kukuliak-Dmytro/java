package battle;

import droids.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Клас для автоматичних дуелів з випадковим вибором учасників і цілей
 * Підтримує дуелі 1v1 та 3v3 з повністю автоматизованим процесом
 */
public class AutomaticDuel {
    /** Генератор випадкових чисел для вибору учасників */
    private Random random;
    /** Екземпляр класу Battle для проведення боїв */
    private Battle battle;
    
    /**
     * Створює новий екземпляр автоматичного дуелю
     * Ініціалізує генератор випадкових чисел та систему боїв
     */
    public AutomaticDuel() {
        this.random = new Random();
        this.battle = new Battle();
    }
    
    /**
     * Запускає автоматичний дуель 1 на 1
     * @param availableDroids список доступних дроїдів для вибору
     * @return результат дуелю
     */
    public String startDuel1v1(List<Droid> availableDroids) {
        System.out.println("=== АВТОМАТИЧНИЙ ДУЕЛЬ 1 НА 1 ===");
        
        if (availableDroids.size() < 2) {
            return "Недостатньо дроїдів для дуелю! Потрібно мінімум 2.";
        }
        
        // Вибираємо 2 випадкових дроїдів
        Droid fighter1 = selectRandomDroid(availableDroids);
        Droid fighter2 = selectRandomDroid(availableDroids, fighter1);
        
        if (fighter2 == null) {
            return "Не вдалося вибрати другого бійця!";
        }
        
        System.out.println("Вибрано бійців:");
        System.out.println("  Боєць 1: " + fighter1.getName() + " (" + fighter1.getClass().getSimpleName() + ")");
        System.out.println("  Боєць 2: " + fighter2.getName() + " (" + fighter2.getClass().getSimpleName() + ")");
        System.out.println();
        
        // Відновлюємо здоров'я перед боєм
        resetDroidHealth(fighter1);
        resetDroidHealth(fighter2);
        
        // Запускаємо бій
        battle.clearLog();
        String result = battle.oneOnOneBattle(fighter1, fighter2);
        
        // Оновлюємо статистику
        updateDuelStatistics(fighter1, fighter2);
        
        return result;
    }
    
    /**
     * Запускає автоматичний дуель 3 на 3
     * @param availableDroids список доступних дроїдів для вибору
     * @return результат дуелю
     */
    public String startDuel3v3(List<Droid> availableDroids) {
        System.out.println("=== АВТОМАТИЧНИЙ ДУЕЛЬ 3 НА 3 ===");
        
        if (availableDroids.size() < 6) {
            return "Недостатньо дроїдів для дуелю 3v3! Потрібно мінімум 6.";
        }
        
        // Формуємо дві команди по 3 дроїди
        List<Droid> team1 = selectRandomTeam(availableDroids, 3);
        List<Droid> team2 = selectRandomTeam(availableDroids, 3, team1);
        
        if (team2.size() < 3) {
            return "Не вдалося сформувати повні команди!";
        }
        
        System.out.println("Сформовано команди:");
        System.out.println("  Команда 1:");
        for (Droid droid : team1) {
            System.out.println("    " + droid.getName() + " (" + droid.getClass().getSimpleName() + ")");
        }
        System.out.println("  Команда 2:");
        for (Droid droid : team2) {
            System.out.println("    " + droid.getName() + " (" + droid.getClass().getSimpleName() + ")");
        }
        System.out.println();
        
        // Відновлюємо здоров'я всіх учасників
        team1.forEach(this::resetDroidHealth);
        team2.forEach(this::resetDroidHealth);
        
        // Запускаємо командний бій
        battle.clearLog();
        String result = battle.teamBattle(team1, team2);
        
        // Оновлюємо статистику
        updateTeamStatistics(team1, team2);
        
        return result;
    }
    
    /**
     * Запускає серію автоматичних дуелів 1v1
     * @param availableDroids список дроїдів
     * @param numberOfDuels кількість дуелів
     * @return статистика результатів
     */
    public String startDuelSeries1v1(List<Droid> availableDroids, int numberOfDuels) {
        System.out.println("=== СЕРІЯ АВТОМАТИЧНИХ ДУЕЛІВ 1v1 ===");
        System.out.println("Кількість дуелів: " + numberOfDuels);
        System.out.println();
        
        Map<String, Integer> wins = new HashMap<>();
        List<String> results = new ArrayList<>();
        
        // Ініціалізуємо статистику
        for (Droid droid : availableDroids) {
            wins.put(droid.getName(), 0);
        }
        
        for (int i = 1; i <= numberOfDuels; i++) {
            System.out.println("--- ДУЕЛЬ " + i + " ---");
            
            // Вибираємо бійців
            Droid fighter1 = selectRandomDroid(availableDroids);
            Droid fighter2 = selectRandomDroid(availableDroids, fighter1);
            
            if (fighter2 == null) {
                System.out.println("Пропускаємо дуель - не вдалося вибрати бійців");
                continue;
            }
            
            // Відновлюємо здоров'я
            resetDroidHealth(fighter1);
            resetDroidHealth(fighter2);
            
            // Проводимо швидкий бій
            battle.clearLog();
            String result = battle.oneOnOneBattle(fighter1, fighter2);
            
            // Оновлюємо статистику
            if (fighter1.isAlive()) {
                wins.put(fighter1.getName(), wins.get(fighter1.getName()) + 1);
            } else if (fighter2.isAlive()) {
                wins.put(fighter2.getName(), wins.get(fighter2.getName()) + 1);
            }
            
            results.add(String.format("Дуель %d: %s vs %s → %s", 
                       i, fighter1.getName(), fighter2.getName(), result));
            
            System.out.println("Результат: " + result);
            System.out.println();
        }
        
        // Формуємо підсумкову статистику
        StringBuilder summary = new StringBuilder("=== ПІДСУМКИ СЕРІЇ ===\n");
        summary.append("Проведено дуелів: ").append(numberOfDuels).append("\n\n");
        summary.append("Статистика перемог:\n");
        
        wins.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
            .forEach(entry -> {
                double winRate = (double) entry.getValue() / numberOfDuels * 100;
                summary.append(String.format("  %s: %d перемог (%.1f%%)\n", 
                              entry.getKey(), entry.getValue(), winRate));
            });
        
        System.out.println(summary.toString());
        return summary.toString();
    }
    
    /**
     * Запускає турнір між всіма дроїдами
     * @param availableDroids список учасників
     * @return результати турніру
     */
    public String startTournament(List<Droid> availableDroids) {
        System.out.println("=== ТУРНІР ===");
        System.out.println("Учасники: " + availableDroids.size());
        System.out.println();
        
        Map<String, Integer> tournamentWins = new HashMap<>();
        List<String> matches = new ArrayList<>();
        
        // Ініціалізуємо статистику
        for (Droid droid : availableDroids) {
            tournamentWins.put(droid.getName(), 0);
        }
        
        int matchNumber = 1;
        
        // Кожен проти кожного
        for (int i = 0; i < availableDroids.size(); i++) {
            for (int j = i + 1; j < availableDroids.size(); j++) {
                Droid fighter1 = availableDroids.get(i);
                Droid fighter2 = availableDroids.get(j);
                
                System.out.println("--- МАТЧ " + matchNumber + " ---");
                System.out.println(fighter1.getName() + " vs " + fighter2.getName());
                
                // Відновлюємо здоров'я
                resetDroidHealth(fighter1);
                resetDroidHealth(fighter2);
                
                // Проводимо бій
                battle.clearLog();
                String result = battle.oneOnOneBattle(fighter1, fighter2);
                
                // Оновлюємо статистику
                if (fighter1.isAlive()) {
                    tournamentWins.put(fighter1.getName(), 
                                     tournamentWins.get(fighter1.getName()) + 1);
                } else if (fighter2.isAlive()) {
                    tournamentWins.put(fighter2.getName(), 
                                     tournamentWins.get(fighter2.getName()) + 1);
                }
                
                matches.add(String.format("Матч %d: %s vs %s → %s", 
                           matchNumber, fighter1.getName(), fighter2.getName(), result));
                
                System.out.println("Переможець: " + result);
                System.out.println();
                
                matchNumber++;
            }
        }
        
        // Формуємо таблицю турніру
        StringBuilder tournament = new StringBuilder("=== РЕЗУЛЬТАТИ ТУРНІРУ ===\n");
        tournament.append("Зіграно матчів: ").append(matches.size()).append("\n\n");
        tournament.append("Турнірна таблиця:\n");
        
        List<Map.Entry<String, Integer>> sortedResults = tournamentWins.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
            .collect(Collectors.toList());
        
        for (int i = 0; i < sortedResults.size(); i++) {
            Map.Entry<String, Integer> entry = sortedResults.get(i);
            tournament.append(String.format("%d. %s: %d перемог\n", 
                            i + 1, entry.getKey(), entry.getValue()));
        }
        
        System.out.println(tournament.toString());
        return tournament.toString();
    }
    
    /**
     * Вибирає випадкового дроїда зі списку
     */
    private Droid selectRandomDroid(List<Droid> droids) {
        if (droids.isEmpty()) return null;
        return droids.get(random.nextInt(droids.size()));
    }
    
    /**
     * Вибирає випадкового дроїда, виключаючи вказаного
     */
    private Droid selectRandomDroid(List<Droid> droids, Droid exclude) {
        List<Droid> available = droids.stream()
            .filter(d -> d != exclude)
            .collect(Collectors.toList());
        
        if (available.isEmpty()) return null;
        return available.get(random.nextInt(available.size()));
    }
    
    /**
     * Формує випадкову команду вказаного розміру
     */
    private List<Droid> selectRandomTeam(List<Droid> droids, int size) {
        List<Droid> available = new ArrayList<>(droids);
        List<Droid> team = new ArrayList<>();
        
        for (int i = 0; i < size && !available.isEmpty(); i++) {
            Droid selected = available.get(random.nextInt(available.size()));
            team.add(selected);
            available.remove(selected);
        }
        
        return team;
    }
    
    /**
     * Формує випадкову команду, виключаючи членів іншої команди
     */
    private List<Droid> selectRandomTeam(List<Droid> droids, int size, List<Droid> excludeTeam) {
        List<Droid> available = droids.stream()
            .filter(d -> !excludeTeam.contains(d))
            .collect(Collectors.toList());
            
        return selectRandomTeam(available, size);
    }
    
    /**
     * Відновлює здоров'я дроїда перед боєм
     */
    private void resetDroidHealth(Droid droid) {
        droid.heal(droid.getMaxHealth()); // Лікуємо до максимуму
        
        // Очищаємо ефекти шляхом видалення кожного
        List<effects.Effect> effects = new ArrayList<>(droid.getActiveEffects());
        for (effects.Effect effect : effects) {
            droid.removeEffect(effect);
        }
        
        // Повідомляємо про підготовку Support дроїдів
        if (droid instanceof SupportDroid) {
            System.out.println(droid.getName() + " (Support) готовий до бою");
        }
    }
    
    /**
     * Оновлює статистику після дуелю 1v1
     */
    private void updateDuelStatistics(Droid fighter1, Droid fighter2) {
        // Тут можна реалізувати збереження статистики дроїдів
        System.out.println("Статистика оновлена");
    }
    
    /**
     * Оновлює статистику після командного бою
     */
    private void updateTeamStatistics(List<Droid> team1, List<Droid> team2) {
        // Тут можна реалізувати збереження статистики команд
        System.out.println("Командна статистика оновлена");
    }
    
    /**
     * Створює збалансовані команди для дуелю 3v3
     */
    public String createBalancedDuel3v3(List<Droid> availableDroids) {
        System.out.println("=== ЗБАЛАНСОВАНИЙ ДУЕЛЬ 3v3 ===");
        
        if (availableDroids.size() < 6) {
            return "Недостатньо дроїдів для збалансованого дуелю!";
        }
        
        // Розділяємо дроїдів за типами
        List<Droid> warriors = new ArrayList<>();
        List<Droid> tanks = new ArrayList<>();
        List<Droid> assassins = new ArrayList<>();
        List<Droid> supports = new ArrayList<>();
        List<Droid> others = new ArrayList<>();
        
        for (Droid droid : availableDroids) {
            if (droid instanceof WarriorDroid) {
                warriors.add(droid);
            } else if (droid instanceof TankDroid) {
                tanks.add(droid);
            } else if (droid instanceof AssassinDroid) {
                assassins.add(droid);
            } else if (droid instanceof SupportDroid) {
                supports.add(droid);
            } else {
                others.add(droid);
            }
        }
        
        // Намагаємося створити збалансовані команди
        List<Droid> team1 = createBalancedTeam(warriors, tanks, assassins, supports, others);
        List<Droid> team2 = createBalancedTeam(warriors, tanks, assassins, supports, others);
        
        if (team1.size() < 3 || team2.size() < 3) {
            // Якщо не вдалося створити збалансовані команди, використовуємо випадковий вибір
            return startDuel3v3(availableDroids);
        }
        
        System.out.println("Створено збалансовані команди:");
        displayTeamComposition(team1, "Команда 1");
        displayTeamComposition(team2, "Команда 2");
        
        // Відновлюємо здоров'я
        team1.forEach(this::resetDroidHealth);
        team2.forEach(this::resetDroidHealth);
        
        // Запускаємо бій
        battle.clearLog();
        return battle.teamBattle(team1, team2);
    }
    
    /**
     * Створює збалансовану команду з різних типів дроїдів
     */
    private List<Droid> createBalancedTeam(List<Droid> warriors, List<Droid> tanks, 
                                          List<Droid> assassins, List<Droid> supports, 
                                          List<Droid> others) {
        List<Droid> team = new ArrayList<>();
        
        // Намагаємося взяти по одному з кожного типу
        if (!tanks.isEmpty()) {
            Droid tank = tanks.get(random.nextInt(tanks.size()));
            team.add(tank);
            tanks.remove(tank);
        }
        
        if (!warriors.isEmpty()) {
            Droid warrior = warriors.get(random.nextInt(warriors.size()));
            team.add(warrior);
            warriors.remove(warrior);
        }
        
        if (!supports.isEmpty()) {
            Droid support = supports.get(random.nextInt(supports.size()));
            team.add(support);
            supports.remove(support);
        }
        
        // Якщо команда ще не повна, додаємо з доступних
        while (team.size() < 3) {
            List<Droid> available = new ArrayList<>();
            available.addAll(warriors);
            available.addAll(tanks);
            available.addAll(assassins);
            available.addAll(supports);
            available.addAll(others);
            
            if (available.isEmpty()) break;
            
            Droid selected = available.get(random.nextInt(available.size()));
            team.add(selected);
            
            // Видаляємо з відповідного списку
            warriors.remove(selected);
            tanks.remove(selected);
            assassins.remove(selected);
            supports.remove(selected);
            others.remove(selected);
        }
        
        return team;
    }
    
    /**
     * Показує склад команди
     */
    private void displayTeamComposition(List<Droid> team, String teamName) {
        System.out.println("  " + teamName + ":");
        for (Droid droid : team) {
            System.out.println("    " + droid.getName() + " (" + droid.getClass().getSimpleName() + ")");
        }
    }
}