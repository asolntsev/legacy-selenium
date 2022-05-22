package pages.components.simpleteaser;

import elements.TextElement;
import org.openqa.selenium.By;

public class SimpleTeaserMainTextTabPage extends SimpleTeaserPage {
    private static final By MAIN_TEXT_TEXTAREA_LOCATOR = By.xpath("//div[./input[@name='./mainText']]/div[@data-editor-type]");
    
    public SimpleTeaserMainTextTabPage() {
        super("Simple teaser main text tab page", MAIN_TEXT_TEXTAREA_LOCATOR);
    }
    
    public void fillMainText(String mainText) {
        new TextElement("Main text field", MAIN_TEXT_TEXTAREA_LOCATOR).setValueByKeyPress(mainText);
    }
    
}
