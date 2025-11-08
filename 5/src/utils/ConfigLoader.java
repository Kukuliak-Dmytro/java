package utils;

public class ConfigLoader {
    private static String configJson = null;
    private static boolean loaded = false;
    
    public static void loadConfig() {
        if (loaded) return;
        
        String configFile = "flower_config.json";
        configJson = JSONUtil.loadFromJSON(configFile);
        loaded = true;
    }
    
    public static String getConfig() {
        return configJson;
    }
}

