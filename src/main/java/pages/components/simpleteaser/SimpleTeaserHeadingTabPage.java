package pages.components.simpleteaser;

import elements.TextElement;
import org.openqa.selenium.By;

public class SimpleTeaserHeadingTabPage extends SimpleTeaserPage {
    
    private static final By HEADING_TEXTAREA_LOCATOR = By.name("./heading");
    
    public SimpleTeaserHeadingTabPage() {
        super("Simple teaser heading tab page", HEADING_TEXTAREA_LOCATOR);
    }
    
    public void fillHeading(String heading) {
        new TextElement("Heading", HEADING_TEXTAREA_LOCATOR).setValueByKeyPress(heading);
    }
    
}
