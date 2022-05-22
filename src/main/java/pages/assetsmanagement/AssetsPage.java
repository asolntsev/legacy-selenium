package pages.assetsmanagement;

import elements.ButtonElement;
import elements.LabelElement;
import elements.LinkElement;
import elements.TextElement;
import entities.Assets;
import helpers.Environment;
import org.openqa.selenium.By;
import pages.MainPage;

public class AssetsPage extends MainPage {
    private static final By UPLOAD_FILE_INPUT_LOCATOR = By.xpath("//input[contains(@class,'FileUpload-input')]");
    private static final By TOP_MENU_MORE_BUTTON_LOCATOR = By.xpath("//huge-actionbar-primary//button[./huge-icon[@aria-label='more']]");
    private static final By TOP_MENU_DELETE_BUTTON_LOCATOR = By.xpath("//button[./huge-icon[@icon='delete']]");
    
    private static final String SELECT_FILE_LOCATOR_TEMPLATE = "//huge-item[.//huge-card-title[@value='%s']]//button[@aria-label='Select']";
    private static final String ASSETS_PAGE_TITLE_LOCATOR_TEMPLATE = "//huge-titlebar[@id='shell-actionbar' and contains(.,'%1$s')]";
    private static final String FOLDER_LOCATOR_TEMPLATE = "//huge-card-title[@value='%1$s']";
    private static final String FILE_LOCATOR_TEMPLATE = "//huge-card-title[@value='%s']";
    
    public AssetsPage(String folder) {
        super("Assets page", By.xpath(String.format(ASSETS_PAGE_TITLE_LOCATOR_TEMPLATE, folder)));
    }
    
    public void selectFolderByTitle(String title) {
        new LinkElement("Folder '" + title + "'", By.xpath(String.format(FOLDER_LOCATOR_TEMPLATE, title))).clickViaJS();
    }
    
    public void uploadFile(Assets file) {
        new TextElement("File upload field", UPLOAD_FILE_INPUT_LOCATOR).element().sendKeys(file.getPath());
    }
    
    public LabelElement getFileTitleLabel(String fullFileName) {
        return new LabelElement("File title", By.xpath(String.format(FILE_LOCATOR_TEMPLATE, fullFileName)));
    }
    
    public void selectFile(String fullFileName) {
        getFileTitleLabel(fullFileName).moveMouseToElement();
        new ButtonElement("Select file button", By.xpath(String.format(SELECT_FILE_LOCATOR_TEMPLATE, fullFileName))).click();
    }
    
    public void selectFile(Assets file) {
        selectFile(file.getFullName());
    }
    
    private void clickTopMenuMoreButtonIfNeeded() {
        Environment.waitForJs();
        ButtonElement buttonMore = new ButtonElement("Top menu more button", TOP_MENU_MORE_BUTTON_LOCATOR);
        if (buttonMore.element().isDisplayed()) {
            buttonMore.click();
        }
    }
    
    public void clickTopMenuDeleteButton() {
        clickTopMenuMoreButtonIfNeeded();
        new ButtonElement("Top menu delete button", TOP_MENU_DELETE_BUTTON_LOCATOR).click();
    }
    
}
