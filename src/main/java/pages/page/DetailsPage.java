package pages.page;

import base.BasePage;
import elements.ButtonElement;
import org.openqa.selenium.By;

public class DetailsPage extends BasePage {
    private static final By CREATE_PAGE_LOCATOR = By.xpath("//div[contains(@class, 'huge-Heading') and contains(text" +
            "(), 'Create Page')]");
    private static final By NEXT_BUTTON_LOCATOR = By.xpath
            ("//huge-panel[@selected]//button[@data-foundation-wizard-control-action='next']");
    private static final By BACK_BUTTON_LOCATOR = By.xpath
            ("//huge-panel[@selected]//button[@data-foundation-wizard-control-action='prev']");
    private static final By CANCEL_BUTTON_LOCATOR = By.xpath
            ("//huge-panel[@selected]//a[@data-foundation-wizard-control-action='cancel']");
    
    public DetailsPage() {
        super("Create page", CREATE_PAGE_LOCATOR);
    }
    
    protected DetailsPage(String name, By path) {
        super(name, path);
    }
    
    public ButtonElement getNextCreateButton() {
        return new ButtonElement("Next button", NEXT_BUTTON_LOCATOR);
    }
    
    public ButtonElement getBackButton() {
        return new ButtonElement("Back button", BACK_BUTTON_LOCATOR);
    }
    
    public ButtonElement getCancelButton() {
        return new ButtonElement("Cancel button", CANCEL_BUTTON_LOCATOR);
    }
    
    public void clickNextButton() {
        getNextCreateButton().click();
    }
    
}
