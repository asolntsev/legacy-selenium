package pages.dialogs;

import base.BasePage;
import elements.ButtonElement;
import org.openqa.selenium.By;

public class SuccessDialogPage extends BasePage {
    private static final By SUCCESS_PAGE_TITLE_LOCATOR = By.xpath("//huge-dialog-header[contains(text(),'Success')]");
    private static final By DONE_BUTTON_LOCATOR = By.xpath("//button[./huge-button-label[contains(text(),'Done')]]");
    private static final By OPEN_BUTTON_LOCATOR = By.xpath("//button[./huge-button-label[contains(text(),'Open')]]");
    
    public SuccessDialogPage() {
        super("Success dialog", SUCCESS_PAGE_TITLE_LOCATOR);
    }
    
    public void clickDoneButton() {
        ButtonElement doneButton = new ButtonElement("Done", this.getPageWebElement(), DONE_BUTTON_LOCATOR);
        doneButton.click();
    }
    
    public void clickOpenButton() {
        ButtonElement openButton = new ButtonElement("Open", this.getPageWebElement(), OPEN_BUTTON_LOCATOR);
        openButton.click();
    }
    
}
