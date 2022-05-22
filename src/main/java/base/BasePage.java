package base;

import helpers.Timeouts;
import logging.Screenshot;
import logging.Log;
import org.openqa.selenium.By;

public class BasePage extends BaseComponent {
    
    private String name;
    private By locator;
    
    protected BasePage(String name, By locator) {
        this.name = name;
        this.locator = locator;
        assertPageIsOpened();
    }
    
    protected BasePage(String name, By locator, Timeouts timeout) {
        this.name = name;
        this.locator = locator;
        assertPageIsOpened(timeout);
    }
    
    private void assertPageIsOpened(Timeouts timeout) {
        try {
            getPageWebElement().assertExists(timeout);
        } catch (Exception e) {
            Log.logFail("Page '" + name + "' is not opened", Screenshot.fromDriver());
        }
    }
    
    public void assertPageIsOpened() {
        assertPageIsOpened(Timeouts.SLOW);
    }
    
    protected BaseElement getPageWebElement() {
        return new BaseElement(name + " page", locator);
    }
}
