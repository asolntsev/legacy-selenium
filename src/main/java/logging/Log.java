package logging;

import helpers.Environment;
import helpers.Verifications;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger for tests
 */
public class Log {
    private static final Logger LOG = Logger.getAnonymousLogger();
    private static final String CURRENT_WORKING_DIRECTORY = System.getProperty("user.dir");

    private static File logsDir;

    public static File getLogPath() {
        if (logsDir == null) {
            String timeStamp = Environment.generateUniqueString();
            logsDir = new File(new File(CURRENT_WORKING_DIRECTORY, "reports"), timeStamp);
            
            removeLogsDirectory(logsDir);
            
            if (!logsDir.exists()) {
                Verifications.assertTrue(logsDir.mkdirs(), "Failed to create reports directory");
            }
        }
        return logsDir;
    }
    
    private static void removeLogsDirectory(File logPath) {
        try {
            FileUtils.deleteDirectory(logPath);
        } catch (IOException ex) {
            Log.logFailWithoutScreenshot("Failed to clean up the directory");
        }
    }
    
    private static void logMessage(String message, File screenShot, String logStatus) {
        File screenshotPath = getLogPath();
        
        try {
            FileUtils.moveFileToDirectory(screenShot, screenshotPath, true);
        } catch (IOException e) {
            logFailWithoutScreenshot(e.getMessage());
        }
        logMessage(message, logStatus);
    }

    private static void logMessage(String message, String logStatus) {
        LOG.info("[" + logStatus + "] " + message);
    }

    public static void logInfo(String message) {
        logMessage(message, "INFO");
    }
    
    public static void logInfo(String message, File screenShot) {
        logMessage(message, screenShot, "INFO");
    }
    
    public static void logFail(String message) {
        logFail(message, Screenshot.fromDriver());
    }
    
    public static void logFailWithoutScreenshot(String message) {
        LOG.log(Level.SEVERE, message);
        throw new RuntimeException(message);
    }
    
    public static void logFail(String message, File screenShot) {
        logMessage(message, screenShot, "FAIL");
        throw new RuntimeException(message);
    }
    
    public static void logPass(String message) {
        logMessage(message, "PASS");
    }
    
    public static void logPass(String message, File screenShot) {
        logMessage(message, screenShot, "PASS");
    }
    
    public static void logDebug(String message) {
        logMessage(message, "DEBUG");
    }
    
}
