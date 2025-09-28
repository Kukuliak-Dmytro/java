package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Клас для логування боїв та їх збереження у файли
 */
public class BattleLogger {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Зберігає лог бою у файл
     */
    public static boolean saveBattleLog(List<String> battleLog, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Записуємо заголовок
            writer.write("=".repeat(60) + "\n");
            writer.write("            БИТВА ДРОЇДІВ - ЛОГ БОЮ\n");
            writer.write("=".repeat(60) + "\n");
            writer.write("Час створення: " + LocalDateTime.now().format(TIMESTAMP_FORMAT) + "\n");
            writer.write("Кількість записів: " + battleLog.size() + "\n");
            writer.write("=".repeat(60) + "\n\n");
            
            // Записуємо лог
            for (String logEntry : battleLog) {
                writer.write(logEntry + "\n");
            }
            
            // Записуємо кінцівку
            writer.write("\n" + "=".repeat(60) + "\n");
            writer.write("Кінець логу\n");
            writer.write("=".repeat(60) + "\n");
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Помилка при збереженні файлу: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Створює резюме бою
     */
    public static String createBattleSummary(List<String> battleLog, String winner) {
        StringBuilder summary = new StringBuilder();
        
        summary.append("=== РЕЗЮМЕ БОЮ ===\n");
        summary.append("Час: ").append(LocalDateTime.now().format(TIMESTAMP_FORMAT)).append("\n");
        summary.append("Переможець: ").append(winner).append("\n");
        summary.append("Загальна кількість ходів: ").append(countTurns(battleLog)).append("\n");
        summary.append("Тривалість логу: ").append(battleLog.size()).append(" записів\n");
        
        return summary.toString();
    }
    
    /**
     * Підраховує кількість ходів у бою
     */
    private static int countTurns(List<String> battleLog) {
        return (int) battleLog.stream()
            .filter(line -> line.contains("--- Хід"))
            .count();
    }
    
    /**
     * Створює ім'я файлу за замовчуванням
     */
    public static String generateDefaultFilename(String battleType) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        return String.format("battle_%s_%s.txt", battleType.toLowerCase().replace(" ", "_"), timestamp);
    }
    
    /**
     * Валідує ім'я файлу
     */
    public static String validateFilename(String filename) {
        // Видаляємо недозволені символи
        String cleaned = filename.replaceAll("[\\\\/:*?\"<>|]", "_");
        
        // Додаємо розширення, якщо потрібно
        if (!cleaned.endsWith(".txt")) {
            cleaned += ".txt";
        }
        
        return cleaned;
    }
}