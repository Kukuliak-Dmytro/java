package utils;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static String readLine() {
        return scanner.nextLine().trim();
    }
    
    public static int readInt(String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String input = readLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static int readInt(String prompt, int min, int max) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String input = readLine();
            int value = Integer.parseInt(input);
            if (value >= min && value <= max) {
                return value;
            }
            return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static String readString(String prompt) {
        System.out.print(prompt);
        System.out.flush();
        return readLine();
    }
    
    public static float readFloat(String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String input = readLine();
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }
    
    public static float readFloat(String prompt, float min, float max) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String input = readLine();
            float value = Float.parseFloat(input);
            if (value >= min && value <= max) {
                return value;
            }
            return -1.0f;
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }
    
    public static void close() {
        scanner.close();
    }
}

