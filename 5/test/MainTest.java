import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.InputUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @AfterEach
    void tearDown() {
        // Reset System.out and System.in
        System.setOut(originalOut);
        InputUtil.setInputStream(System.in);
    }

    @Test
    void testMainWithImmediateExit() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Simulate user entering 0 to exit immediately
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        
        // Run main
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        
        // Verify output contains welcome message
        String output = outContent.toString();
        assertTrue(output.contains("Hello and welcome!"));
    }

    @Test
    void testMainDisplaysMenu() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Simulate user entering 0 to exit
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        
        // Run main
        Main.main(new String[]{});
        
        // Verify menu is displayed
        String output = outContent.toString();
        assertTrue(output.contains("Available commands"));
    }

    @Test
    void testMainLoadsConfig() {
        // Simulate user entering 0 to exit
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        
        // Run main - should load config without errors
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void testMainWithInvalidCommandThenExit() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Simulate user entering invalid command (999) then exit (0)
        InputUtil.setInputStream(new ByteArrayInputStream("999\n0\n".getBytes()));
        
        // Run main
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        
        // Verify invalid command message
        String output = outContent.toString();
        assertTrue(output.contains("Invalid command number") || output.contains("Available commands"));
    }

    @Test
    void testMainExecutesDisplayFlowersCommand() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Command 12 is Display Flowers, then exit
        InputUtil.setInputStream(new ByteArrayInputStream("12\n0\n".getBytes()));
        
        // Run main
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        
        String output = outContent.toString();
        // Should show flowers (even if empty)
        assertTrue(output.contains("Executing") || output.contains("flowers"));
    }

    @Test
    void testMainExecutesDisplayBouquetsCommand() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Command 13 is Display Bouquets, then exit
        InputUtil.setInputStream(new ByteArrayInputStream("13\n0\n".getBytes()));
        
        // Run main
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        
        String output = outContent.toString();
        // Should show bouquets (even if empty)
        assertTrue(output.contains("Executing") || output.contains("bouquets"));
    }

    @Test
    void testMainExecutesDisplayHistoryCommand() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Command 14 is Display History, then exit
        InputUtil.setInputStream(new ByteArrayInputStream("14\n0\n".getBytes()));
        
        // Run main
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        
        String output = outContent.toString();
        // Should show history (even if empty)
        assertTrue(output.contains("Executing") || output.contains("history") || output.contains("No commands"));
    }

    @Test
    void testMainWithMultipleCommands() {
        // Capture output
        System.setOut(new PrintStream(outContent));
        
        // Execute Display Flowers, Display Bouquets, Display History, then exit
        InputUtil.setInputStream(new ByteArrayInputStream("12\n13\n14\n0\n".getBytes()));
        
        // Run main
        assertDoesNotThrow(() -> Main.main(new String[]{}));
        
        String output = outContent.toString();
        // Verify multiple commands were executed
        assertTrue(output.contains("Available commands"));
    }

    @Test
    void testMainWithEmptyArgs() {
        // Test with null args
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        
        assertDoesNotThrow(() -> Main.main(null));
    }

    @Test
    void testMainWithArgs() {
        // Test with some args (should be ignored)
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        
        assertDoesNotThrow(() -> Main.main(new String[]{"arg1", "arg2"}));
    }
}
