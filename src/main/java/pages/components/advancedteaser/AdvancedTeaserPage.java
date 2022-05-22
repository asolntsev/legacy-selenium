package pages.components.advancedteaser;

import elements.ButtonElement;
import entities.Components;
import org.openqa.selenium.By;
import pages.components.ComponentPage;

public class AdvancedTeaserPage extends ComponentPage {
    private static final By ADVANCED_TEASER_PAGE_LOCATOR
            = By.xpath(String.format("//form[contains(@action,'%1$s')]", Components.ADVANCED_TEASER.getDataPath()));
    
    private static final By TAB_GENERAL_LOCATOR = By.xpath("//a[@aria-controls='advanced-teaser-dialog-general-tab']");
    private static final By TAB_BACKGROUND_LOCATOR = By.xpath("//a[@aria-controls='advanced-teaser-dialog-background-tab']");
    private static final By TAB_CTA_LINKS_LOCATOR = By.xpath("//a[@aria-controls='advanced-teaser-dialog-cta-links-tab']");
    private static final By TAB_VIDEO_TEASER_LOCATOR = By.xpath("//a[@aria-controls='advanced-teaser-dialog-video-teaser-tab']");
    
    public AdvancedTeaserPage() {
        super("Advanced teaser", ADVANCED_TEASER_PAGE_LOCATOR);
    }
    
    AdvancedTeaserPage(String name, By locator) {
        super(name, locator);
    }
    
    public AdvancedTeaserGeneralTabPage navigateGeneralTab() {
        new ButtonElement("General tab", TAB_GENERAL_LOCATOR).click();
        return new AdvancedTeaserGeneralTabPage();
    }
    
    public AdvancedTeaserBackgroundTabPage navigateBackgroundTab() {
        new ButtonElement("Background tab", TAB_BACKGROUND_LOCATOR).click();
        return new AdvancedTeaserBackgroundTabPage();
    }
    
    public AdvancedTeaserCtaButtonLinkTabPage navigateCtaLinksTab() {
        new ButtonElement("CTA links tab", TAB_CTA_LINKS_LOCATOR).click();
        return new AdvancedTeaserCtaButtonLinkTabPage();
    }
    
    public AdvancedTeaserVideoTeaserTabPage navigateVideoTeaserTab() {
        new ButtonElement("Video teaser tab", TAB_VIDEO_TEASER_LOCATOR).click();
        return new AdvancedTeaserVideoTeaserTabPage();
    }
    
}
