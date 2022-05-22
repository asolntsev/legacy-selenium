package pages.page.editor;

import elements.ButtonElement;
import elements.LabelElement;
import entities.Components;
import helpers.Environment;
import org.openqa.selenium.By;

public class PublishedViewPage extends ContentAbstractPage {
    private static final String PUBLISHED_VIEW_PAGE_LOCATOR_TEMPLATE = "//head[..//div[not(contains(@class,'editor-panel'))]]/title[contains(text(),'%1$s')]";
    private static final String CONTENT_ITEM_LOCATOR_TEMPLATE = "//div[contains(@class,'%1$s aem')]";
    private static final By ACCEPT_COOKIES_BUTTON_LOCATOR = By.xpath("//div[@id='cookie-notification']//button");
    
    public PublishedViewPage() {
        super("Published page");
    }
    
    public LabelElement getContentContainer(Components component) {
        return new LabelElement("Content container: " + component.getName(),
                By.xpath(String.format(CONTENT_ITEM_LOCATOR_TEMPLATE, component.getValue())));
    }
    
    public void verifyCookieBannerDoesNotExist() {
        Environment.waitForJs();
        getAcceptCookiesButton().assertIsNotVisible();
    }
    
    public void acceptCookiePolicy() {
        getAcceptCookiesButton().click();
    }
    
    private ButtonElement getAcceptCookiesButton() {
        return new ButtonElement("Accept cookies button", ACCEPT_COOKIES_BUTTON_LOCATOR);
    }
    
}
