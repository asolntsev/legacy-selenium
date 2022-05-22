package pages;

import base.BasePage;
import elements.ButtonElement;
import org.openqa.selenium.By;

public class MainPage extends BasePage {
    private static final By USER_ICON_LOCATOR = By.xpath("//huge-shell-menubar-item[@title='User']/button");
    private static final By SEARCH_ICON_LOCATOR = By.xpath
            ("//huge-shell-menubar-item[@id='hugesearch-trigger']//button");

    public MainPage() {
        super("Main page", USER_ICON_LOCATOR);
    }
    
    protected MainPage(String name, By locator) {
        super(name, locator);
    }
    
    public void clickOnSearchButton() {
        new ButtonElement("Search button", SEARCH_ICON_LOCATOR).click();
    }
    
}
