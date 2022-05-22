package pages.page.editor;

import base.BasePage;
import elements.ButtonElement;
import helpers.Environment;
import org.openqa.selenium.By;
import utils.WebDriverFactory;

public abstract class AuthorViewNavigationPage extends BasePage {
    private static final By SELECT_ANOTHER_MODE_LOCATOR = By.xpath("//a[contains(@href,'selectlayer-popover')]");
    private static final By EDIT_MODE_LOCATOR = By.xpath("//huge-popover[@id='selectlayer-popover']//button[@value='Edit']");
    private static final By PREVIEW_MODE_LOCATOR = By.xpath("//button[@data-layer='Preview']");
    
    protected AuthorViewNavigationPage(String name, By locator) {
        super(name, locator);
    }
    
    protected abstract EditorsViewPage switchToEditMode(String pageName);
    
    protected void selectEditMode() {
        switchToDefaultMode();
        Environment.waitForJs();
        getSelectAnotherModeButton().clickViaJS();
        getEditModeButton().clickViaJS();
        Environment.waitForJs();
    }
    
    protected ButtonElement getEditModeButton() {
        return new ButtonElement("Edit mode", EDIT_MODE_LOCATOR);
    }
    
    public void switchToDefaultMode() {
        WebDriverFactory.instance().switchDriverToDefaultContent();
    }
    
    public PreviewModeIFramePage switchToPreviewMode() {
        new ButtonElement("Edit mode", PREVIEW_MODE_LOCATOR).clickViaJS();
        Environment.waitForJs();
        return new PreviewModePage().switchToPreviewFrameIfNeeded();
    }
    
    protected ButtonElement getSelectAnotherModeButton() {
        return new ButtonElement("Select another mode", SELECT_ANOTHER_MODE_LOCATOR);
    }
    
}
