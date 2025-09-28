package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для управління файловими операціями
 */
public class FileManager {
    /** Директорія для збереження файлів */
    private static final String SAVE_DIRECTORY = "saves/";
    
    /**
     * Ініціалізує директорію для збережень
     */
    static {
        createSaveDirectory();
    }
    
    /**
     * Створює директорію для збережень, якщо вона не існує
     */
    private static void createSaveDirectory() {
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
    }
    
    /**
     * Зберігає текст у файл
     */
    public static boolean saveToFile(String content, String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write(content);
            System.out.println("✓ Файл збережено: " + fullPath);
            return true;
            
        } catch (IOException e) {
            System.err.println("✗ Помилка при збереженні файлу: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Завантажує текст з файлу
     */
    public static List<String> loadFromFile(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("✓ Файл завантажено: " + fullPath + " (" + lines.size() + " рядків)");
            
        } catch (IOException e) {
            System.err.println("✗ Помилка при завантаженні файлу: " + e.getMessage());
        }
        
        return lines;
    }
    
    /**
     * Перевіряє, чи існує файл
     */
    public static boolean fileExists(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        return new File(fullPath).exists();
    }
    
    /**
     * Видаляє файл
     */
    public static boolean deleteFile(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        File file = new File(fullPath);
        
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("✓ Файл видалено: " + fullPath);
            } else {
                System.err.println("✗ Не вдалося видалити файл: " + fullPath);
            }
            return deleted;
        } else {
            System.err.println("✗ Файл не знайдено: " + fullPath);
            return false;
        }
    }
    
    /**
     * Отримує список всіх збережених файлів
     */
    public static List<String> listSaveFiles() {
        File saveDir = new File(SAVE_DIRECTORY);
        List<String> files = new ArrayList<>();
        
        if (saveDir.exists() && saveDir.isDirectory()) {
            File[] fileList = saveDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            
            if (fileList != null) {
                for (File file : fileList) {
                    files.add(file.getName());
                }
            }
        }
        
        return files;
    }
    
    /**
     * Отримує розмір файлу в байтах
     */
    public static long getFileSize(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        File file = new File(fullPath);
        
        if (file.exists()) {
            return file.length();
        } else {
            return -1;
        }
    }
    
    /**
     * Створює резервну копію файлу
     */
    public static boolean backupFile(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        String backupPath = SAVE_DIRECTORY + "backup_" + filename;
        
        try (FileInputStream input = new FileInputStream(fullPath);
             FileOutputStream output = new FileOutputStream(backupPath)) {
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            
            System.out.println("✓ Резервну копію створено: " + backupPath);
            return true;
            
        } catch (IOException e) {
            System.err.println("✗ Помилка при створенні резервної копії: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Очищує старі файли (старші за вказану кількість днів)
     */
    public static void cleanOldFiles(int daysOld) {
        File saveDir = new File(SAVE_DIRECTORY);
        
        if (saveDir.exists() && saveDir.isDirectory()) {
            File[] files = saveDir.listFiles();
            
            if (files != null) {
                long cutoffTime = System.currentTimeMillis() - (daysOld * 24L * 60L * 60L * 1000L);
                int deletedCount = 0;
                
                for (File file : files) {
                    if (file.isFile() && file.lastModified() < cutoffTime) {
                        if (file.delete()) {
                            deletedCount++;
                        }
                    }
                }
                
                if (deletedCount > 0) {
                    System.out.println("✓ Очищено " + deletedCount + " старих файлів");
                }
            }
        }
    }
    
    /**
     * Показує інформацію про директорію збережень
     */
    public static void showSaveDirectoryInfo() {
        File saveDir = new File(SAVE_DIRECTORY);
        
        if (saveDir.exists()) {
            File[] files = saveDir.listFiles();
            int fileCount = (files != null) ? files.length : 0;
            
            long totalSize = 0;
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        totalSize += file.length();
                    }
                }
            }
            
            System.out.println("=== ІНФОРМАЦІЯ ПРО ДИРЕКТОРІЮ ЗБЕРЕЖЕНЬ ===");
            System.out.println("Шлях: " + saveDir.getAbsolutePath());
            System.out.println("Кількість файлів: " + fileCount);
            System.out.println("Загальний розмір: " + formatFileSize(totalSize));
            
        } else {
            System.out.println("Директорія збережень не існує");
        }
    }
    
    /**
     * Форматує розмір файлу для зручного читання
     */
    private static String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " байт";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f КБ", bytes / 1024.0);
        } else {
            return String.format("%.1f МБ", bytes / (1024.0 * 1024.0));
        }
    }
}