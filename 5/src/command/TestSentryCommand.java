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
        
        try {
            // Create a test exception
            throw new RuntimeException("This is a test exception for Sentry verification");
        } catch (Exception e) {
            // Log the error (will be captured by Log4j2 Sentry appender)
            logger.error("Test exception caught and logged", e);
            
            // Also send directly to Sentry with additional context
            Sentry.captureException(e);
            
            System.out.println("✓ Test exception sent to Sentry!");
            System.out.println("Check your Sentry dashboard to verify the error was captured.");
        }
    }
    
    @Override
    public void getInfo() {
        System.out.println("Test Sentry - Sends a test exception to verify Sentry integration");
    }
}
