package pages.components.simpleteaser;

import elements.ButtonElement;
import entities.Components;
import org.openqa.selenium.By;
import pages.components.ComponentPage;

public class SimpleTeaserPage extends ComponentPage {
    private static final By SIMPLE_TEASER_PAGE_LOCATOR
            = By.xpath(String.format("//form[contains(@action,'%1$s')]", Components.SIMPLE_TEASER.getDataPath()));
    
    private static final By TAB_HEADING_LOCATOR = By.xpath("//a[@aria-controls='simple-teaser-dialog-heading-tab']");
    private static final By TAB_BACKGROUND_LOCATOR = By.xpath("//a[@aria-controls='simple-teaser-dialog-background-tab']");
    private static final By TAB_MAIN_TEXT_LOCATOR = By.xpath("//a[@aria-controls='simple-teaser-dialog-main-text-tab']");
    private static final By TAB_CTA_BUTTON_LINK__LOCATOR = By.xpath("//a[@aria-controls='component-dialog-link-list-tab']");
    
    public SimpleTeaserPage() {
        super("Simple Teaser page", SIMPLE_TEASER_PAGE_LOCATOR);
    }
    
    SimpleTeaserPage(String name, By locator) {
        super(name, locator);
    }
    
    public SimpleTeaserHeadingTabPage navigateHeadingTab() {
        new ButtonElement("Heading tab", TAB_HEADING_LOCATOR).click();
        return new SimpleTeaserHeadingTabPage();
    }
    
    public SimpleTeaserBackgroundTabPage navigateBackgroundTab() {
        new ButtonElement("Background tab", TAB_BACKGROUND_LOCATOR).click();
        return new SimpleTeaserBackgroundTabPage();
    }
    
    public SimpleTeaserMainTextTabPage navigateMainTextTab() {
        new ButtonElement("Main text tab", TAB_MAIN_TEXT_LOCATOR).click();
        return new SimpleTeaserMainTextTabPage();
    }
    
    public SimpleTeaserCtaButtonLinkTabPage navigateCtaButtonLinkTab() {
        new ButtonElement("Cta button link tab", TAB_CTA_BUTTON_LINK__LOCATOR).click();
        return new SimpleTeaserCtaButtonLinkTabPage();
    }
}
