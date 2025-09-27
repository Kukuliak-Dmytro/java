/**
 * Головна програма, що демонструє функціональність телефонної системи
 */
public class Main {

    /**
     * Створює зразки операторів з різними тарифами мережі
     * @return масив об'єктів операторів
     */
    public static Carrier[] createCarriers() {
        Carrier[] carriers = new Carrier[4];

        carriers[0] = new Carrier("Київстар", "КС", 0.02, 0.75);
        carriers[1] = new Carrier("Водафон", "ВФ", 0.03, 0.85);
        carriers[2] = new Carrier("Лайфселл", "ЛС", 0.025, 0.80);

        return carriers;
    }

    /**
     * Створює зразки телефонів з різними конфігураціями
     * @param carriers доступні варіанти операторів
     * @return масив об'єктів телефонів
     */
    public static Phone[] createPhones(Carrier[] carriers) {
        Phone[] phones = new Phone[4];

        phones[0] = new Phone(1, "Андрій", "Шевченко", "Миколайович", "555-0101",
                1500.0, 3000.0, 2000.0, carriers[0]);

        phones[1] = new Phone(2, "Олена", "Коваль", null, "555-0102",
                2000.0, 2500.0, 1800.0, carriers[0]);

        phones[2] = new Phone(3, "Тарас", "Бондар", "Володимирович", "555-0103",
                1800.0, 4000.0, 3000.0, carriers[1]);

        phones[3] = new Phone(4, "Ірина", "Мельник", "Маріївна", "555-0104",
                2200.0, 3500.0, 2500.0, carriers[2]);

        return phones;
    }

    /**
     * Запускає комплексну демонстрацію всіх функцій телефонної системи
     */
    public static void runDemo() {
        System.out.println("=== ДЕМОНСТРАЦІЯ ТЕЛЕФОННОЇ СИСТЕМИ ===\n");

        Carrier[] carriers = createCarriers();
        Phone[] phones = createPhones(carriers);

        System.out.println("=== ПОЧАТКОВИЙ СТАН РАХУНКУ ===");
        phones[0].displayAccountStatus();
        System.out.println();

        System.out.println("=== ЗДІЙСНЕННЯ ДЗВІНКІВ ===");

        System.out.println("1. Дзвінок у межах одного оператора:");
        phones[0].initiateCall(phones[1], 300.0);
        System.out.println();

        System.out.println("2. Дзвінок між операторами:");
        phones[0].initiateCall(phones[2], 120.0);
        System.out.println();

        System.out.println("3. Дзвінок при недостатньому балансі:");
        phones[0].initiateCall(phones[3], 2000.0);
        System.out.println();

        System.out.println("4. Поповнення балансу та повторна спроба:");
        phones[0].addBalance(500.0);
        phones[0].initiateCall(phones[3], 100.0);
        System.out.println();

        System.out.println("=== ДЕМОНСТРАЦІЯ ЗМІНИ ОПЕРАТОРА ===");
        phones[1].switchCarrier(carriers[2]);
        phones[0].initiateCall(phones[1], 60.0);
        System.out.println();

        System.out.println("=== ДОДАВАННЯ ХВИЛИН ===");
        phones[0].addPrivateNetworkSeconds(1000.0);
        phones[0].addPublicNetworkSeconds(500.0);
        System.out.println();

        System.out.println("=== КІНЦЕВИЙ СТАН РАХУНКУ ===");
        phones[0].displayAccountStatus();

        System.out.println("=== ПЕРЕВІРКА ДОСТУПНОСТІ ДЗВІНКА ===");
        if (phones[0].canMakeCall(phones[2], 200.0)) {
            System.out.println("Можна здійснити дзвінок тривалістю 200 секунд до " + phones[2].getFullName());
        } else {
            System.out.println("Неможливо здійснити дзвінок тривалістю 200 секунд до " + phones[2].getFullName());
        }
    }

    /**
     * Головна точка входу для демонстрації телефонної системи
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        runDemo();
    }
}
