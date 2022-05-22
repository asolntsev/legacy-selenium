package logging;

import base.BaseComponent;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;

import java.io.File;

public class Screenshot extends BaseComponent {
    public static File fromDriver() {
        return fromDriver(WebDriverFactory.instance().getInitializedDriver());
    }
    
    private static File fromDriver(WebDriver driver) {
        File scrFile;
        try {
            scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            e.printStackTrace();
            scrFile = null;
        }
        return scrFile;
    }
}
