package utils;

import helpers.Verifications;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Drivers factory. Default browser = CHROME
 */
public class WebDriverFactory {
    private static final String DRIVERS_PATH = "drivers" + File.separator;
    private static final String CHROME_DRIVER_PATH = "chromedriver.exe";
    private static final String FIREFOX_DRIVER_PATH = "geckodriver.exe";
    private static final String IE_DRIVER_PATH = "iedriver.exe";
    
    private static final String HUB_URL = System.getenv("HUB_URL");
    private static final String PROXY_URL = "http://pbpac.fspa.myntet.se/swppb.pac";
    
    private enum Browser {
        IE, CHROME, FIREFOX
    }
    
    private RemoteWebDriver activeDriver;
    private static WebDriverFactory _instance = null;
    private Browser defaultBrowser;
    
    private WebDriverFactory(Browser defaultBrowser) {
        this.defaultBrowser = defaultBrowser;
    }
    
    private static void init() {
        init(Browser.CHROME);
    }
    
    public static WebDriverFactory instance() {
        init();
        return _instance;
    }
    
    private static void init(Browser defaultBrowser) {
        _instance = (_instance == null) ? new WebDriverFactory(defaultBrowser) : _instance;
    }
    
    public RemoteWebDriver getInitializedDriver() {
        return getInitializedDriver(defaultBrowser);
    }
    
    private RemoteWebDriver get() {
        return activeDriver;
    }
    
    private RemoteWebDriver getInitializedDriver(Browser browser) {
        if (activeDriver == null) {
            switch (browser) {
                case IE:
                    activeDriver = createIeDriver();
                    break;
                case CHROME:
                    activeDriver = createChromeDriver();
                    break;
                case FIREFOX:
                    activeDriver = createFirefoxDriver();
                    break;
            }
            activeDriver.manage().deleteAllCookies();
            activeDriver.manage().window().maximize();
        }
        return get();
    }
    
    private static RemoteWebDriver createIeDriver() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability("ignoreZoomSetting", true);
        
        if (isRemote()) {
            return createRemoteDriver(options);
        }
        
        File ieDriver = new File(DRIVERS_PATH + IE_DRIVER_PATH);
        System.setProperty("webdriver.ie.driver", ieDriver.getAbsolutePath());
        
        return new InternetExplorerDriver(options);
    }
    
    private static RemoteWebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-ssl-errors");
        options.setCapability("proxy", new Proxy().setProxyAutoconfigUrl(PROXY_URL));
        
        if (isRemote()) {
            return createRemoteDriver(options);
        }
        
        File chromeDriver = new File(DRIVERS_PATH + CHROME_DRIVER_PATH);
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
        
        return new ChromeDriver(options);
    }
    
    private RemoteWebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (isRemote()) {
            return createRemoteDriver(options);
        }
        
        System.setProperty("webdriver.firefox.marionette", DRIVERS_PATH + FIREFOX_DRIVER_PATH);
        
        return new FirefoxDriver(options);
    }
    
    private static boolean isRemote() {
        return HUB_URL != null;
    }
    
    private static RemoteWebDriver createRemoteDriver(Capabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(HUB_URL), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void closeBrowser() {
        if (activeDriver != null) {
            activeDriver.quit();
            activeDriver = null;
        }
    }
    
    public void switchDriverToDefaultContent() {
        getInitializedDriver().switchTo().defaultContent();
        //iFrameActive = false;
    }
    
    public void checkAlertMessageAndCloseIt(String message) {
        Alert alert = instance().getInitializedDriver().switchTo().alert();
        String actual = alert.getText();
        
        Verifications.assertEquals(actual, message, "Alert message expected to be '" + message + "', but was '"
                + message + "'");
    
        closeAlertPopUp(alert);
    }
    
    public void closeAlertPopUp(Alert alert) {
        alert.accept();
        instance().switchDriverToDefaultContent();
    }
    
    public void refreshPage() {
        getInitializedDriver().navigate().refresh();
    }
    
    
}
