package pages.components.advancedteaser;

import org.openqa.selenium.By;

public class AdvancedTeaserCtaButtonLinkTabPage extends AdvancedTeaserPage {
    private static final By FIRST_CTA_LINK_TYPE_LOCATOR = By.name("./links/item0/linkAction");
    
    public AdvancedTeaserCtaButtonLinkTabPage() {
        super("Advanced teaser CTA button link tab", FIRST_CTA_LINK_TYPE_LOCATOR);
    }
    
    
    
}
