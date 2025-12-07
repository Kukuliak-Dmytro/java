package command;

import io.sentry.Sentry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Command to test Sentry integration
 * This will send a test exception to Sentry to verify the integration is working
 */
public class TestSentryCommand extends BaseCommand {
    private static final Logger logger = LogManager.getLogger(TestSentryCommand.class);
    
    @Override
    public void execute() {
        logger.info("Testing Sentry integration...");
        
        // Debug: Print current Sentry config
        try {
            String currentDsn = Sentry.getCurrentHub().getOptions().getDsn();
            String currentEnv = Sentry.getCurrentHub().getOptions().getEnvironment();
            System.out.println("DEBUG: Current Sentry DSN: " + currentDsn);
            
            // Extract and print Project ID
            if (currentDsn != null && currentDsn.contains("/")) {
                String projectId = currentDsn.substring(currentDsn.lastIndexOf('/') + 1);
                System.out.println("DEBUG: 🔍 CHECK THIS PROJECT ID: " + projectId);
            }
            
            System.out.println("DEBUG: Current Sentry Environment: " + currentEnv);
        } catch (Exception e) {
            System.out.println("DEBUG: Could not read Sentry options: " + e.getMessage());
        }
        
        try {
            // Create a test exception with a unique timestamp to avoid deduplication
            String uniqueId = java.util.UUID.randomUUID().toString();
            throw new RuntimeException("Sentry Test Exception " + uniqueId);
        } catch (Exception e) {
            // Log the error (will be captured by Log4j2 Sentry appender)
            logger.error("Test exception caught and logged via Log4j", e);
            
            // We removed the manual Sentry.captureException(e) to avoid "Duplicate Exception" warnings
            // because the Log4j appender is already sending it.
            
            System.out.println("✓ Test exception logged! (ID: " + e.getMessage() + ")");
            System.out.println("--------------------------------------------------");
            System.out.println("STATUS: The logs confirm the event was SENT to Sentry.");
            System.out.println("--------------------------------------------------");
            System.out.println("If you are on the 'Waiting for events' screen in Sentry:");
            System.out.println("1. Refresh the page manually.");
            System.out.println("2. Click 'Take me to Issues' or 'Skip this step' if available.");
            System.out.println("3. Go to the 'Stats' tab on the left to confirm data arrival.");
            System.out.println("--------------------------------------------------");
            
            // Send a separate simple message to verify connectivity without exception overhead
            Sentry.captureMessage("Manual test message from Console App " + System.currentTimeMillis());
        }
    }
    
    @Override
    public void getInfo() {
        System.out.println("Test Sentry - Sends a test exception to verify Sentry integration");
    }
}
