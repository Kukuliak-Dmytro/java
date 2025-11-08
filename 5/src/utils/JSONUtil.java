package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtil {
    
    /**
     * Loads JSON content from a file
     * @param filePath Path to the JSON file
     * @return JSON string or null if loading fails
     */
    public static String loadFromJSON(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return json.toString();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Writes JSON content to a file
     * @param filePath Path to the JSON file
     * @param jsonContent JSON string to write
     * @return true if successful, false otherwise
     */
    public static boolean writeToJSON(String filePath, String jsonContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonContent);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Extracts a string value from JSON by key
     * @param json JSON string
     * @param key Key to search for
     * @return String value or "Default" if not found
     */
    public static String getString(String json, String key) {
        String pattern = "\"" + key + "\"";
        int keyPos = json.indexOf(pattern);
        if (keyPos == -1) return "Default";
        
        int colon = json.indexOf(":", keyPos);
        int quoteStart = json.indexOf("\"", colon);
        if (quoteStart == -1) return "Default";
        
        int quoteEnd = json.indexOf("\"", quoteStart + 1);
        if (quoteEnd == -1) return "Default";
        
        return json.substring(quoteStart + 1, quoteEnd);
    }
    
    /**
     * Extracts a float value from JSON by key
     * @param json JSON string
     * @param key Key to search for
     * @return Float value or 0.0f if not found
     */
    public static float getFloat(String json, String key) {
        String pattern = "\"" + key + "\"";
        int keyPos = json.indexOf(pattern);
        if (keyPos == -1) return 0.0f;
        
        int colon = json.indexOf(":", keyPos);
        int valueStart = colon + 1;
        while (valueStart < json.length() && Character.isWhitespace(json.charAt(valueStart))) {
            valueStart++;
        }
        
        int valueEnd = valueStart;
        while (valueEnd < json.length() && 
               (Character.isDigit(json.charAt(valueEnd)) || 
                json.charAt(valueEnd) == '.' || 
                json.charAt(valueEnd) == '-' ||
                json.charAt(valueEnd) == 'E' ||
                json.charAt(valueEnd) == 'e')) {
            valueEnd++;
        }
        
        try {
            return Float.parseFloat(json.substring(valueStart, valueEnd).trim());
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }
    
    /**
     * Finds the matching closing brace for an opening brace
     * @param json JSON string
     * @param start Starting position of the opening brace
     * @return Position of matching closing brace or -1 if not found
     */
    public static int findMatchingBrace(String json, int start) {
        int depth = 0;
        for (int i = start; i < json.length(); i++) {
            if (json.charAt(i) == '{') depth++;
            if (json.charAt(i) == '}') {
                depth--;
                if (depth == 0) return i;
            }
        }
        return -1;
    }
}

