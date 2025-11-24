package ui;

import org.junit.jupiter.api.Test;
import utils.InputUtil;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleMenuTest {

    @Test
    void testConsoleMenuCreation() {
        ConsoleMenu menu = new ConsoleMenu();
        assertNotNull(menu);
    }

    @Test
    void testShowMenu() {
        ConsoleMenu menu = new ConsoleMenu();
        // Just verify it doesn't throw an exception
        assertDoesNotThrow(() -> menu.showMenu());
    }

    @Test
    void testHandleInputWithExit() {
        ConsoleMenu menu = new ConsoleMenu();
        
        // Simulate user entering 0 to exit
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        
        // Should exit gracefully
        assertDoesNotThrow(() -> menu.handleInput());
        
        // Reset
        InputUtil.setInputStream(System.in);
    }

    @Test
    void testHandleInputWithInvalidCommand() {
        ConsoleMenu menu = new ConsoleMenu();
        
        // Simulate user entering invalid command then exit
        InputUtil.setInputStream(new ByteArrayInputStream("999\n0\n".getBytes()));
        
        // Should handle invalid input gracefully
        assertDoesNotThrow(() -> menu.handleInput());
        
        // Reset
        InputUtil.setInputStream(System.in);
    }
}
