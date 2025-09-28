package utils;

import java.util.Scanner;

/**
 * Утилітарний клас для валідації користувацького вводу
 */
public class InputValidator {
    
    /**
     * Валідує ввід цілого числа в заданому діапазоні
     */
    public static int validateIntInput(Scanner scanner, int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Значення повинно бути від " + min + " до " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть правильне число!");
            }
        }
    }
    
    /**
     * Валідує ввід рядка (не порожній)
     */
    public static String validateStringInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Рядок не може бути порожнім!");
            }
        }
    }
    
    /**
     * Валідує так/ні відповідь
     */
    public static boolean validateYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes") || input.equals("так")) {
                return true;
            } else if (input.equals("n") || input.equals("no") || input.equals("ні")) {
                return false;
            } else {
                System.out.println("Введіть 'y' для так або 'n' для ні");
            }
        }
    }
    
    /**
     * Валідує вибір з меню
     */
    public static int validateMenuChoice(Scanner scanner, int maxOption) {
        return validateIntInput(scanner, 0, maxOption, "Ваш вибір (0-" + maxOption + "): ");
    }
}