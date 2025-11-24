package storage;

import model.Rose;
import model.Lily;
import model.Tulip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Bouquet class.
 */
public class BouquetTest {
    
    private Bouquet bouquet;
    private Rose rose;
    private Lily lily;
    private Tulip tulip;
    
    @BeforeEach
    void setUp() {
        // Create fresh instances before each test
        bouquet = new Bouquet();
        
        // Create test flowers
        rose = new Rose();
        rose.name = "Red Rose";
        rose.setPrice(50.0f);
        rose.setFreshness(12.0f);
        rose.setStemLength(30.0f);
        
        lily = new Lily();
        lily.name = "White Lily";
        lily.setPrice(40.0f);
        lily.setFreshness(8.0f);
        lily.setStemLength(25.0f);
        
        tulip = new Tulip();
        tulip.name = "Yellow Tulip";
        tulip.setPrice(30.0f);
        tulip.setFreshness(6.0f);
        tulip.setStemLength(20.0f);
    }
    
    @Test
    void testDefaultConstructor() {
        // Test that default constructor creates empty bouquet
        Bouquet newBouquet = new Bouquet();
        assertTrue(newBouquet.getFlowers().isEmpty());
        assertTrue(newBouquet.getAccessories().isEmpty());
        assertEquals(0.0f, newBouquet.getPrice());
    }
    
    @Test
    void testAddFlower() {
        // Test adding a single flower
        bouquet.addFlower(rose);
        assertEquals(1, bouquet.getFlowers().size());
        assertTrue(bouquet.getFlowers().contains(rose));
    }
    
    @Test
    void testAddMultipleFlowers() {
        // Test adding multiple flowers
        bouquet.addFlower(rose);
        bouquet.addFlower(lily);
        bouquet.addFlower(tulip);
        
        assertEquals(3, bouquet.getFlowers().size());
        assertTrue(bouquet.getFlowers().contains(rose));
        assertTrue(bouquet.getFlowers().contains(lily));
        assertTrue(bouquet.getFlowers().contains(tulip));
    }
    
    @Test
    void testRemoveFlower() {
        // Test removing a flower
        bouquet.addFlower(rose);
        bouquet.addFlower(lily);
        
        bouquet.removeFlower(rose);
        
        assertEquals(1, bouquet.getFlowers().size());
        assertFalse(bouquet.getFlowers().contains(rose));
        assertTrue(bouquet.getFlowers().contains(lily));
    }
    
    @Test
    void testCalculatePriceWithFlowers() {
        // Test price calculation with flowers only
        bouquet.addFlower(rose);  // 50.0f
        bouquet.addFlower(lily);  // 40.0f
        bouquet.addFlower(tulip); // 30.0f
        
        float expectedPrice = 50.0f + 40.0f + 30.0f;
        assertEquals(expectedPrice, bouquet.calculatePrice(), 0.01f);
        assertEquals(expectedPrice, bouquet.getPrice(), 0.01f);
    }
    
    @Test
    void testCalculatePriceWithAccessories() {
        // Test price calculation with accessories
        bouquet.addAccessory("Ribbon", 10.0f);
        bouquet.addAccessory("Wrapper", 5.0f);
        
        float expectedPrice = 10.0f + 5.0f;
        assertEquals(expectedPrice, bouquet.getPrice(), 0.01f);
    }
    
    @Test
    void testCalculatePriceWithFlowersAndAccessories() {
        // Test price calculation with both flowers and accessories
        bouquet.addFlower(rose);  // 50.0f
        bouquet.addFlower(lily);  // 40.0f
        bouquet.addAccessory("Ribbon", 10.0f);
        bouquet.addAccessory("Wrapper", 5.0f);
        
        float expectedPrice = 50.0f + 40.0f + 10.0f + 5.0f;
        assertEquals(expectedPrice, bouquet.getPrice(), 0.01f);
    }
    
    @Test
    void testAddAccessory() {
        // Test adding accessories
        bouquet.addAccessory("Ribbon", 10.0f);
        bouquet.addAccessory("Wrapper", 5.0f);
        
        assertEquals(2, bouquet.getAccessories().size());
        assertTrue(bouquet.getAccessories().contains("Ribbon"));
        assertTrue(bouquet.getAccessories().contains("Wrapper"));
        assertEquals(10.0f, bouquet.getAccessoryPrices().get(0), 0.01f);
        assertEquals(5.0f, bouquet.getAccessoryPrices().get(1), 0.01f);
    }
    
    @Test
    void testSortByFreshness() {
        // Test sorting flowers by freshness (freshest first = lowest freshness value)
        bouquet.addFlower(rose);  // freshness: 12.0f
        bouquet.addFlower(lily);  // freshness: 8.0f
        bouquet.addFlower(tulip); // freshness: 6.0f
        
        bouquet.sortByFreshness();
        
        // After sorting, freshest (lowest value) should be first
        assertEquals(tulip, bouquet.getFlowers().get(0)); // 6.0f
        assertEquals(lily, bouquet.getFlowers().get(1));  // 8.0f
        assertEquals(rose, bouquet.getFlowers().get(2));  // 12.0f
    }
    
    @Test
    void testFindFlowersByStemLength() {
        // Test finding flowers within stem length range
        bouquet.addFlower(rose);  // stemLength: 30.0f
        bouquet.addFlower(lily);  // stemLength: 25.0f
        bouquet.addFlower(tulip); // stemLength: 20.0f
        
        // Find flowers with stem length between 20 and 30
        var result = bouquet.findFlowersByStemLength(20.0f, 30.0f);
        assertEquals(3, result.size());
        
        // Find flowers with stem length between 25 and 30
        result = bouquet.findFlowersByStemLength(25.0f, 30.0f);
        assertEquals(2, result.size());
        assertTrue(result.contains(rose));
        assertTrue(result.contains(lily));
        
        // Find flowers with stem length between 15 and 20
        result = bouquet.findFlowersByStemLength(15.0f, 20.0f);
        assertEquals(1, result.size());
        assertTrue(result.contains(tulip));
    }
    
    @Test
    void testFindFlowersByStemLengthNoMatches() {
        // Test finding flowers when no matches exist
        bouquet.addFlower(rose);  // stemLength: 30.0f
        
        var result = bouquet.findFlowersByStemLength(10.0f, 15.0f);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testEmptyBouquetPrice() {
        // Test that empty bouquet has zero price
        assertEquals(0.0f, bouquet.getPrice(), 0.01f);
        assertEquals(0.0f, bouquet.calculatePrice(), 0.01f);
    }
    
    @Test
    void testPriceRecalculationAfterRemoval() {
        // Test that price is recalculated when flower is removed
        bouquet.addFlower(rose);  // 50.0f
        bouquet.addFlower(lily);   // 40.0f
        assertEquals(90.0f, bouquet.getPrice(), 0.01f);
        
        bouquet.removeFlower(rose);
        assertEquals(40.0f, bouquet.getPrice(), 0.01f);
    }
}



