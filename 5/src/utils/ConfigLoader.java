package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class ConfigLoader {
    private static Map<String, FlowerConfig> configMap = null;
    private static boolean loaded = false;
    
    public static void loadConfig() {
        if (loaded) return;
        
        String configFile = "flower_config.json";
        try (FileReader reader = new FileReader(configFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, FlowerConfig>>(){}.getType();
            configMap = gson.fromJson(reader, type);
            loaded = true;
        } catch (IOException e) {
            // Config file not found - factories will use defaults
            loaded = true;
        }
    }
    
    public static FlowerConfig getConfig(String flowerType) {
        if (!loaded) {
            loadConfig();
        }
        return configMap != null ? configMap.get(flowerType.toLowerCase()) : null;
    }
    
    // Config data class
    public static class FlowerConfig {
        public String name;
        public String color;
        public float freshness;
        public float stemLength;
        public float price;
        
        // Type-specific fields
        public Boolean hasThorns;      // For Rose
        public Float bloomLevel;       // For Lily
        public Integer leavesNumber;   // For Tulip
    }
}
