package pages.components.simpleteaser;

import org.openqa.selenium.By;

public class SimpleTeaserBackgroundTabPage extends SimpleTeaserPage {
    
    private static final By BACKGROUND_COLOR_DROPDOWN_LOCATOR = By.name("./backgroundColor");
    
    public SimpleTeaserBackgroundTabPage() {
        super("Simple teaser background tab page", BACKGROUND_COLOR_DROPDOWN_LOCATOR);
    }
    
}
