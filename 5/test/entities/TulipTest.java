package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tulip class.
 */
public class TulipTest {
    
    private Tulip tulip;
    
    @BeforeEach
    void setUp() {
        tulip = new Tulip();
    }
    
    @Test
    void testTulipCreation() {
        // Test that Tulip can be instantiated
        assertNotNull(tulip);
        assertTrue(tulip instanceof Flower);
    }
    
    @Test
    void testLeavesNumberProperty() {
        // Test the leavesNumber property
        tulip.leavesNumber = 0;
        assertEquals(0, tulip.leavesNumber);
        
        tulip.leavesNumber = 5;
        assertEquals(5, tulip.leavesNumber);
        
        tulip.leavesNumber = 10;
        assertEquals(10, tulip.leavesNumber);
    }
    
    @Test
    void testTrimLeavesMethod() {
        // Test that trimLeaves method doesn't throw exceptions
        assertDoesNotThrow(() -> tulip.trimLeaves());
    }
    
    @Test
    void testDisplayMethod() {
        // Test that display method works correctly with leaves number
        tulip.name = "Yellow Tulip";
        tulip.color = "Yellow";
        tulip.setFreshness(6.0f);
        tulip.setStemLength(20.0f);
        tulip.setPrice(30.0f);
        tulip.leavesNumber = 6;
        
        // Verify display doesn't throw
        assertDoesNotThrow(() -> tulip.display());
    }
    
    @Test
    void testInheritance() {
        // Test that Tulip inherits Flower properties
        tulip.name = "Test Tulip";
        tulip.setPrice(30.0f);
        tulip.setFreshness(10.0f);
        tulip.setStemLength(20.0f);
        
        assertEquals("Test Tulip", tulip.getName());
        assertEquals(30.0f, tulip.getPrice());
        assertEquals(10.0f, tulip.getFreshness());
        assertEquals(20.0f, tulip.getStemLength());
    }
}

