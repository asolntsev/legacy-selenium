package pages.components;

import base.BasePage;
import elements.ButtonElement;
import elements.LabelElement;
import org.openqa.selenium.By;

public class ComponentPage extends BasePage {
    private static final By DONE_BUTTON_LOCATOR = By.xpath("//button[./huge-icon[@aria-label='check']]");
    private static final By CLOSE_BUTTON_LOCATOR = By.xpath("//button[./huge-icon[@aria-label='close']]");
    private static final By FIELD_ERROR_LOCATOR = By.xpath("//huge-icon[@aria-label='alert' and contains(@class,'fielderror')]");
    
    protected ComponentPage(String name, By locator) {
        super(name, locator);
    }
    
    public void clickDone() {
        new ButtonElement("Done button", DONE_BUTTON_LOCATOR).click();
    }
    
    public void clickClose() {
        new ButtonElement("Close button", CLOSE_BUTTON_LOCATOR).clickViaJS();
    }
    
    public void verifyFieldErrorExists() {
        getErrorIcon().assertExists();
    }
    
    public void verifyFieldErrorDoesNotExist() {
        getErrorIcon().assertDoesNotExist();
    }
    
    private LabelElement getErrorIcon() {
        return new LabelElement("Field error", FIELD_ERROR_LOCATOR);
    }
}
