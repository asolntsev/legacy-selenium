package base;

import entities.enums.ContentItemsPages;
import helpers.ApiHelper;
import helpers.Environment;
import logging.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TestResultListener;

import java.util.ArrayList;

/**
 * Superclass for all tests
 */
@ExtendWith(TestResultListener.class)
public abstract class AbstractTest extends BaseComponent {
    
    @BeforeAll
    public static void beforeSuite() {
        Environment.setTimeOutForPageLoad();
        Environment.setTimeOutForElements();
        
        Log.logInfo("Cleanup before tests");
        deleteAllPagesFromTestAutomationFolder();
    }
    
    @BeforeEach
    public void beforeMethod(TestInfo testInfo) {
        Log.logInfo("Starting test " + testInfo.getTestClass().get().getName() + "." + testInfo.getDisplayName());
        Environment.openNewBrowser();
    }
    
    @AfterEach
    public void afterMethod() {
        Environment.closeCurrentBrowser();
    }
    
    @AfterAll
    public static void afterSuite() {
        Log.logInfo("Finished test suite");
    }
    
    private static void deleteAllPagesFromTestAutomationFolder() {
        ArrayList<String> listOfPages = ApiHelper.getListOfChildPages(ContentItemsPages.AUTOMATION_TESTS);
        if (!listOfPages.isEmpty()) {
            listOfPages.forEach(page -> ApiHelper.deletePage(page.toLowerCase()));
        }
    }
}
