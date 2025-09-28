package menu;

import droids.*;
import battle.Battle;
import battle.AutomaticDuel;
import java.util.*;

/**
 * Консольне меню гри "Битва дроїдів"
 * Надає доступ до всіх функцій гри через зручний інтерфейс
 */
public class GameMenu {
    /** Scanner для читання вводу користувача */
    private Scanner scanner;
    /** Список всіх створених дроїдів */
    private List<Droid> droidList;
    /** Система боїв */
    private Battle battle;
    /** Система автоматичних дуелів */
    private AutomaticDuel automaticDuel;
    
    /**
     * Створює новий екземпляр головного меню гри
     * Ініціалізує всі необхідні компоненти та створює дроїдів за замовчуванням
     */
    public GameMenu() {
        this.scanner = new Scanner(System.in);
        this.droidList = new ArrayList<>();
        this.battle = new Battle();
        this.automaticDuel = new AutomaticDuel();
        
        // Створюємо кілька дроїдів за замовчуванням
        createDefaultDroids();
    }
    
    /**
     * Головне меню гри
     */
    public void showMainMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         БИТВА ДРОЇДІВ v2.0            ║");
        System.out.println("║      Консольна гра на Java            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        while (true) {
            System.out.println("\n=== ГОЛОВНЕ МЕНЮ ===");
            System.out.println("1.  Створити дроїда");
            System.out.println("2.  Показати список дроїдів");
            System.out.println("3.  Бій 1 на 1");
            System.out.println("4.  Командний бій");
            System.out.println("5.  Автоматичний дуель 1v1");
            System.out.println("6.  Автоматичний дуель 3v3");
            System.out.println("7.  Серія автоматичних дуелів");
            System.out.println("8.  Турнір");
            System.out.println("9.  Збалансований дуель 3v3");
            System.out.println("10. Зберегти бій у файл");
            System.out.println("11. Завантажити бій з файлу");
            System.out.println("12. Показати статистику дроїдів");
            System.out.println("13. Видалити дроїда");
            System.out.println("14. Інформація про гру");
            System.out.println("0.  Вихід");
            
            System.out.print("\nВиберіть опцію (0-14): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1: createDroid(); break;
                    case 2: showDroidList(); break;
                    case 3: startOneOnOneBattle(); break;
                    case 4: startTeamBattle(); break;
                    case 5: startAutomaticDuel1v1(); break;
                    case 6: startAutomaticDuel3v3(); break;
                    case 7: startDuelSeries(); break;
                    case 8: startTournament(); break;
                    case 9: startBalancedDuel3v3(); break;
                    case 10: saveBattle(); break;
                    case 11: loadBattle(); break;
                    case 12: showStatistics(); break;
                    case 13: deleteDroid(); break;
                    case 14: showGameInfo(); break;
                    case 0: 
                        System.out.println("Дякуємо за гру! До побачення!");
                        return;
                    default:
                        System.out.println("Невірний вибір! Спробуйте ще раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть число від 0 до 14!");
            }
        }
    }
    
    /**
     * Створює нового дроїда
     */
    private void createDroid() {
        System.out.println("\n=== СТВОРЕННЯ ДРОЇДА ===");
        System.out.println("Виберіть тип дроїда:");
        System.out.println("1. WarriorDroid - воїн (високий урон, середнє здоров'я)");
        System.out.println("2. TankDroid - танк (високе здоров'я, низький урон)");
        System.out.println("3. AssassinDroid - assassin (дуже високий урон, низьке здоров'я)");
        System.out.println("4. MedicDroid - медик (лікування союзників)");
        System.out.println("5. ShieldDroid - щитоносець (створення щитів)");
        System.out.println("6. BufferDroid - підсилювач (підвищення характеристик)");
        System.out.println("7. DefenderDroid - захисник (ослаблення ворогів)");
        System.out.println("8. PyromancerDroid - піромант (вогняні атаки)");
        System.out.println("9. ElementalGuardianDroid - елементаль (універсальний)");
        System.out.println("10. MageAssassinDroid - маг-убивця (магічні атаки)");
        System.out.println("0. Повернутися до меню");
        
        System.out.print("Ваш вибір: ");
        
        try {
            int type = Integer.parseInt(scanner.nextLine());
            
            if (type == 0) return;
            
            System.out.print("Введіть ім'я дроїда: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                name = "Дроїд-" + (droidList.size() + 1);
            }
            
            // Перевіряємо унікальність імені
            if (isDroidNameExists(name)) {
                System.out.println("Дроїд з таким іменем вже існує!");
                return;
            }
            
            Droid newDroid = createDroidByType(type, name);
            
            if (newDroid != null) {
                droidList.add(newDroid);
                System.out.println("\n✓ Дроїда успішно створено!");
                System.out.println(newDroid.getInfo());
            } else {
                System.out.println("Невірний тип дроїда!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Будь ласка, введіть правильний номер!");
        }
    }
    
    /**
     * Створює дроїда за типом
     */
    private Droid createDroidByType(int type, String name) {
        switch (type) {
            case 1: return new WarriorDroid(name);
            case 2: return new TankDroid(name);
            case 3: return new AssassinDroid(name);
            case 4: return new MedicDroid(name);
            case 5: return new ShieldDroid(name);
            case 6: return new BufferDroid(name);
            case 7: return new DefenderDroid(name);
            case 8: return new PyromancerDroid(name);
            case 9: return new ElementalGuardianDroid(name);
            case 10: return new MageAssassinDroid(name);
            default: return null;
        }
    }
    
    /**
     * Показує список всіх дроїдів
     */
    private void showDroidList() {
        System.out.println("\n=== СПИСОК ДРОЇДІВ ===");
        
        if (droidList.isEmpty()) {
            System.out.println("Список дроїдів порожній. Створіть дроїдів перед грою!");
            return;
        }
        
        for (int i = 0; i < droidList.size(); i++) {
            Droid droid = droidList.get(i);
            System.out.println("\n" + (i + 1) + ". " + droid.getName() + 
                             " (" + droid.getClass().getSimpleName() + ")");
            System.out.println("   HP: " + droid.getHealth() + "/" + droid.getMaxHealth() + 
                             ", Урон: " + droid.getDamage() + 
                             ", Швидкість: " + droid.getSpeed());
            
            if (droid instanceof SupportDroid) {
                SupportDroid support = (SupportDroid) droid;
                System.out.println("   Мана: " + support.getCurrentMana() + "/" + support.getMaxMana());
                System.out.println("   Здібність: " + support.getAbilityDescription());
            }
        }
        
        System.out.println("\nВсього дроїдів: " + droidList.size());
    }
    
    /**
     * Запускає бій 1 на 1
     */
    private void startOneOnOneBattle() {
        System.out.println("\n=== БІЙ 1 НА 1 ===");
        
        if (droidList.size() < 2) {
            System.out.println("Для бою потрібно мінімум 2 дроїди!");
            return;
        }
        
        System.out.println("Виберіть першого бійця:");
        Droid fighter1 = selectDroid();
        if (fighter1 == null) return;
        
        System.out.println("Виберіть другого бійця:");
        Droid fighter2 = selectDroid(fighter1);
        if (fighter2 == null) return;
        
        // Підтверджуємо вибір
        System.out.println("\nБій між: " + fighter1.getName() + " vs " + fighter2.getName());
        System.out.print("Розпочати бій? (y/n): ");
        
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            // Відновлюємо здоров'я перед боєм
            resetDroidForBattle(fighter1);
            resetDroidForBattle(fighter2);
            
            battle.clearLog();
            String result = battle.oneOnOneBattle(fighter1, fighter2);
            System.out.println("\n🏆 Результат: " + result);
        }
    }
    
    /**
     * Запускає командний бій
     */
    private void startTeamBattle() {
        System.out.println("\n=== КОМАНДНИЙ БІЙ ===");
        
        if (droidList.size() < 2) {
            System.out.println("Для командного бою потрібно мінімум 2 дроїди!");
            return;
        }
        
        // Формуємо команди
        List<Droid> team1 = new ArrayList<>();
        List<Droid> team2 = new ArrayList<>();
        
        System.out.println("Формування Команди 1:");
        formTeam(team1, "Команда 1");
        
        if (team1.isEmpty()) return;
        
        System.out.println("\nFormування Команди 2:");
        formTeam(team2, "Команда 2", team1);
        
        if (team2.isEmpty()) return;
        
        // Підтверджуємо склад команд
        System.out.println("\nКоманда 1: " + getTeamNames(team1));
        System.out.println("Команда 2: " + getTeamNames(team2));
        System.out.print("Розпочати командний бій? (y/n): ");
        
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            // Відновлюємо здоров'я команд
            team1.forEach(this::resetDroidForBattle);
            team2.forEach(this::resetDroidForBattle);
            
            battle.clearLog();
            String result = battle.teamBattle(team1, team2);
            System.out.println("\n🏆 Результат: " + result);
        }
    }
    
    /**
     * Запускає автоматичний дуель 1v1
     */
    private void startAutomaticDuel1v1() {
        if (droidList.size() < 2) {
            System.out.println("Для автоматичного дуелю потрібно мінімум 2 дроїди!");
            return;
        }
        
        System.out.println("\n=== АВТОМАТИЧНИЙ ДУЕЛЬ 1v1 ===");
        String result = automaticDuel.startDuel1v1(droidList);
        System.out.println("🏆 " + result);
    }
    
    /**
     * Запускає автоматичний дуель 3v3
     */
    private void startAutomaticDuel3v3() {
        if (droidList.size() < 6) {
            System.out.println("Для дуелю 3v3 потрібно мінімум 6 дроїдів!");
            return;
        }
        
        System.out.println("\n=== АВТОМАТИЧНИЙ ДУЕЛЬ 3v3 ===");
        String result = automaticDuel.startDuel3v3(droidList);
        System.out.println("🏆 " + result);
    }
    
    /**
     * Запускає серію дуелів
     */
    private void startDuelSeries() {
        if (droidList.size() < 2) {
            System.out.println("Для серії дуелів потрібно мінімум 2 дроїди!");
            return;
        }
        
        System.out.print("Введіть кількість дуелів (1-50): ");
        try {
            int count = Integer.parseInt(scanner.nextLine());
            if (count < 1 || count > 50) {
                System.out.println("Кількість дуелів повинна бути від 1 до 50!");
                return;
            }
            
            automaticDuel.startDuelSeries1v1(droidList, count);
        } catch (NumberFormatException e) {
            System.out.println("Будь ласка, введіть правильне число!");
        }
    }
    
    /**
     * Запускає турнір
     */
    private void startTournament() {
        if (droidList.size() < 3) {
            System.out.println("Для турніру потрібно мінімум 3 дроїди!");
            return;
        }
        
        System.out.println("\n=== ТУРНІР (кожен проти кожного) ===");
        System.out.println("Буде проведено " + (droidList.size() * (droidList.size() - 1) / 2) + " матчів");
        System.out.print("Продовжити? (y/n): ");
        
        if (scanner.nextLine().toLowerCase().startsWith("y")) {
            automaticDuel.startTournament(droidList);
        }
    }
    
    /**
     * Запускає збалансований дуель 3v3
     */
    private void startBalancedDuel3v3() {
        if (droidList.size() < 6) {
            System.out.println("Для збалансованого дуелю 3v3 потрібно мінімум 6 дроїдів!");
            return;
        }
        
        automaticDuel.createBalancedDuel3v3(droidList);
    }
    
    /**
     * Зберігає бій у файл
     */
    private void saveBattle() {
        System.out.print("Введіть ім'я файлу для збереження (без розширення): ");
        String filename = scanner.nextLine().trim();
        
        if (filename.isEmpty()) {
            filename = "battle_" + System.currentTimeMillis();
        }
        
        battle.saveBattleLog(filename + ".txt");
    }
    
    /**
     * Завантажує бій з файлу
     */
    private void loadBattle() {
        System.out.print("Введіть ім'я файлу для завантаження: ");
        String filename = scanner.nextLine().trim();
        
        if (!filename.isEmpty()) {
            battle.loadBattleLog(filename);
        }
    }
    
    /**
     * Показує статистику дроїдів
     */
    private void showStatistics() {
        System.out.println("\n=== СТАТИСТИКА ДРОЇДІВ ===");
        
        if (droidList.isEmpty()) {
            System.out.println("Немає дроїдів для показу статистики!");
            return;
        }
        
        // Групуємо за типами
        Map<String, List<Droid>> byType = new HashMap<>();
        for (Droid droid : droidList) {
            String type = droid.getClass().getSimpleName();
            byType.computeIfAbsent(type, k -> new ArrayList<>()).add(droid);
        }
        
        System.out.println("Всього дроїдів: " + droidList.size());
        System.out.println("\nРозподіл за типами:");
        
        for (Map.Entry<String, List<Droid>> entry : byType.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue().size() + 
                             " (" + String.format("%.1f", (double)entry.getValue().size()/droidList.size()*100) + "%)");
        }
        
        // Показуємо найсильніших дроїдів
        System.out.println("\nТоп-3 за силою атаки:");
        droidList.stream()
            .sorted((d1, d2) -> Integer.compare(d2.getDamage(), d1.getDamage()))
            .limit(3)
            .forEach(d -> System.out.println("  " + d.getName() + " - " + d.getDamage() + " урону"));
            
        System.out.println("\nТоп-3 за здоров'ям:");
        droidList.stream()
            .sorted((d1, d2) -> Integer.compare(d2.getMaxHealth(), d1.getMaxHealth()))
            .limit(3)
            .forEach(d -> System.out.println("  " + d.getName() + " - " + d.getMaxHealth() + " HP"));
            
        System.out.println("\nТоп-3 за швидкістю:");
        droidList.stream()
            .sorted((d1, d2) -> Integer.compare(d2.getSpeed(), d1.getSpeed()))
            .limit(3)
            .forEach(d -> System.out.println("  " + d.getName() + " - " + d.getSpeed() + " швидкості"));
    }
    
    /**
     * Видаляє дроїда зі списку
     */
    private void deleteDroid() {
        if (droidList.isEmpty()) {
            System.out.println("Список дроїдів порожній!");
            return;
        }
        
        System.out.println("\n=== ВИДАЛЕННЯ ДРОЇДА ===");
        showDroidList();
        
        System.out.print("Введіть номер дроїда для видалення (0 - скасувати): ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (index == -1) return;
            
            if (index >= 0 && index < droidList.size()) {
                Droid removed = droidList.remove(index);
                System.out.println("✓ Дроїда " + removed.getName() + " видалено!");
            } else {
                System.out.println("Невірний номер дроїда!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Будь ласка, введіть правильний номер!");
        }
    }
    
    /**
     * Показує інформацію про гру
     */
    private void showGameInfo() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        БИТВА ДРОЇДІВ v2.0                        ║");
        System.out.println("║                   Консольна гра на Java                          ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                   ║");
        System.out.println("║  Гра включає в себе:                                             ║");
        System.out.println("║  • 10 різних типів дроїдів з унікальними здібностями             ║");
        System.out.println("║  • Систему ефектів (підсилення, ослаблення, щити)               ║");
        System.out.println("║  • Покрокову систему бою на основі швидкості                     ║");
        System.out.println("║  • Бої 1v1 та командні бої                                       ║");
        System.out.println("║  • Автоматичні дуелі та турніри                                  ║");
        System.out.println("║  • Систему мани для Support дроїдів                              ║");
        System.out.println("║                                                                   ║");
        System.out.println("║  Типи дроїдів:                                                   ║");
        System.out.println("║  • Warrior - високий урон, середнє HP                            ║");
        System.out.println("║  • Tank - високе HP, низький урон                                ║");
        System.out.println("║  • Assassin - дуже високий урон, низьке HP                       ║");
        System.out.println("║  • Medic - лікує союзників                                       ║");
        System.out.println("║  • Shield - створює захисні щити                                 ║");
        System.out.println("║  • Buffer - підсилює характеристики                              ║");
        System.out.println("║  • Defender - ослаблює ворогів                                   ║");
        System.out.println("║  • Pyromancer - вогняні атаки                                    ║");
        System.out.println("║  • ElementalGuardian - елементальна магія                        ║");
        System.out.println("║  • MageAssassin - магічні атаки                                  ║");
        System.out.println("║                                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }
    
    // Допоміжні методи
    
    /**
     * Вибирає дроїда зі списку
     */
    private Droid selectDroid() {
        return selectDroid(null);
    }
    
    /**
     * Вибирає дроїда, виключаючи вказаного
     */
    private Droid selectDroid(Droid exclude) {
        showAvailableDroids(exclude);
        
        System.out.print("Введіть номер дроїда (0 - скасувати): ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (index == -1) return null;
            
            List<Droid> available = getAvailableDroids(exclude);
            if (index >= 0 && index < available.size()) {
                return available.get(index);
            } else {
                System.out.println("Невірний номер дроїда!");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Будь ласка, введіть правильний номер!");
            return null;
        }
    }
    
    /**
     * Показує доступних дроїдів для вибору
     */
    private void showAvailableDroids(Droid exclude) {
        List<Droid> available = getAvailableDroids(exclude);
        
        System.out.println("Доступні дроїди:");
        for (int i = 0; i < available.size(); i++) {
            Droid droid = available.get(i);
            System.out.println((i + 1) + ". " + droid.getName() + 
                             " (" + droid.getClass().getSimpleName() + 
                             ") HP: " + droid.getHealth() + "/" + droid.getMaxHealth());
        }
    }
    
    /**
     * Отримує список доступних дроїдів
     */
    private List<Droid> getAvailableDroids(Droid exclude) {
        return droidList.stream()
            .filter(d -> d != exclude)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Формує команду для командного бою
     */
    private void formTeam(List<Droid> team, String teamName) {
        formTeam(team, teamName, new ArrayList<>());
    }
    
    /**
     * Формує команду, виключаючи дроїдів з іншої команди
     */
    private void formTeam(List<Droid> team, String teamName, List<Droid> excludeTeam) {
        System.out.println("Формування " + teamName);
        System.out.println("Додавайте дроїдів до команди (введіть 0 для завершення):");
        
        while (team.size() < droidList.size()) {
            List<Droid> available = droidList.stream()
                .filter(d -> !team.contains(d) && !excludeTeam.contains(d))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                
            if (available.isEmpty()) {
                System.out.println("Немає доступних дроїдів для додавання!");
                break;
            }
            
            System.out.println("\nПоточна команда: " + getTeamNames(team));
            System.out.println("Доступні дроїди:");
            
            for (int i = 0; i < available.size(); i++) {
                Droid droid = available.get(i);
                System.out.println((i + 1) + ". " + droid.getName() + 
                                 " (" + droid.getClass().getSimpleName() + ")");
            }
            
            System.out.print("Виберіть дроїда (0 - закінчити): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                if (choice == 0) break;
                
                if (choice > 0 && choice <= available.size()) {
                    team.add(available.get(choice - 1));
                    System.out.println("✓ Дроїда додано до команди!");
                } else {
                    System.out.println("Невірний вибір!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть правильний номер!");
            }
        }
    }
    
    /**
     * Отримує імена дроїдів команди
     */
    private String getTeamNames(List<Droid> team) {
        if (team.isEmpty()) return "порожня";
        
        return team.stream()
            .map(Droid::getName)
            .reduce((a, b) -> a + ", " + b)
            .orElse("порожня");
    }
    
    /**
     * Відновлює дроїда для бою
     */
    private void resetDroidForBattle(Droid droid) {
        droid.heal(droid.getMaxHealth());
        // Тут можна додати очищення ефектів та відновлення мани
    }
    
    /**
     * Перевіряє, чи існує дроїд з таким іменем
     */
    private boolean isDroidNameExists(String name) {
        return droidList.stream().anyMatch(d -> d.getName().equals(name));
    }
    
    /**
     * Створює дроїдів за замовчуванням для демонстрації
     */
    private void createDefaultDroids() {
        droidList.add(new WarriorDroid("Воїн-Альфа"));
        droidList.add(new TankDroid("Танк-Бета"));
        droidList.add(new AssassinDroid("Ассасін-Гамма"));
        droidList.add(new MedicDroid("Медик-Дельта"));
        droidList.add(new ShieldDroid("Щит-Епсілон"));
        droidList.add(new BufferDroid("Бафер-Дзета"));
        droidList.add(new DefenderDroid("Захисник-Ета"));
        droidList.add(new PyromancerDroid("Піромант-Тета"));
    }
}