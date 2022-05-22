package pages.page.editor;

import base.BaseElement;
import org.openqa.selenium.By;

public class PreviewModePage extends AuthorViewNavigationPage {
    private static final By PAGE_LOCATOR = By.id("Content");
    private static final By IFRAME_LOCATOR = By.id("ContentFrame");
    protected static boolean iFrameActive = false;
    
    protected PreviewModePage(String name, By locator) {
        super(name, locator);
    }
    
    @Override
    public EditorsViewPage switchToEditMode(String pageName) {
        selectEditMode();
        iFrameActive = false;
        return new EditorsViewPage(pageName);
    }
    
    public PreviewModePage() {
        super("Preview mode page", PAGE_LOCATOR);
    }
    
    public PreviewModeIFramePage switchToPreviewFrameIfNeeded() {
        if (!iFrameActive) {
            BaseElement iFrameElement = new BaseElement("Editors view frame element", IFRAME_LOCATOR);
            iFrameElement.switchDriverToFrameElement();
            iFrameActive = true;
        }
        return new PreviewModeIFramePage();
    }
    
}
