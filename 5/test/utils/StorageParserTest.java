package utils;

import model.Flower;
import model.Lily;
import model.Rose;
import model.Tulip;
import storage.Bouquet;
import storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageParserTest {

    private static final String TEST_JSON_FILE = "parser_test_storage.json";
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getInstance();
        storage.getFlowersInStorage().clear();
        storage.getBouquetsInStorage().clear();
        storage.setJsonFilePath(TEST_JSON_FILE);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_JSON_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testSerializeAndParseAllFlowerTypes() {
        // Create one of each flower type
        Rose rose = new Rose();
        rose.name = "Red Rose";
        rose.color = "Red";
        rose.setFreshness(10.0f);
        rose.setStemLength(30.0f);
        rose.setPrice(50.0f);
        rose.hasThorns = true;
        
        Lily lily = new Lily();
        lily.name = "White Lily";
        lily.color = "White";
        lily.setFreshness(15.0f);
        lily.setStemLength(40.0f);
        lily.setPrice(60.0f);
        lily.bloomLevel = 5.0f;
        
        Tulip tulip = new Tulip();
        tulip.name = "Yellow Tulip";
        tulip.color = "Yellow";
        tulip.setFreshness(8.0f);
        tulip.setStemLength(25.0f);
        tulip.setPrice(40.0f);
        tulip.leavesNumber = 3;
        
        storage.addFlower(rose);
        storage.addFlower(lily);
        storage.addFlower(tulip);
        
        // Write to JSON
        storage.writeToJson();
        
        // Clear and Read back
        storage.getFlowersInStorage().clear();
        storage.readFromJson();
        
        List<Flower> flowers = storage.getFlowersInStorage();
        assertEquals(3, flowers.size());
        
        // Verify Rose
        assertTrue(flowers.get(0) instanceof Rose);
        Rose loadedRose = (Rose) flowers.get(0);
        assertEquals("Red Rose", loadedRose.name);
        assertEquals("Red", loadedRose.color);
        assertEquals(10.0f, loadedRose.getFreshness());
        assertEquals(30.0f, loadedRose.getStemLength());
        assertEquals(50.0f, loadedRose.getPrice());
        assertTrue(loadedRose.hasThorns);
        
        // Verify Lily
        assertTrue(flowers.get(1) instanceof Lily);
        Lily loadedLily = (Lily) flowers.get(1);
        assertEquals("White Lily", loadedLily.name);
        assertEquals(5.0f, loadedLily.bloomLevel);
        
        // Verify Tulip
        assertTrue(flowers.get(2) instanceof Tulip);
        Tulip loadedTulip = (Tulip) flowers.get(2);
        assertEquals("Yellow Tulip", loadedTulip.name);
        assertEquals(3, loadedTulip.leavesNumber);
    }

    @Test
    void testSerializeAndParseBouquetWithAccessories() {
        Bouquet bouquet = new Bouquet();
        
        Rose rose = new Rose();
        rose.name = "Bouquet Rose";
        rose.color = "Pink";
        rose.setFreshness(12.0f);
        rose.setStemLength(35.0f);
        rose.setPrice(55.0f);
        bouquet.addFlower(rose);
        
        bouquet.addAccessory("Ribbon", 15.0f);
        bouquet.addAccessory("Card", 5.0f);
        
        storage.addBouquet(bouquet);
        
        // Write
        storage.writeToJson();
        
        // Clear and Read
        storage.getBouquetsInStorage().clear();
        storage.readFromJson();
        
        List<Bouquet> bouquets = storage.getBouquetsInStorage();
        assertEquals(1, bouquets.size());
        
        Bouquet loadedBouquet = bouquets.get(0);
        assertEquals(1, loadedBouquet.getFlowers().size());
        assertEquals("Bouquet Rose", loadedBouquet.getFlowers().get(0).name);
        
        assertEquals(2, loadedBouquet.getAccessories().size());
        assertEquals("Ribbon", loadedBouquet.getAccessories().get(0));
        assertEquals(15.0f, loadedBouquet.getAccessoryPrices().get(0));
        assertEquals("Card", loadedBouquet.getAccessories().get(1));
        assertEquals(5.0f, loadedBouquet.getAccessoryPrices().get(1));
    }
    
    @Test
    void testParseEmptyFile() {
        // Create empty file
        try {
            new File(TEST_JSON_FILE).createNewFile();
        } catch (Exception e) {
            fail("Could not create test file");
        }
        
        // Should handle gracefully
        assertDoesNotThrow(() -> storage.readFromJson());
        assertTrue(storage.getFlowersInStorage().isEmpty());
        assertTrue(storage.getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testParseNonexistentFile() {
        storage.setJsonFilePath("nonexistent_file_12345.json");
        
        // Should handle gracefully
        assertDoesNotThrow(() -> storage.readFromJson());
        assertTrue(storage.getFlowersInStorage().isEmpty());
    }
    
    @Test
    void testSerializeEmptyStorage() {
        // Write empty storage
        storage.writeToJson();
        
        // Read back
        storage.readFromJson();
        
        assertTrue(storage.getFlowersInStorage().isEmpty());
        assertTrue(storage.getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testSerializeBouquetWithMultipleFlowers() {
        Bouquet bouquet = new Bouquet();
        
        for (int i = 0; i < 5; i++) {
            Rose rose = new Rose();
            rose.name = "Rose " + i;
            rose.setFreshness(10.0f + i);
            bouquet.addFlower(rose);
        }
        
        storage.addBouquet(bouquet);
        storage.writeToJson();
        
        storage.getBouquetsInStorage().clear();
        storage.readFromJson();
        
        assertEquals(1, storage.getBouquetsInStorage().size());
        assertEquals(5, storage.getBouquetsInStorage().get(0).getFlowers().size());
    }
    
    @Test
    void testSerializeMultipleBouquets() {
        for (int i = 0; i < 3; i++) {
            Bouquet bouquet = new Bouquet();
            Rose rose = new Rose();
            rose.name = "Rose in Bouquet " + i;
            bouquet.addFlower(rose);
            storage.addBouquet(bouquet);
        }
        
        storage.writeToJson();
        storage.getBouquetsInStorage().clear();
        storage.readFromJson();
        
        assertEquals(3, storage.getBouquetsInStorage().size());
    }
    
    @Test
    void testSerializeNullFlower() {
        // Add null flower (should be handled)
        storage.addFlower(null);
        
        assertDoesNotThrow(() -> storage.writeToJson());
    }
    
    @Test
    void testSerializeNullBouquet() {
        // Add null bouquet (should be handled)
        storage.addBouquet(null);
        
        assertDoesNotThrow(() -> storage.writeToJson());
    }
    
    @Test
    void testStorageDataClass() {
        // Test the inner StorageData class directly
        StorageParser.StorageData data = new StorageParser.StorageData();
        
        assertNotNull(data);
        assertNull(data.flowers);
        assertNull(data.bouquets);
        
        // Set values
        data.flowers = storage.getFlowersInStorage();
        data.bouquets = storage.getBouquetsInStorage();
        
        assertNotNull(data.flowers);
        assertNotNull(data.bouquets);
    }
    
    @Test
    void testStorageDataSerialization() {
        StorageParser.StorageData data = new StorageParser.StorageData();
        
        Rose rose = new Rose();
        rose.name = "Test";
        storage.addFlower(rose);
        
        data.flowers = storage.getFlowersInStorage();
        data.bouquets = storage.getBouquetsInStorage();
        
        // Serialize and deserialize
        String json = JSONUtil.toJson(data);
        assertNotNull(json);
        
        StorageParser.StorageData restored = JSONUtil.fromJson(json, StorageParser.StorageData.class);
        assertNotNull(restored);
        assertNotNull(restored.flowers);
        assertEquals(1, restored.flowers.size());
    }
}
