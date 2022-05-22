package pages;

import base.BaseElement;
import entities.enums.NavigationItems;
import org.openqa.selenium.By;

public class NavigationPage extends MainPage {
    
    private static final By NAVIGATION_PAGE_TITLE = By.xpath("//huge-titlebar//span[contains(text(),'Navigation')]");
    private static final String NAVIGATION_ITEM_LOCATOR_TEMPLATE = "//div[./huge-icon[@aria-label='%1$s']]";
    
    public NavigationPage() {
        super("Navigation page", NAVIGATION_PAGE_TITLE);
    }
    
    public void selectMenuItem(NavigationItems item) {
        BaseElement navigationLink = new BaseElement(item.toString(),
                By.xpath(String.format(NAVIGATION_ITEM_LOCATOR_TEMPLATE, item.getValue())));
        navigationLink.click();
    }
    
}
