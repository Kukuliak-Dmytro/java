package battle;

import droids.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Основний клас для управління всіма типами боїв у грі "Битва дроїдів"
 */
public class Battle {
    private InitiativeSystem initiative;
    private Random random;
    private List<String> battleLog;
    private boolean battleEnded;
    private String battleResult;
    
    public Battle() {
        this.initiative = new InitiativeSystem();
        this.random = new Random();
        this.battleLog = new ArrayList<>();
        this.battleEnded = false;
    }
    
    /**
     * Бій 1 на 1 між двома дроїдами
     */
    public String oneOnOneBattle(Droid droid1, Droid droid2) {
        log("=== БІЙ 1 НА 1 ===");
        log(droid1.getName() + " vs " + droid2.getName());
        log("");
        
        // Показуємо характеристики
        log("Характеристики бійців:");
        log(droid1.getInfo());
        log("");
        log(droid2.getInfo());
        log("");
        
        // Ініціалізуємо систему ініціативи
        initiative.reset();
        initiative.addParticipant(droid1);
        initiative.addParticipant(droid2);
        
        log("Початок бою!");
        log(initiative.getSpeedStatistics());
        log("");
        
        int turn = 1;
        while (droid1.isAlive() && droid2.isAlive() && turn <= 100) { // Максимум 100 ходів
            log("--- Хід " + turn + " ---");
            
            Droid currentFighter = initiative.getNextDroid();
            if (currentFighter == null) break;
            
            Droid target = (currentFighter == droid1) ? droid2 : droid1;
            
            processTurn(currentFighter, target);
            
            log("Стан після ходу:");
            log("  " + droid1.getName() + ": " + droid1.getHealth() + "/" + droid1.getMaxHealth() + " HP");
            log("  " + droid2.getName() + ": " + droid2.getHealth() + "/" + droid2.getMaxHealth() + " HP");
            log("");
            
            turn++;
        }
        
        // Визначаємо переможця
        String winner;
        if (droid1.isAlive() && droid2.isAlive()) {
            winner = "Нічия (досягнуто максимальну кількість ходів)";
        } else if (droid1.isAlive()) {
            winner = droid1.getName() + " перемагає!";
        } else {
            winner = droid2.getName() + " перемагає!";
        }
        
        log("=== РЕЗУЛЬТАТ ===");
        log(winner);
        battleResult = winner;
        battleEnded = true;
        
        return winner;
    }
    
    /**
     * Командний бій (команда проти команди)
     */
    public String teamBattle(List<Droid> team1, List<Droid> team2) {
        log("=== КОМАНДНИЙ БІЙ ===");
        log("Команда 1: " + team1.stream().map(Droid::getName).collect(Collectors.joining(", ")));
        log("Команда 2: " + team2.stream().map(Droid::getName).collect(Collectors.joining(", ")));
        log("");
        
        // Показуємо характеристики команд
        log("=== КОМАНДА 1 ===");
        for (Droid droid : team1) {
            log(droid.getInfo());
            log("");
        }
        
        log("=== КОМАНДА 2 ===");
        for (Droid droid : team2) {
            log(droid.getInfo());
            log("");
        }
        
        // Ініціалізуємо систему ініціативи
        initiative.reset();
        initiative.addParticipants(team1);
        initiative.addParticipants(team2);
        
        log("Початок командного бою!");
        log(initiative.getSpeedStatistics());
        log("");
        
        int turn = 1;
        while (hasAliveDroids(team1) && hasAliveDroids(team2) && turn <= 200) { // Максимум 200 ходів
            log("--- Хід " + turn + " ---");
            
            Droid currentFighter = initiative.getNextDroid();
            if (currentFighter == null) break;
            
            // Визначаємо ворожу команду та цілі
            List<Droid> enemyTeam = team1.contains(currentFighter) ? team2 : team1;
            List<Droid> allyTeam = team1.contains(currentFighter) ? team1 : team2;
            
            // Обробляємо хід з урахуванням типу дроїда
            processTeamTurn(currentFighter, allyTeam, enemyTeam);
            
            // Показуємо стан команд
            displayTeamStatus(team1, team2);
            
            turn++;
        }
        
        // Визначаємо переможця
        String winner;
        if (hasAliveDroids(team1) && hasAliveDroids(team2)) {
            winner = "Нічия (досягнуто максимальну кількість ходів)";
        } else if (hasAliveDroids(team1)) {
            winner = "Команда 1 перемагає!";
        } else {
            winner = "Команда 2 перемагає!";
        }
        
        log("=== РЕЗУЛЬТАТ ===");
        log(winner);
        battleResult = winner;
        battleEnded = true;
        
        return winner;
    }
    
    /**
     * Обробляє хід дроїда в командному бою
     */
    private void processTeamTurn(Droid currentFighter, List<Droid> allies, List<Droid> enemies) {
        // Оновлюємо ефекти
        currentFighter.updateEffects();
        
        if (!currentFighter.isAlive()) {
            log(currentFighter.getName() + " помер від ефектів!");
            return;
        }
        
        log(currentFighter.getName() + " (" + currentFighter.getClass().getSimpleName() + ") робить хід");
        
        // Різна логіка для різних типів дроїдів
        if (currentFighter instanceof SupportDroid) {
            processSupportDroidTurn((SupportDroid) currentFighter, allies, enemies);
        } else {
            processAttackerDroidTurn(currentFighter, enemies);
        }
    }
    
