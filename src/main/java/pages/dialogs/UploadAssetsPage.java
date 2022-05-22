package pages.dialogs;

import base.BasePage;
import elements.ButtonElement;
import elements.TextElement;
import org.openqa.selenium.By;

public class UploadAssetsPage extends BasePage {
    private static final By UPLOAD_ASSETS_PAGE_TITLE_LOCATOR = By.xpath("//huge-dialog[@class='huge-Dialog is-open' and @id='uploadListDialog']");
    
    private static final By RENAME_FILE_FIELD_LOCATOR = By.id("user-profile-asset-upload-rename-input");
    private static final By UPLOAD_BUTTON_LOCATOR = By.xpath("//button[contains(@class,'user-profile-asset-upload-button')]");
    
    public UploadAssetsPage() {
        super("Upload assets page", UPLOAD_ASSETS_PAGE_TITLE_LOCATOR);
    }
    
    public void renameFile(String newFileName) {
        new TextElement("Rename file", RENAME_FILE_FIELD_LOCATOR).setValueByKeyPress(newFileName);
    }
    
    public void clickUploadButton() {
        new ButtonElement("Upload button", UPLOAD_BUTTON_LOCATOR).click();
    }
}
