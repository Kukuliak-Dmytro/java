//with the new update, Java allows to NOT write static public void main
import ui.ConsoleMenu;
import utils.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.sentry.Sentry;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        // Initialize Sentry first (before any logging)
        initializeSentry();
        
        // Test logging first
        logger.info("=== Application started ===");
        
        // Load config on startup
        ConfigLoader.loadConfig();
   
//    do the rest of the stuff here
        System.out.println("Hello and welcome!");
        ConsoleMenu menu = new ConsoleMenu();
        menu.handleInput();
        
        logger.info("=== Application shutting down ===");
        
        // Close Sentry on shutdown
        Sentry.close();
    }
    
    /**
     * Initialize Sentry for error tracking
     * Reads SENTRY_DSN from environment variables
     */
    private static void initializeSentry() {
        String sentryDsn = System.getenv("SENTRY_DSN");
        
        if (sentryDsn == null || sentryDsn.isEmpty()) {
            logger.warn("SENTRY_DSN not found in environment variables. Sentry will not be initialized.");
            return;
        }
        
        Sentry.init(options -> {
            options.setDsn(sentryDsn);
            // Set environment (e.g., "development", "production")
            options.setEnvironment(System.getenv("SENTRY_ENVIRONMENT") != null ? 
                System.getenv("SENTRY_ENVIRONMENT") : "development");
            // Set release version if available
            String release = System.getenv("SENTRY_RELEASE");
            if (release != null && !release.isEmpty()) {
                options.setRelease(release);
            }
            // Enable debug mode for Sentry itself (optional, for troubleshooting)
            options.setDebug(true);
            // Set traces sample rate (1.0 = 100% of transactions)
            options.setTracesSampleRate(1.0);
        });
        
        logger.info("Sentry initialized successfully with DSN: " + (sentryDsn.length() > 10 ? sentryDsn.substring(0, 10) + "..." : "SHORT_DSN"));
        
        logger.info("Sentry initialized successfully");
    }
}

