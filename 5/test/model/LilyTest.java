package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Lily class.
 */
public class LilyTest {
    
    private Lily lily;
    
    @BeforeEach
    void setUp() {
        lily = new Lily();
    }
    
    @Test
    void testLilyCreation() {
        // Test that Lily can be instantiated
        assertNotNull(lily);
        assertTrue(lily instanceof Flower);
    }
    
    @Test
    void testBloomLevelProperty() {
        // Test the bloomLevel property (0 to 1)
        lily.bloomLevel = 0.0f;
        assertEquals(0.0f, lily.bloomLevel);
        
        lily.bloomLevel = 0.5f;
        assertEquals(0.5f, lily.bloomLevel);
        
        lily.bloomLevel = 1.0f;
        assertEquals(1.0f, lily.bloomLevel);
    }
    
    @Test
    void testDisplayMethod() {
        // Test that display method works correctly with bloom level
        lily.name = "White Lily";
        lily.color = "White";
        lily.setFreshness(8.0f);
        lily.setStemLength(25.0f);
        lily.setPrice(40.0f);
        lily.bloomLevel = 0.75f;
        
        // Verify display doesn't throw
        assertDoesNotThrow(() -> lily.display());
    }
    
    @Test
    void testInheritance() {
        // Test that Lily inherits Flower properties
        lily.name = "Test Lily";
        lily.setPrice(40.0f);
        lily.setFreshness(12.0f);
        lily.setStemLength(25.0f);
        
        assertEquals("Test Lily", lily.getName());
        assertEquals(40.0f, lily.getPrice());
        assertEquals(12.0f, lily.getFreshness());
        assertEquals(25.0f, lily.getStemLength());
    }
}


