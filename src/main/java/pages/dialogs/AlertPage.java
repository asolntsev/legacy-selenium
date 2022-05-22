package pages.dialogs;

import base.BasePage;
import elements.LabelElement;
import org.openqa.selenium.By;

public class AlertPage extends BasePage {
    
    private static final By ALERT_PAGE_LOCATOR = By.xpath("//huge-alert");
    private static final String ALERT_MESSAGE_LOCATOR_TEMPLATE = "//huge-alert-content[contains(text(),'%1$s')]";
    
    public AlertPage() {
        super("Alert popup", ALERT_PAGE_LOCATOR);
    }
    
    public void verifyAlertInfo(String value) {
        new LabelElement("Alert message", By.xpath(String.format(ALERT_MESSAGE_LOCATOR_TEMPLATE, value))).assertExists();
    }
}
