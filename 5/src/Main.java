//with the new update, Java allows to NOT write static public void main
import commands.CommandHistory;
import storage.Storage;
import ui.ConsoleMenu;
import utils.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        // Test logging first
        logger.info("=== Application started ===");
        
        // Load config on startup
        ConfigLoader.loadConfig();
   
//    do the rest of the stuff here
        System.out.println("Hello and welcome!");
        ConsoleMenu menu = new ConsoleMenu();
        menu.handleInput();
        
        logger.info("=== Application shutting down ===");
    }
}
