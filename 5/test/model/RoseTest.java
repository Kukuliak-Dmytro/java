package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Rose class.
 */
public class RoseTest {
    
    private Rose rose;
    
    @BeforeEach
    void setUp() {
        rose = new Rose();
    }
    
    @Test
    void testRoseCreation() {
        // Test that Rose can be instantiated
        assertNotNull(rose);
        assertTrue(rose instanceof Flower);
    }
    
    @Test
    void testHasThornsProperty() {
        // Test the hasThorns property
        rose.hasThorns = true;
        assertTrue(rose.hasThorns);
        
        rose.hasThorns = false;
        assertFalse(rose.hasThorns);
    }
    
    @Test
    void testRemoveThornsMethod() {
        // Test that removeThorns method doesn't throw exceptions
        assertDoesNotThrow(() -> rose.removeThorns());
    }
    
    @Test
    void testDisplayMethod() {
        // Test that display method works correctly with thorns
        rose.name = "Red Rose";
        rose.color = "Red";
        rose.setFreshness(12.0f);
        rose.setStemLength(30.0f);
        rose.setPrice(50.0f);
        rose.hasThorns = true;
        
        // Verify display doesn't throw
        assertDoesNotThrow(() -> rose.display());
    }
    
    @Test
    void testInheritance() {
        // Test that Rose inherits Flower properties
        rose.name = "Test Rose";
        rose.setPrice(50.0f);
        rose.setFreshness(24.0f);
        rose.setStemLength(30.0f);
        
        assertEquals("Test Rose", rose.getName());
        assertEquals(50.0f, rose.getPrice());
        assertEquals(24.0f, rose.getFreshness());
        assertEquals(30.0f, rose.getStemLength());
    }
}


