package utils;

import org.junit.jupiter.api.Test;
import utils.ConfigLoader.FlowerConfig;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {

    @Test
    void testLoadConfig() {
        // Load config
        ConfigLoader.loadConfig();
        
        // Should not throw
        assertDoesNotThrow(() -> ConfigLoader.loadConfig());
    }

    @Test
    void testGetConfigForRose() {
        ConfigLoader.loadConfig();
        FlowerConfig config = ConfigLoader.getConfig("rose");
        
        // Config might be null if file doesn't exist, or have values if it does
        // Either way, the method should not throw
        assertDoesNotThrow(() -> ConfigLoader.getConfig("rose"));
    }

    @Test
    void testGetConfigForLily() {
        ConfigLoader.loadConfig();
        FlowerConfig config = ConfigLoader.getConfig("lily");
        
        assertDoesNotThrow(() -> ConfigLoader.getConfig("lily"));
    }

    @Test
    void testGetConfigForTulip() {
        ConfigLoader.loadConfig();
        FlowerConfig config = ConfigLoader.getConfig("tulip");
        
        assertDoesNotThrow(() -> ConfigLoader.getConfig("tulip"));
    }

    @Test
    void testGetConfigForNonexistent() {
        ConfigLoader.loadConfig();
        FlowerConfig config = ConfigLoader.getConfig("nonexistent");
        
        // Should return null for non-existent flower type
        assertNull(config);
    }

    @Test
    void testLoadConfigMultipleTimes() {
        // Loading multiple times should be safe (idempotent)
        ConfigLoader.loadConfig();
        ConfigLoader.loadConfig();
        ConfigLoader.loadConfig();
        
        // Should not throw
        assertDoesNotThrow(() -> ConfigLoader.loadConfig());
    }
    
    @Test
    void testFlowerConfigClass() {
        // Test the inner FlowerConfig class directly
        ConfigLoader.FlowerConfig config = new ConfigLoader.FlowerConfig();
        
        assertNotNull(config);
        assertNull(config.name);
        assertNull(config.color);
        assertEquals(0.0f, config.freshness);
        assertEquals(0.0f, config.stemLength);
        assertEquals(0.0f, config.price);
        assertNull(config.hasThorns);
        assertNull(config.bloomLevel);
        assertNull(config.leavesNumber);
    }
    
    @Test
    void testFlowerConfigWithValues() {
        ConfigLoader.FlowerConfig config = new ConfigLoader.FlowerConfig();
        
        config.name = "Test Rose";
        config.color = "Red";
        config.freshness = 48.0f;
        config.stemLength = 35.0f;
        config.price = 20.0f;
        config.hasThorns = true;
        
        assertEquals("Test Rose", config.name);
        assertEquals("Red", config.color);
        assertEquals(48.0f, config.freshness);
        assertEquals(35.0f, config.stemLength);
        assertEquals(20.0f, config.price);
        assertTrue(config.hasThorns);
    }
    
    @Test
    void testFlowerConfigSerialization() {
        ConfigLoader.FlowerConfig config = new ConfigLoader.FlowerConfig();
        config.name = "Lily";
        config.color = "White";
        config.freshness = 36.0f;
        config.stemLength = 40.0f;
        config.price = 25.0f;
        config.bloomLevel = 0.8f;
        
        // Serialize and deserialize
        String json = JSONUtil.toJson(config);
        assertNotNull(json);
        
        ConfigLoader.FlowerConfig restored = JSONUtil.fromJson(json, ConfigLoader.FlowerConfig.class);
        assertNotNull(restored);
        assertEquals("Lily", restored.name);
        assertEquals(0.8f, restored.bloomLevel);
    }
}
