package utils;

import model.Flower;
import model.Lily;
import model.Rose;
import model.Tulip;
import storage.Bouquet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONUtilTest {

    @Test
    void testToJson() {
        TestObject obj = new TestObject();
        obj.name = "Test";
        obj.value = 42;
        
        String json = JSONUtil.toJson(obj);
        
        assertNotNull(json);
        assertTrue(json.contains("Test"));
        assertTrue(json.contains("42"));
    }

    @Test
    void testFromJson() {
        String json = "{\"name\":\"Test\",\"value\":42}";
        
        TestObject obj = JSONUtil.fromJson(json, TestObject.class);
        
        assertNotNull(obj);
        assertEquals("Test", obj.name);
        assertEquals(42, obj.value);
    }
    
    @Test
    void testRoundTrip() {
        TestObject original = new TestObject();
        original.name = "RoundTrip";
        original.value = 100;
        
        String json = JSONUtil.toJson(original);
        TestObject restored = JSONUtil.fromJson(json, TestObject.class);
        
        assertEquals(original.name, restored.name);
        assertEquals(original.value, restored.value);
    }
    
    // Test Flower serialization/deserialization
    
    @Test
    void testSerializeRose() {
        Rose rose = new Rose();
        rose.name = "Red Rose";
        rose.color = "Red";
        rose.setFreshness(10.0f);
        rose.setStemLength(30.0f);
        rose.setPrice(50.0f);
        rose.hasThorns = true;
        
        String json = JSONUtil.toJson(rose);
        
        assertNotNull(json);
        assertTrue(json.contains("Rose"));
        assertTrue(json.contains("Red Rose"));
        assertTrue(json.contains("true"));
    }
    
    @Test
    void testDeserializeRose() {
        String json = "{\"type\":\"Rose\",\"name\":\"Test Rose\",\"color\":\"Pink\"," +
                      "\"freshness\":12.0,\"stemLength\":35.0,\"price\":55.0,\"hasThorns\":true}";
        
        Flower flower = JSONUtil.fromJson(json, Flower.class);
        
        assertNotNull(flower);
        assertTrue(flower instanceof Rose);
        assertEquals("Test Rose", flower.name);
        assertEquals("Pink", flower.color);
        assertEquals(12.0f, flower.getFreshness());
        assertEquals(35.0f, flower.getStemLength());
        assertEquals(55.0f, flower.getPrice());
        assertTrue(((Rose) flower).hasThorns);
    }
    
    @Test
    void testSerializeLily() {
        Lily lily = new Lily();
        lily.name = "White Lily";
        lily.color = "White";
        lily.setFreshness(15.0f);
        lily.setStemLength(40.0f);
        lily.setPrice(60.0f);
        lily.bloomLevel = 5.0f;
        
        String json = JSONUtil.toJson(lily);
        
        assertNotNull(json);
        assertTrue(json.contains("Lily"));
        assertTrue(json.contains("White Lily"));
        assertTrue(json.contains("5.0"));
    }
    
    @Test
    void testDeserializeLily() {
        String json = "{\"type\":\"Lily\",\"name\":\"Test Lily\",\"color\":\"White\"," +
                      "\"freshness\":15.0,\"stemLength\":40.0,\"price\":60.0,\"bloomLevel\":5.0}";
        
        Flower flower = JSONUtil.fromJson(json, Flower.class);
        
        assertNotNull(flower);
        assertTrue(flower instanceof Lily);
        assertEquals("Test Lily", flower.name);
        assertEquals(5.0f, ((Lily) flower).bloomLevel);
    }
    
    @Test
    void testSerializeTulip() {
        Tulip tulip = new Tulip();
        tulip.name = "Yellow Tulip";
        tulip.color = "Yellow";
        tulip.setFreshness(8.0f);
        tulip.setStemLength(25.0f);
        tulip.setPrice(40.0f);
        tulip.leavesNumber = 3;
        
        String json = JSONUtil.toJson(tulip);
        
        assertNotNull(json);
        assertTrue(json.contains("Tulip"));
        assertTrue(json.contains("Yellow Tulip"));
        assertTrue(json.contains("3"));
    }
    
    @Test
    void testDeserializeTulip() {
        String json = "{\"type\":\"Tulip\",\"name\":\"Test Tulip\",\"color\":\"Yellow\"," +
                      "\"freshness\":8.0,\"stemLength\":25.0,\"price\":40.0,\"leavesNumber\":3}";
        
        Flower flower = JSONUtil.fromJson(json, Flower.class);
        
        assertNotNull(flower);
        assertTrue(flower instanceof Tulip);
        assertEquals("Test Tulip", flower.name);
        assertEquals(3, ((Tulip) flower).leavesNumber);
    }
    
    @Test
    void testSerializeNullFlower() {
        Flower flower = null;
        String json = JSONUtil.toJson(flower);
        
        assertNotNull(json);
        assertEquals("null", json);
    }
    
    @Test
    void testDeserializeNullFlower() {
        String json = "null";
        Flower flower = JSONUtil.fromJson(json, Flower.class);
        
        assertNull(flower);
    }
    
    // Test Bouquet serialization/deserialization
    
    @Test
    void testSerializeBouquet() {
        Bouquet bouquet = new Bouquet();
        
        Rose rose = new Rose();
        rose.name = "Rose";
        bouquet.addFlower(rose);
        
        bouquet.addAccessory("Ribbon", 10.0f);
        
        String json = JSONUtil.toJson(bouquet);
        
        assertNotNull(json);
        assertTrue(json.contains("flowers"));
        assertTrue(json.contains("accessories"));
        assertTrue(json.contains("Ribbon"));
    }
    
    @Test
    void testDeserializeBouquet() {
        String json = "{\"flowers\":[{\"type\":\"Rose\",\"name\":\"Test\",\"color\":\"Red\"," +
                      "\"freshness\":10.0,\"stemLength\":30.0,\"price\":50.0,\"hasThorns\":false}]," +
                      "\"accessories\":[{\"name\":\"Ribbon\",\"price\":10.0}]}";
        
        Bouquet bouquet = JSONUtil.fromJson(json, Bouquet.class);
        
        assertNotNull(bouquet);
        assertEquals(1, bouquet.getFlowers().size());
        assertEquals(1, bouquet.getAccessories().size());
        assertEquals("Ribbon", bouquet.getAccessories().get(0));
    }
    
    @Test
    void testSerializeEmptyBouquet() {
        Bouquet bouquet = new Bouquet();
        String json = JSONUtil.toJson(bouquet);
        
        assertNotNull(json);
        assertTrue(json.contains("flowers"));
        assertTrue(json.contains("accessories"));
    }
    
    @Test
    void testDeserializeEmptyBouquet() {
        String json = "{\"flowers\":[],\"accessories\":[]}";
        Bouquet bouquet = JSONUtil.fromJson(json, Bouquet.class);
        
        assertNotNull(bouquet);
        assertTrue(bouquet.getFlowers().isEmpty());
        assertTrue(bouquet.getAccessories().isEmpty());
    }
    
    @Test
    void testSerializeNullBouquet() {
        Bouquet bouquet = null;
        String json = JSONUtil.toJson(bouquet);
        
        assertNotNull(json);
        assertEquals("null", json);
    }
    
    @Test
    void testDeserializeNullBouquet() {
        String json = "null";
        Bouquet bouquet = JSONUtil.fromJson(json, Bouquet.class);
        
        assertNull(bouquet);
    }
    
    @Test
    void testDeserializeBouquetWithMultipleFlowers() {
        String json = "{\"flowers\":[" +
                      "{\"type\":\"Rose\",\"name\":\"Rose1\",\"color\":\"Red\",\"freshness\":10.0,\"stemLength\":30.0,\"price\":50.0,\"hasThorns\":true}," +
                      "{\"type\":\"Lily\",\"name\":\"Lily1\",\"color\":\"White\",\"freshness\":15.0,\"stemLength\":40.0,\"price\":60.0,\"bloomLevel\":5.0}" +
                      "],\"accessories\":[]}";
        
        Bouquet bouquet = JSONUtil.fromJson(json, Bouquet.class);
        
        assertNotNull(bouquet);
        assertEquals(2, bouquet.getFlowers().size());
        assertTrue(bouquet.getFlowers().get(0) instanceof Rose);
        assertTrue(bouquet.getFlowers().get(1) instanceof Lily);
    }
    
    @Test
    void testDeserializeBouquetWithMultipleAccessories() {
        String json = "{\"flowers\":[]," +
                      "\"accessories\":[" +
                      "{\"name\":\"Ribbon\",\"price\":10.0}," +
                      "{\"name\":\"Card\",\"price\":5.0}," +
                      "{\"name\":\"Wrapper\",\"price\":15.0}" +
                      "]}";
        
        Bouquet bouquet = JSONUtil.fromJson(json, Bouquet.class);
        
        assertNotNull(bouquet);
        assertEquals(3, bouquet.getAccessories().size());
        assertEquals("Ribbon", bouquet.getAccessories().get(0));
        assertEquals("Card", bouquet.getAccessories().get(1));
        assertEquals("Wrapper", bouquet.getAccessories().get(2));
    }
    
    // Helper class for testing
    private static class TestObject {
        String name;
        int value;
    }
}
