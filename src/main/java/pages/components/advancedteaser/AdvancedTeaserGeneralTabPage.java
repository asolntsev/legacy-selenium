package pages.components.advancedteaser;

import elements.DropdownElement;
import elements.LabelElement;
import elements.TextElement;
import org.openqa.selenium.By;

public class AdvancedTeaserGeneralTabPage extends AdvancedTeaserPage {
    private static final By HEADING_TEXTAREA_LOCATOR = By.name("./heading");
    private static final By MAIN_TEXT_TEXTAREA_LOCATOR = By.xpath("//div[./input[@name='./mainText']]/div[@data-editor-type]");
    private static final By DISCLAIMER_TEXT_TEXTAREA_LOCATOR = By.xpath("//div[./input[@name='./disclaimerText']]/div[@data-editor-type]");
    private static final By MAIN_IMAGE_DRAG_AND_DROP_LOCATOR = By.name("./main-image/image");
    private static final By LAYOUT_DROPDOWN_LOCATOR = By.name("./layout");
    
    public AdvancedTeaserGeneralTabPage() {
        super("Advanced teaser General tab", HEADING_TEXTAREA_LOCATOR);
    }
    
    public void setHeading(String heading) {
        new TextElement("Heading", HEADING_TEXTAREA_LOCATOR).setValueByKeyPress(heading);
    }
    
    public void setMainText(String mainText) {
        TextElement element = new TextElement("Main text", MAIN_TEXT_TEXTAREA_LOCATOR);
        element.setValueByKeyPress(mainText);
    }
    
    public void setDisclaimerText(String disclaimerText) {
        TextElement element = new TextElement("Disclaimer text", DISCLAIMER_TEXT_TEXTAREA_LOCATOR);
        element.setValueByKeyPress(disclaimerText);
    }
    
    public void selectLayout(String layout) {
        new DropdownElement("Layout dropdown", LAYOUT_DROPDOWN_LOCATOR).selectOptionFromDropdownByVisibleText(layout);
    }
    
    public LabelElement getMainImagePlaceholder() {
        return new LabelElement("Image placeholder", MAIN_IMAGE_DRAG_AND_DROP_LOCATOR);
    }
    
}