    /**
     * Обробляє хід Support дроїда
     */
    private void processSupportDroidTurn(SupportDroid supporter, List<Droid> allies, List<Droid> enemies) {
        // Support дроїди намагаються використати спеціальні здібності
        if (supporter.canUseAbility()) {
            log("  → " + supporter.getName() + " використовує підтримуючі здібності");
            supporter.useSpecialAbility(allies, enemies);
        } else {
            // Якщо не можуть використати здібності, атакують
            log("  → " + supporter.getName() + " не може використати здібності, атакує");
            Droid target = selectRandomAliveTarget(enemies);
            if (target != null) {
                int damage = supporter.attack(target);
                log("    " + supporter.getName() + " атакує " + target.getName() + 
                   " і завдає " + damage + " урону");
            }
        }
    }
    
    /**
     * Обробляє хід атакуючого дроїда
     */
    private void processAttackerDroidTurn(Droid attacker, List<Droid> enemies) {
        Droid target = selectRandomAliveTarget(enemies);
        if (target != null) {
            int damage = attacker.attack(target);
            log("  " + attacker.getName() + " атакує " + target.getName() + 
               " і завдає " + damage + " урону");
            
            if (!target.isAlive()) {
                log("  " + target.getName() + " знищений!");
            }
        }
    }
    
    /**
     * Обробляє хід у бою 1 на 1
     */
    private void processTurn(Droid attacker, Droid target) {
        // Оновлюємо ефекти
        attacker.updateEffects();
        
        if (!attacker.isAlive()) {
            log(attacker.getName() + " помер від ефектів!");
            return;
        }
        
        log(attacker.getName() + " атакує " + target.getName());
        int damage = attacker.attack(target);
        log("Завдано " + damage + " урону!");
        
        if (!target.isAlive()) {
            log(target.getName() + " знищений!");
        }
    }
    
    /**
     * Вибирає випадкову живу ціль зі списку ворогів
     */
    private Droid selectRandomAliveTarget(List<Droid> enemies) {
        List<Droid> aliveEnemies = enemies.stream()
            .filter(Droid::isAlive)
            .collect(Collectors.toList());
            
        if (aliveEnemies.isEmpty()) {
            return null;
        }
        
        return aliveEnemies.get(random.nextInt(aliveEnemies.size()));
    }
    
    /**
     * Перевіряє, чи є живі дроїди в команді
     */
    private boolean hasAliveDroids(List<Droid> team) {
        return team.stream().anyMatch(Droid::isAlive);
    }
    
    /**
     * Показує стан команд
     */
    private void displayTeamStatus(List<Droid> team1, List<Droid> team2) {
        log("Стан команд:");
        log("  Команда 1:");
        for (Droid droid : team1) {
            if (droid.isAlive()) {
                String manaInfo = droid instanceof SupportDroid ? 
                    " (мана: " + ((SupportDroid)droid).getCurrentMana() + "/" + 
                    ((SupportDroid)droid).getMaxMana() + ")" : "";
                log("    " + droid.getName() + ": " + droid.getHealth() + "/" + 
                   droid.getMaxHealth() + " HP" + manaInfo);
            } else {
                log("    " + droid.getName() + ": ЗНИЩЕНИЙ");
            }
        }
        
        log("  Команда 2:");
        for (Droid droid : team2) {
            if (droid.isAlive()) {
                String manaInfo = droid instanceof SupportDroid ? 
                    " (мана: " + ((SupportDroid)droid).getCurrentMana() + "/" + 
                    ((SupportDroid)droid).getMaxMana() + ")" : "";
                log("    " + droid.getName() + ": " + droid.getHealth() + "/" + 
                   droid.getMaxHealth() + " HP" + manaInfo);
            } else {
                log("    " + droid.getName() + ": ЗНИЩЕНИЙ");
            }
        }
        log("");
    }
    
    /**
     * Додає запис до логу бою
     */
    private void log(String message) {
        battleLog.add(message);
        System.out.println(message);
    }
    
    /**
     * Отримує повний лог бою
     */
    public List<String> getBattleLog() {
        return new ArrayList<>(battleLog);
    }
    
    /**
     * Очищає лог для нового бою
     */
    public void clearLog() {
        battleLog.clear();
        battleEnded = false;
        battleResult = null;
    }
    
    /**
     * Перевіряє, чи закінчився бій
     */
    public boolean isBattleEnded() {
        return battleEnded;
    }
    
    /**
     * Отримує результат бою
     */
    public String getBattleResult() {
        return battleResult;
    }
    
    /**
     * Зберігає лог бою у файл
     */
    public void saveBattleLog(String filename) {
        // Тут буде реалізація збереження у файл
        // Поки що просто повідомляємо
        System.out.println("Лог бою збережено у файл: " + filename);
        System.out.println("Збережено " + battleLog.size() + " записів");
    }
    
    /**
     * Завантажує лог бою з файлу
     */
    public void loadBattleLog(String filename) {
        // Тут буде реалізація завантаження з файлу
        // Поки що просто повідомляємо
        System.out.println("Завантаження логу бою з файлу: " + filename);
        System.out.println("Функція буде реалізована пізніше");
    }
    
    /**
     * Відтворює збережений бій
     */
    public void replayBattle() {
        System.out.println("=== ВІДТВОРЕННЯ БОЮ ===");
        for (String logEntry : battleLog) {
            System.out.println(logEntry);
            try {
                Thread.sleep(100); // Пауза між записами
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}