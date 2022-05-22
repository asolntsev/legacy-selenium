package base;

import helpers.Timeouts;
import helpers.Verifications;
import logging.Screenshot;
import logging.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WebDriverFactory;

import java.util.List;

/**
 * Base class for all elements. Contains all dialogs methods
 */
public class BaseElement extends BaseComponent {
    protected final By locator;
    protected final WebDriver currentDriver;
    protected final String name;
    protected final BaseElement parentElement;
    
    public BaseElement(String name, By locator) {
        this.locator = locator;
        this.currentDriver = WebDriverFactory.instance().getInitializedDriver();
        this.name = name;
        this.parentElement = null;
    }
    
    public BaseElement(String name, BaseElement parentElement, By locator) {
        this.locator = locator;
        this.currentDriver = WebDriverFactory.instance().getInitializedDriver();
        this.name = name;
        this.parentElement = parentElement;
    }
    
    public void assertValue(String expected) {
        Log.logInfo("Validating expected value '" + expected + "' on the TextElement " + name);
        
        String actual = getValue();
        if (actual.equalsIgnoreCase(expected)) {
            Log.logPass("Expected value equals to the actual: " + actual);
        } else {
            Log.logFail("Actual value '" + actual + "' is not equal to the expected '" + expected + "'",
                    Screenshot.fromDriver());
        }
    }
    
    public String getValue() {
        return element().getAttribute("value");
    }
    
    public void assertExists() {
        assertExists(Timeouts.FAST);
    }
    
    public void assertExists(Timeouts timeout) {
        Verifications.assertTrue(exists(timeout), "Element [" + name + "] should exist, but was not found");
    }
    
    public void assertDoesNotExist(Timeouts timeout) {
        Verifications.assertTrue(absent(timeout), "Element [" + name + "] should not exist, but was found");
    }
    
    public void assertDoesNotExist() {
        assertDoesNotExist(Timeouts.FAST);
    }
    
    public void assertIsNotVisible() {
        Verifications.assertFalse(element().isDisplayed(), "Element [" + name + "] should not be visible, but was " +
                "displayed");
    }
    
    public void assertDisabled() {
        Verifications.assertFalse(element().isEnabled(), "Element should be disabled, but was enabled");
    }
    
    public void assertEnabled() {
        waitForElementToBeClickable();
        Verifications.assertTrue(element().isEnabled(), "Element should be enabled, but was disabled");
    }
    
    public WebElement element() {
        return parentElement == null ? currentDriver.findElement(locator) : parentElement.element().findElement
                (locator);
    }
    
    public List<WebElement> elements() {
        return currentDriver.findElements(locator);
    }
    
    public String getName() {
        return name;
    }
    
    public void click() {
        assertExists();
        waitForElementToBeClickable();
        Log.logInfo("Clicking on the element '" + getName() + "'");
        element().click();
    }
    
    public void clickViaJS() {
        assertExists();
        waitForElementToBeClickable();
        ((JavascriptExecutor) currentDriver).executeScript("arguments[0].click();", element());
        Log.logInfo("Clicked on the element '" + getName() + "'");
    }
    
    public void clickViaAction() {
        assertExists();
        waitForElementToBeClickable();
        Actions action = new Actions(currentDriver);
        action.click(element()).perform();
        Log.logInfo("Clicked on the element '" + getName() + "'");
    }
    
    public boolean exists(Timeouts timeout) {
        waitForElementToBePresent(timeout);
        return !elements().isEmpty();
    }
    
    public boolean exists() {
        return exists(Timeouts.NULL);
    }
    
    public boolean absent(Timeouts timeout) {
        waitForElementToBeAbsent(timeout);
        return elements().isEmpty();
    }
    
    public boolean absent() {
        return absent(Timeouts.NULL);
    }
    
    public void waitForElementToBeClickable() {
        new WebDriverWait(currentDriver, Timeouts.FAST.getValue()).until(ExpectedConditions.elementToBeClickable
                (locator));
    }
    
    public void waitForElementToBeVisible() {
        new WebDriverWait(currentDriver, Timeouts.FAST.getValue()).until(ExpectedConditions.visibilityOf(element()));
    }
    
    public void waitForElementToBePresent() {
        waitForElementToBePresent(Timeouts.FAST);
    }
    
    public void waitForElementToBePresent(Timeouts timeout) {
        try {
            new WebDriverWait(currentDriver, timeout.getValue()).until(ExpectedConditions.presenceOfElementLocated
                    (locator));
        } catch (TimeoutException e) {
        
        }
    }
    
    public void waitForElementToBeAbsent(Timeouts timeout) {
        try {
            new WebDriverWait(currentDriver, timeout.getValue()).until(condition -> !element().isDisplayed());
        } catch (TimeoutException e) {
        
        }
    }
    
    protected WebElement getLastElement() {
        List<WebElement> allElements = elements();
        return allElements.get(allElements.size() - 1);
    }
    
    protected WebElement getFirstElement() {
        List<WebElement> allElements = elements();
        return allElements.get(0);
    }
    
    public void moveMouseToElement() {
        Actions builder = new Actions(currentDriver);
        builder.moveToElement(element()).build().perform();
    }
    
    public void scrollToElement() {
        ((JavascriptExecutor) currentDriver).executeScript("arguments[0].scrollIntoView(true);", element());
    }
    
    public void switchDriverToFrameElement() {
        WebDriverFactory.instance().getInitializedDriver().switchTo().frame(element());
    }
    
    public By getLocator() {
        return locator;
    }
    
    public void hitEnter() {
        element().sendKeys(Keys.ENTER);
    }
    
    public void dragAndDrop(BaseElement toElement) {
        toElement.scrollToElement();
        
        Actions action = new Actions(WebDriverFactory.instance().getInitializedDriver());
        Action dragDrop = action.dragAndDrop(element(), toElement.element()).build();
        dragDrop.perform();
    }
    
}
