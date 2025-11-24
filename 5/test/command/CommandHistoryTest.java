package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    void setUp() {
        history = CommandHistory.getInstance();
        history.clear();
    }

    @Test
    void testPushAndPop() {
        BaseCommand cmd1 = new CreateFlowerCommand();
        BaseCommand cmd2 = new CreateBouquetCommand();
        
        history.push(cmd1);
        assertEquals(1, history.size());
        
        history.push(cmd2);
        assertEquals(2, history.size());
        
        BaseCommand popped2 = history.pop();
        // Note: CommandHistory.push() stores a copy, so it won't be the exact same instance
        // But it should be the same class
        assertNotNull(popped2);
        assertEquals(cmd2.getClass(), popped2.getClass());
        assertEquals(1, history.size());
        
        BaseCommand popped1 = history.pop();
        assertNotNull(popped1);
        assertEquals(cmd1.getClass(), popped1.getClass());
        assertEquals(0, history.size());
        
        assertNull(history.pop());
    }

    @Test
    void testGetAllCommands() {
        BaseCommand cmd1 = new CreateFlowerCommand();
        BaseCommand cmd2 = new CreateBouquetCommand();
        
        history.push(cmd1);
        history.push(cmd2);
        
        List<BaseCommand> allCommands = history.getAllCommands();
        assertEquals(2, allCommands.size());
        assertEquals(cmd1.getClass(), allCommands.get(0).getClass());
        assertEquals(cmd2.getClass(), allCommands.get(1).getClass());
    }
    
    @Test
    void testClear() {
        history.push(new CreateFlowerCommand());
        assertFalse(history.getAllCommands().isEmpty());
        
        history.clear();
        assertTrue(history.getAllCommands().isEmpty());
        assertEquals(0, history.size());
    }
}
