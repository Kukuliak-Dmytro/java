package factory;

import model.Flower;
import model.Lily;
import model.Rose;
import model.Tulip;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {

    @Test
    void testRoseFactory() {
        AbstractFlowerFactory factory = new RoseFactory();
        Flower flower = factory.createFlower();
        
        assertNotNull(flower);
        assertTrue(flower instanceof Rose);
        assertEquals("rose", factory.getFlowerType());
        
        // Verify properties are set (either default or from config)
        assertNotNull(flower.getName());
        assertTrue(flower.getFreshness() >= 0);
        assertTrue(flower.getStemLength() >= 0);
        assertTrue(flower.getPrice() >= 0);
    }

    @Test
    void testLilyFactory() {
        AbstractFlowerFactory factory = new LilyFactory();
        Flower flower = factory.createFlower();
        
        assertNotNull(flower);
        assertTrue(flower instanceof Lily);
        assertEquals("lily", factory.getFlowerType());
        
        assertNotNull(flower.getName());
        assertTrue(flower.getFreshness() >= 0);
    }

    @Test
    void testTulipFactory() {
        AbstractFlowerFactory factory = new TulipFactory();
        Flower flower = factory.createFlower();
        
        assertNotNull(flower);
        assertTrue(flower instanceof Tulip);
        assertEquals("tulip", factory.getFlowerType());
        
        assertNotNull(flower.getName());
        assertTrue(flower.getFreshness() >= 0);
    }
    
    @Test
    void testRoseFactoryGetInfo() {
        AbstractFlowerFactory factory = new RoseFactory();
        // Just verify it doesn't throw
        assertDoesNotThrow(() -> factory.getInfo());
    }
    
    @Test
    void testLilyFactoryGetInfo() {
        AbstractFlowerFactory factory = new LilyFactory();
        assertDoesNotThrow(() -> factory.getInfo());
    }
    
    @Test
    void testTulipFactoryGetInfo() {
        AbstractFlowerFactory factory = new TulipFactory();
        assertDoesNotThrow(() -> factory.getInfo());
    }
    
    @Test
    void testRoseFactoryWithConfig() {
        // This tests the config loading path
        RoseFactory factory = new RoseFactory();
        Rose rose = (Rose) factory.createFlower();
        
        // If config exists, should have values from config
        // If not, should have defaults
        assertNotNull(rose);
        assertNotNull(rose.getName());
    }
    
    @Test
    void testLilyFactoryWithConfig() {
        LilyFactory factory = new LilyFactory();
        Lily lily = (Lily) factory.createFlower();
        
        assertNotNull(lily);
        assertNotNull(lily.getName());
    }
    
    @Test
    void testTulipFactoryWithConfig() {
        TulipFactory factory = new TulipFactory();
        Tulip tulip = (Tulip) factory.createFlower();
        
        assertNotNull(tulip);
        assertNotNull(tulip.getName());
    }
}
