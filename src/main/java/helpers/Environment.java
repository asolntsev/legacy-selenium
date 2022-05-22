package helpers;

import base.BaseComponent;
import entities.Components;
import entities.Page;
import entities.enums.ContentItemsPages;
import logging.Log;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertiesManager;
import utils.WebDriverFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Contains all project constants
 */
public class Environment extends BaseComponent {
    private static final String PROP_FILE = "e2e.properties";
    private static final String ADMIN_USERNAME = PropertiesManager.get(PROP_FILE, "admin.username");
    private static final String ADMIN_PASSWORD = PropertiesManager.get(PROP_FILE, "admin.password");
    private static final String PUBLISH_URL = PropertiesManager.get(PROP_FILE, "url.publish");
    private static final String CREATE_PAGE_URL = PropertiesManager.get(PROP_FILE, "url.create-page");
    private static final String DELETE_PAGE_URL = PropertiesManager.get(PROP_FILE, "url.delete-page");
    private static final String PUBLISH_PAGE_URL = PropertiesManager.get(PROP_FILE, "url.publish-page");
    private static final String LOGIN_URL = PropertiesManager.get(PROP_FILE, "url.login");
    private static final String TOKEN_URL = PropertiesManager.get(PROP_FILE, "url.token");
    private static final String FILE_UPLOAD_URL = PropertiesManager.get(PROP_FILE, "url.upload-file");
    private static final String COMPONENT_UPDATE_IDENTIFIER = "/_vue__js_";
    
    public static final String URL = PropertiesManager.get(PROP_FILE, "url");
    public static final String API_URL = PropertiesManager.get(PROP_FILE, "api_url");

    private static RemoteWebDriver driver;

    public static void openNewBrowser() {
        driver = WebDriverFactory.instance().getInitializedDriver();
        driver.manage().window().maximize();
        Log.logInfo("New browser opened");
    }

    public static void closeCurrentBrowser() {
        WebDriverFactory.instance().closeBrowser();
        Log.logInfo("Current browser closed");
    }

    public static void navigateUrl(String url) {
        driver.get(url);
        Log.logInfo("URL: " + url + " opened");
    }

    public static void setTimeOutForElements() {
        setTimeOutForElements(Timeouts.VERY_FAST);
    }
    
    public static void setTimeOutForElements(Timeouts timeout) {
        WebDriverFactory.instance().getInitializedDriver().manage().timeouts().implicitlyWait(timeout.getValue(), TimeUnit.SECONDS);
    }
    
    public static void setTimeOutForPageLoad() {
        setTimeOutForPageLoad(Timeouts.SLOW);
    }
    
    public static void setTimeOutForPageLoad(Timeouts timeout) {
        WebDriverFactory.instance().getInitializedDriver().manage().timeouts().pageLoadTimeout(timeout.getValue(), TimeUnit.SECONDS);
    }
    
    public static String generateUniqueString() {
        return "" + new Date().getTime();
    }
    
    public static String getCreatePageUrl() {
        return API_URL + CREATE_PAGE_URL;
    }

    public static String getDeletePageUrl() {
        return API_URL + DELETE_PAGE_URL;
    }
    
    public static String getPublishPageUrl() {
        return API_URL + PUBLISH_PAGE_URL;
    }
    
    public static String getLoginUrl() {
        return API_URL + LOGIN_URL;
    }
    
    public static String getTokenUrl() {
        return API_URL + TOKEN_URL;
    }
    
    public static String getFileUploadUrl() {
        return API_URL + FILE_UPLOAD_URL;
    }
    
    public static String getComponentUpdateUrl(Components component, Page page) {
        return API_URL + NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS)
                + StringManager.URL_SEPARATOR + page.getName() + COMPONENT_UPDATE_IDENTIFIER + component.getDataPath();
    }
    
    public static String getContentItemPageUrl(ContentItemsPages contentItemsPage) {
        return API_URL + StringManager.URL_SEPARATOR + "sites.html" +
                NavigationHelper.getPathToContentItem(contentItemsPage);
    }
    
    public static String getAdminUsername() {
        return ADMIN_USERNAME;
    }
    
    public static String getAdminPassword() {
        return ADMIN_PASSWORD;
    }
    
    public static String getPublishUrl() {
        return PUBLISH_URL;
    }
    
    public static String getApiUrl() {
        return API_URL;
    }
    
    public static String getEditorsPageUrl(String environment, String pageName) {
        return environment + StringManager.URL_SEPARATOR + StringManager.EDITORS_POSTFIX
                + NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS)
                + StringManager.URL_SEPARATOR + pageName + StringManager.EXTENSION_HTML;
    }
    
    public static String getEditorsPageUrl(String pageName) {
        return getEditorsPageUrl(URL, pageName);
    }

    public static ArrayList<String> getCurrentTabs() {
        return new ArrayList<>(driver.getWindowHandles());
    }
    
    public static void switchToTab(String tab) {
        driver.switchTo().window(tab);
    }
    
    public static void switchToLastTab() {
        ArrayList<String> tabs = Environment.getCurrentTabs();
        Environment.switchToTab(tabs.get(tabs.size() - 1));
    }
    
    public static void navigateBack() {
        WebDriverFactory.instance().getInitializedDriver().navigate().back();
        waitForJs();
    }
    
    public static void closeCurrentTab() {
        driver.close();
    }
    
    public static void waitForJs() {
        new WebDriverWait(driver, Timeouts.SLOW.getValue()) {
        }.until((ExpectedCondition<Boolean>) driverObject -> {
            JavascriptExecutor executor = (JavascriptExecutor) driverObject;
            if ((boolean) executor.executeScript("return window.jQuery == undefined")) {
                return true;
            } else {
                return (Boolean) executor.executeScript("return jQuery.active == 0");
            }
        });
        
        new WebDriverWait(driver, Timeouts.SLOW.getValue()).until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }
    
    public static String getCurrentUrl() {
        return WebDriverFactory.instance().getInitializedDriver().getCurrentUrl();
    }
    
}
