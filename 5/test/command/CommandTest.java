package command;

import model.Flower;
import model.Rose;
import model.Lily;
import storage.Bouquet;
import storage.Storage;
import utils.InputUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    private static final String TEST_JSON_FILE = "test_storage.json";

    @BeforeEach
    void setUp() {
        // Clear storage
        Storage.getInstance().getFlowersInStorage().clear();
        Storage.getInstance().getBouquetsInStorage().clear();
        CommandHistory.getInstance().clear();
        
        // Reset input stream to System.in just in case
        InputUtil.setInputStream(System.in);
        
        // Set test storage file
        Storage.getInstance().setJsonFilePath(TEST_JSON_FILE);
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test file
        File file = new File(TEST_JSON_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    private void provideInput(String data) {
        InputStream testIn = new ByteArrayInputStream(data.getBytes());
        InputUtil.setInputStream(testIn);
    }

    @Test
    void testCreateFlowerCommand() {
        provideInput("3\n");
        CreateFlowerCommand command = new CreateFlowerCommand();
        command.execute();
        
        List<Flower> flowers = Storage.getInstance().getFlowersInStorage();
        assertEquals(1, flowers.size());
        assertTrue(flowers.get(0) instanceof Rose);
    }

    @Test
    void testCreateBouquetCommand() {
        Flower flower = new Rose();
        Storage.getInstance().addFlower(flower);
        
        provideInput("1\n1\n2\n");
        CreateBouquetCommand command = new CreateBouquetCommand();
        command.execute();
        
        List<Bouquet> bouquets = Storage.getInstance().getBouquetsInStorage();
        assertEquals(1, bouquets.size());
        assertEquals(0, Storage.getInstance().getFlowersInStorage().size());
        assertEquals(1, bouquets.get(0).getFlowers().size());
    }
    
    @Test
    void testAddFlowerToBouquetCommand() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        Flower flower = new Rose();
        Storage.getInstance().addFlower(flower);
        
        provideInput("1\n1\n");
        AddFlowerToBouquetCommand command = new AddFlowerToBouquetCommand();
        command.execute();
        
        assertEquals(0, Storage.getInstance().getFlowersInStorage().size());
        assertEquals(1, bouquet.getFlowers().size());
    }

    @Test
    void testRemoveFlowerCommand() {
        Flower flower = new Rose();
        Storage.getInstance().addFlower(flower);
        
        provideInput("1\n");
        RemoveFlowerCommand command = new RemoveFlowerCommand();
        command.execute();
        
        assertTrue(Storage.getInstance().getFlowersInStorage().isEmpty());
    }

    @Test
    void testRemoveFlowerFromBouquetCommand() {
        Bouquet bouquet = new Bouquet();
        Flower flower = new Rose();
        bouquet.addFlower(flower);
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n1\n");
        RemoveFlowerFromBouquetCommand command = new RemoveFlowerFromBouquetCommand();
        command.execute();
        
        assertTrue(bouquet.getFlowers().isEmpty());
        assertEquals(1, Storage.getInstance().getFlowersInStorage().size());
    }

    @Test
    void testSortBouquetByFreshnessCommand() {
        Bouquet bouquet = new Bouquet();
        Flower freshFlower = new Rose();
        freshFlower.setFreshness(10.0f);
        Flower oldFlower = new Lily();
        oldFlower.setFreshness(100.0f);
        
        bouquet.addFlower(oldFlower);
        bouquet.addFlower(freshFlower);
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n");
        SortBouquetByFreshnessCommand command = new SortBouquetByFreshnessCommand();
        command.execute();
        
        assertEquals(freshFlower, bouquet.getFlowers().get(0));
        assertEquals(oldFlower, bouquet.getFlowers().get(1));
    }

    @Test
    void testUndoCommand() {
        provideInput("3\n");
        CreateFlowerCommand createCmd = new CreateFlowerCommand();
        createCmd.execute();
        
        assertFalse(Storage.getInstance().getFlowersInStorage().isEmpty());
        
        UndoCommand undoCmd = new UndoCommand();
        undoCmd.execute();
        
        assertTrue(Storage.getInstance().getFlowersInStorage().isEmpty());
    }

    @Test
    void testAddAccessoryToBouquetCommand() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n1\n");
        AddAccessoryToBouquetCommand command = new AddAccessoryToBouquetCommand();
        command.execute();
        
        assertFalse(bouquet.getAccessories().isEmpty());
        assertEquals("Ribbon", bouquet.getAccessories().get(0));
    }
    
    @Test
    void testDisplayCommands() {
        // Just verify they don't throw exceptions
        Storage.getInstance().addFlower(new Rose());
        Storage.getInstance().addBouquet(new Bouquet());
        CommandHistory.getInstance().push(new CreateFlowerCommand());
        
        assertDoesNotThrow(() -> new DisplayFlowersCommand().execute());
        assertDoesNotThrow(() -> new DisplayBouquetsCommand().execute());
        assertDoesNotThrow(() -> new DisplayHistoryCommand().execute());
    }
    
    @Test
    void testFindFlowersByStemLengthCommand() {
        Bouquet bouquet = new Bouquet();
        Flower shortFlower = new Rose();
        shortFlower.setStemLength(10.0f);
        Flower longFlower = new Lily();
        longFlower.setStemLength(50.0f);
        
        bouquet.addFlower(shortFlower);
        bouquet.addFlower(longFlower);
        Storage.getInstance().addBouquet(bouquet);
        
        // Input: Select bouquet 1, min 5, max 20 (should find shortFlower)
        provideInput("1\n5\n20\n");
        
        FindFlowersByStemLengthCommand command = new FindFlowersByStemLengthCommand();
        
        // We can't easily verify console output without capturing it, 
        // but we can verify it runs without error.
        // To be more thorough, we could inspect internal state if the command exposed it,
        // but it prints directly. 
        // At least we exercise the code path.
        assertDoesNotThrow(() -> command.execute());
    }
    
    @Test
    void testSaveAndLoadToJsonCommand() {
        // 1. Setup data
        Flower flower = new Rose();
        flower.name = "Test Rose";
        Storage.getInstance().addFlower(flower);
        
        // 2. Save
        SaveToJsonCommand saveCmd = new SaveToJsonCommand();
        saveCmd.execute();
        
        // 3. Clear storage
        Storage.getInstance().getFlowersInStorage().clear();
        assertTrue(Storage.getInstance().getFlowersInStorage().isEmpty());
        
        // 4. Load
        LoadFromJsonCommand loadCmd = new LoadFromJsonCommand();
        loadCmd.execute();
        
        // 5. Verify
        assertFalse(Storage.getInstance().getFlowersInStorage().isEmpty());
        assertEquals("Test Rose", Storage.getInstance().getFlowersInStorage().get(0).getName());
    }
    
    // Edge case tests for better branch coverage
    
    @Test
    void testCreateFlowerCommandWithInvalidInput() {
        // Test with invalid choice (out of range)
        provideInput("-1\n");
        CreateFlowerCommand command = new CreateFlowerCommand();
        command.execute();
        
        // Should not create any flower
        assertTrue(Storage.getInstance().getFlowersInStorage().isEmpty());
    }
    
    @Test
    void testCreateBouquetCommandWithNoFlowers() {
        // Try to create bouquet with no flowers in storage
        provideInput("1\n");
        CreateBouquetCommand command = new CreateBouquetCommand();
        command.execute();
        
        // Should not create bouquet
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testCreateBouquetCommandCancelled() {
        Flower flower = new Rose();
        Storage.getInstance().addFlower(flower);
        
        // Input: Cancel (0)
        provideInput("0\n");
        CreateBouquetCommand command = new CreateBouquetCommand();
        command.execute();
        
        // Should not create bouquet
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testAddFlowerToBouquetCommandNoBouquets() {
        provideInput("1\n");
        AddFlowerToBouquetCommand command = new AddFlowerToBouquetCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testAddFlowerToBouquetCommandNoFlowers() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n");
        AddFlowerToBouquetCommand command = new AddFlowerToBouquetCommand();
        command.execute();
        
        // Bouquet should remain empty
        assertTrue(bouquet.getFlowers().isEmpty());
    }
    
    @Test
    void testRemoveFlowerCommandNoFlowers() {
        provideInput("1\n");
        RemoveFlowerCommand command = new RemoveFlowerCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(Storage.getInstance().getFlowersInStorage().isEmpty());
    }
    
    @Test
    void testRemoveFlowerFromBouquetCommandNoBouquets() {
        provideInput("1\n");
        RemoveFlowerFromBouquetCommand command = new RemoveFlowerFromBouquetCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testRemoveFlowerFromBouquetCommandEmptyBouquet() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n");
        RemoveFlowerFromBouquetCommand command = new RemoveFlowerFromBouquetCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(bouquet.getFlowers().isEmpty());
    }
    
    @Test
    void testSortBouquetByFreshnessCommandNoBouquets() {
        provideInput("1\n");
        SortBouquetByFreshnessCommand command = new SortBouquetByFreshnessCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testSortBouquetByFreshnessCommandEmptyBouquet() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n");
        SortBouquetByFreshnessCommand command = new SortBouquetByFreshnessCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(bouquet.getFlowers().isEmpty());
    }
    
    @Test
    void testUndoCommandWithNoHistory() {
        UndoCommand undoCmd = new UndoCommand();
        undoCmd.execute();
        
        // Should handle gracefully (no crash)
        assertEquals(0, CommandHistory.getInstance().size());
    }
    
    @Test
    void testFindFlowersByStemLengthCommandNoBouquets() {
        provideInput("1\n");
        FindFlowersByStemLengthCommand command = new FindFlowersByStemLengthCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testFindFlowersByStemLengthCommandEmptyBouquet() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        
        provideInput("1\n10\n20\n");
        FindFlowersByStemLengthCommand command = new FindFlowersByStemLengthCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(bouquet.getFlowers().isEmpty());
    }
    
    @Test
    void testAddAccessoryToBouquetCommandNoBouquets() {
        provideInput("1\n");
        AddAccessoryToBouquetCommand command = new AddAccessoryToBouquetCommand();
        command.execute();
        
        // Should handle gracefully
        assertTrue(Storage.getInstance().getBouquetsInStorage().isEmpty());
    }
    
    @Test
    void testAddAccessoryToBouquetCommandCustomAccessory() {
        Bouquet bouquet = new Bouquet();
        Storage.getInstance().addBouquet(bouquet);
        
        // Input: Select bouquet 1, select option 5 (custom), name "Custom", price 25
        provideInput("1\n5\nCustom\n25\n");
        AddAccessoryToBouquetCommand command = new AddAccessoryToBouquetCommand();
        command.execute();
        
        // Verify custom accessory added
        assertEquals(1, bouquet.getAccessories().size());
        assertEquals("Custom", bouquet.getAccessories().get(0));
        assertEquals(25.0f, bouquet.getAccessoryPrices().get(0));
    }
    
    @Test
    void testCommandUndo() {
        // Test undo for CreateFlowerCommand
        provideInput("1\n");
        CreateFlowerCommand createCmd = new CreateFlowerCommand();
        createCmd.execute();
        
        assertEquals(1, Storage.getInstance().getFlowersInStorage().size());
        
        // Undo
        createCmd.undo();
        assertTrue(Storage.getInstance().getFlowersInStorage().isEmpty());
    }
    
    @Test
    void testCommandCopy() {
        CreateFlowerCommand original = new CreateFlowerCommand();
        BaseCommand copy = original.copy();
        
        assertNotNull(copy);
        assertTrue(copy instanceof CreateFlowerCommand);
    }
}
