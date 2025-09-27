import droids.*;

/**
 * Головний клас гри "Битва дроїдів"
 * Тестуємо розширену систему дроїдів з новими класами та ефектами
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== ТЕСТ РОЗШИРЕНОЇ СИСТЕМИ ДРОЇДІВ ===\n");

        // Створюємо базових дроїдів
        WarriorDroid warrior = new WarriorDroid("Воїн-X1");
        TankDroid tank = new TankDroid("Танк-Т7");
        AssassinDroid assassin = new AssassinDroid("Ніндзя-А3");

        // Створюємо нових магічних дроїдів
        PyromancerDroid pyro = new PyromancerDroid("Піромант-F5");
        MageAssassinDroid mageAss = new MageAssassinDroid("Маг-Убивця-M9");
        ElementalGuardianDroid guardian = new ElementalGuardianDroid("Елем.Страж-G2");

        System.out.println("=== ІНФОРМАЦІЯ ПРО НОВИХ ДРОЇДІВ ===");
        System.out.println(pyro.getInfo());
        System.out.println();
        System.out.println(mageAss.getInfo());
        System.out.println();
        System.out.println(guardian.getInfo());
        System.out.println();

        // Тест 1: Піромант проти танка
        System.out.println("=== ТЕСТ 1: ПІРОМАНТ VS ТАНК ===");
        System.out.println("Піромант атакує танк (тест підпалення):");
        for (int i = 0; i < 4; i++) {
            System.out.println("\n--- Атака " + (i + 1) + " ---");
            pyro.attack(tank);
            
            // Оновлюємо ефекти після кожної атаки
            System.out.println("Оновлення ефектів танка:");
            tank.updateEffects();
            System.out.println("HP танка: " + tank.getHealth() + "/" + tank.getMaxHealth());
        }

        // Тест вогняного вибуху піроманта
        if (pyro.canUseFireBlast()) {
            System.out.println("\n--- СПЕЦІАЛЬНА АТАКА ПІРОМАНТА ---");
            pyro.fireBlast(tank);
        }

        // Піромант входить в лють при низькому HP
        if (pyro.getHealth() < pyro.getMaxHealth() * 0.5) {
            System.out.println("HP піроманта: " + pyro.getHealth() + "/" + pyro.getMaxHealth() + " (може активувати лють)");
        }

        System.out.println();

        // Тест 2: Маг-асасин проти воїна
        System.out.println("=== ТЕСТ 2: МАГ-АСАСИН VS ВОЇН ===");
        System.out.println("Маг-асасин атакує воїна (тест комбо ефектів):");
        for (int i = 0; i < 3; i++) {
            System.out.println("\n--- Атака " + (i + 1) + " ---");
            mageAss.attack(warrior);
            
            // Воїн атакує у відповідь (тест ухиляння мага)
            System.out.println("Воїн атакує у відповідь:");
            warrior.attack(mageAss);
            
            // Оновлюємо ефекти
            System.out.println("Оновлення ефектів воїна:");
            warrior.updateEffects();
            System.out.println("HP воїна: " + warrior.getHealth() + "/" + warrior.getMaxHealth());
            System.out.println("HP маг-асасина: " + mageAss.getHealth() + "/" + mageAss.getMaxHealth());
        }

        // Тест подвійного удару маг-асасина
        if (mageAss.canUseToxicFireStrike()) {
            System.out.println("\n--- ПОДВІЙНИЙ УДАР МАГ-АСАСИНА ---");
            mageAss.toxicFireStrike(warrior);
        }

        System.out.println();

        // Тест 3: Елементальний страж
        System.out.println("=== ТЕСТ 3: ЕЛЕМЕНТАЛЬНИЙ СТРАЖ ===");
        System.out.println("Асасин атакує стража (тест блокування):");
        for (int i = 0; i < 5; i++) {
            System.out.println("\n--- Атака " + (i + 1) + " ---");
            assassin.attack(guardian);
            System.out.println("HP стража: " + guardian.getHealth() + "/" + guardian.getMaxHealth());
        }

        // Тест елементальної підтримки
        System.out.println("\n--- ЕЛЕМЕНТАЛЬНА ПІДТРИМКА ---");
        if (guardian.canUseElementalSupport()) {
            guardian.elementalSupport(pyro);
            System.out.println("Піромант отримав підтримку. HP піроманта: " + pyro.getHealth() + "/" + pyro.getMaxHealth());
        }

        // Тест елементального вибуху
        if (guardian.canUseElementalBurst()) {
            System.out.println("\n--- ЕЛЕМЕНТАЛЬНИЙ ВИБУХ ---");
            guardian.elementalBurst(assassin);
        }

        System.out.println();

        // Тест 4: Комплексні ефекти
        System.out.println("=== ТЕСТ 4: КОМПЛЕКСНІ ЕФЕКТИ ===");
        System.out.println("Симуляція кількох ходів з ефектами:");

        // Піромант підпалює маг-асасина
        pyro.attack(mageAss);

        // Маг-асасин отруює піроманта
        mageAss.attack(pyro);

        // Оновлюємо ефекти протягом 4 ходів
        for (int turn = 1; turn <= 4; turn++) {
            System.out.println("\n=== ХІД " + turn + " ===");
            System.out.println("Оновлення ефектів піроманта:");
            pyro.updateEffects();
            System.out.println("HP піроманта: " + pyro.getHealth() + "/" + pyro.getMaxHealth());
            
            System.out.println("Оновлення ефектів маг-асасина:");
            mageAss.updateEffects();
            System.out.println("HP маг-асасина: " + mageAss.getHealth() + "/" + mageAss.getMaxHealth());
        }

        System.out.println();

        // Тестуємо різницю в точності
        System.out.println("=== ТЕСТ РІЗНИЦІ В ТОЧНОСТІ ===");
        WarriorDroid testWarrior = new WarriorDroid("Тест-Воїн");
        TankDroid testTarget = new TankDroid("Ціль");
        AssassinDroid testSniper = new AssassinDroid("Тест-Снайпер");

        System.out.println("Порівняння точності при 5 атаках:");
        System.out.println();

        // Тест воїна (точність 85%)
        System.out.println("Воїн (85% точність) атакує 5 разів:");
        for (int i = 1; i <= 5; i++) {
            testWarrior.attack(testTarget);
            testTarget.heal(50); // Відновлюємо HP для наступного тесту
        }
        System.out.println();

        // Тест асасина (точність 95%)
        System.out.println("Асасин (95% точність) атакує 5 разів:");
        for (int i = 1; i <= 5; i++) {
            testSniper.attack(testTarget);
            testTarget.heal(50); // Відновлюємо HP для наступного тесту
        }
        System.out.println();

        // Тестуємо спеціальну атаку асасина
        System.out.println("=== ТЕСТ СПЕЦІАЛЬНОЇ АТАКИ З ОТРУЄННЯМ ===");
        AssassinDroid venomAssassin = new AssassinDroid("Отруйник");
        TankDroid poisonTarget = new TankDroid("Жертва");

        System.out.println("Спеціальна атака асасина:");
        venomAssassin.venomStrike(poisonTarget);

        // Імітуємо кілька ходів для перевірки отрути
        for (int turn = 1; turn <= 4; turn++) {
            System.out.println("\n--- Хід " + turn + " ---");
            poisonTarget.updateEffects();
            System.out.println(poisonTarget.getStatus());
        }

        System.out.println();

        // Фінальний стан
        System.out.println("=== ФІНАЛЬНИЙ СТАН ВСІХ ДРОЇДІВ ===");
        System.out.println(warrior.getName() + ": " + warrior.getHealth() + "/" + warrior.getMaxHealth() + " HP");
        System.out.println(tank.getName() + ": " + tank.getHealth() + "/" + tank.getMaxHealth() + " HP");
        System.out.println(assassin.getName() + ": " + assassin.getHealth() + "/" + assassin.getMaxHealth() + " HP");
        System.out.println(pyro.getName() + ": " + pyro.getHealth() + "/" + pyro.getMaxHealth() + " HP");
        System.out.println(mageAss.getName() + ": " + mageAss.getHealth() + "/" + mageAss.getMaxHealth() + " HP");
        System.out.println(guardian.getName() + ": " + guardian.getHealth() + "/" + guardian.getMaxHealth() + " HP");

        System.out.println("\n=== ТЕСТУВАННЯ ЗАВЕРШЕНО ===");
    }
}