package pages.dialogs;

import base.BasePage;
import elements.ButtonElement;
import org.openqa.selenium.By;

public class DeleteDialogPage extends BasePage {
    private static final By DELETE_PAGE_TITLE_LOCATOR = By.xpath("//huge-dialog-header[contains(text(),'Delete')]");
    private static final By DELETE_BUTTON_LOCATOR = By.xpath("//huge-dialog//button[./huge-button-label[contains(text(),'Delete')]]");
    
    public DeleteDialogPage() {
        super("Delete dialog", DELETE_PAGE_TITLE_LOCATOR);
    }
    
    public void clickDeleteButton() {
        ButtonElement deleteButton = new ButtonElement("Delete button", DELETE_BUTTON_LOCATOR);
        deleteButton.click();
    }
}
