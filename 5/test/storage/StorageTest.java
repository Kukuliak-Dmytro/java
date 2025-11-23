package storage;

import entities.Flower;
import entities.Rose;
import entities.Lily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Storage class (Singleton pattern).
 * Note: Storage uses singleton pattern and reads from JSON, which may affect test isolation.
 */
public class StorageTest {
    
    private Storage storage;
    private Rose testRose;
    private Lily testLily;
    private Bouquet testBouquet;
    
    @BeforeEach
    void setUp() {
        // Get the singleton instance
        storage = Storage.getInstance();
        
        // Clear storage for test isolation (if possible)
        // Note: In a real scenario, you might want to add a clear/reset method to Storage
        
        // Create test flowers
        testRose = new Rose();
        testRose.name = "Test Rose";
        testRose.setPrice(50.0f);
        
        testLily = new Lily();
        testLily.name = "Test Lily";
        testLily.setPrice(40.0f);
        
        // Create test bouquet
        testBouquet = new Bouquet();
        testBouquet.addFlower(testRose);
    }
    
    @Test
    void testSingletonPattern() {
        // Test that getInstance always returns the same instance
        Storage instance1 = Storage.getInstance();
        Storage instance2 = Storage.getInstance();
        
        assertSame(instance1, instance2, "Storage should be a singleton");
    }
    
    @Test
    void testAddFlower() {
        // Test adding a flower to storage
        int initialSize = storage.getFlowersInStorage().size();
        storage.addFlower(testRose);
        
        assertEquals(initialSize + 1, storage.getFlowersInStorage().size());
        assertTrue(storage.getFlowersInStorage().contains(testRose));
    }
    
    @Test
    void testRemoveFlower() {
        // Test removing a flower from storage
        storage.addFlower(testRose);
        int sizeAfterAdd = storage.getFlowersInStorage().size();
        
        storage.removeFlower(testRose);
        
        assertEquals(sizeAfterAdd - 1, storage.getFlowersInStorage().size());
        assertFalse(storage.getFlowersInStorage().contains(testRose));
    }
    
    @Test
    void testAddBouquet() {
        // Test adding a bouquet to storage
        int initialSize = storage.getBouquetsInStorage().size();
        storage.addBouquet(testBouquet);
        
        assertEquals(initialSize + 1, storage.getBouquetsInStorage().size());
        assertTrue(storage.getBouquetsInStorage().contains(testBouquet));
    }
    
    @Test
    void testRemoveBouquet() {
        // Test removing a bouquet from storage
        storage.addBouquet(testBouquet);
        int sizeAfterAdd = storage.getBouquetsInStorage().size();
        
        storage.removeBouquet(testBouquet);
        
        assertEquals(sizeAfterAdd - 1, storage.getBouquetsInStorage().size());
        assertFalse(storage.getBouquetsInStorage().contains(testBouquet));
    }
    
    @Test
    void testMoveFlowerToBouquet() {
        // Test moving a flower from storage to a bouquet
        storage.addFlower(testRose);
        Bouquet bouquet = new Bouquet();
        storage.addBouquet(bouquet);
        
        int flowersInStorageBefore = storage.getFlowersInStorage().size();
        int flowersInBouquetBefore = bouquet.getFlowers().size();
        
        storage.moveFlowerToBouquet(testRose, bouquet);
        
        // Flower should be removed from storage
        assertEquals(flowersInStorageBefore - 1, storage.getFlowersInStorage().size());
        assertFalse(storage.getFlowersInStorage().contains(testRose));
        
        // Flower should be added to bouquet
        assertEquals(flowersInBouquetBefore + 1, bouquet.getFlowers().size());
        assertTrue(bouquet.getFlowers().contains(testRose));
    }
    
    @Test
    void testMoveFlowerToBouquetWithNullFlower() {
        // Test that moving null flower doesn't cause errors
        Bouquet bouquet = new Bouquet();
        int initialSize = bouquet.getFlowers().size();
        
        storage.moveFlowerToBouquet(null, bouquet);
        
        // Should not add anything
        assertEquals(initialSize, bouquet.getFlowers().size());
    }
    
    @Test
    void testMoveFlowerToBouquetWithNullBouquet() {
        // Test that moving to null bouquet doesn't cause errors
        storage.addFlower(testRose);
        int initialSize = storage.getFlowersInStorage().size();
        
        storage.moveFlowerToBouquet(testRose, null);
        
        // Should not remove anything
        assertEquals(initialSize, storage.getFlowersInStorage().size());
    }
    
    @Test
    void testGetFlowersInStorage() {
        // Test that getFlowersInStorage returns a list
        assertNotNull(storage.getFlowersInStorage());
    }
    
    @Test
    void testGetBouquetsInStorage() {
        // Test that getBouquetsInStorage returns a list
        assertNotNull(storage.getBouquetsInStorage());
    }
}


