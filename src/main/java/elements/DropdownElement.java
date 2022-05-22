package elements;

import base.BaseElement;
import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DropdownElement extends BaseElement {
    
    public DropdownElement(String name, By locator) {
        super(name, locator);
    }
    
    public DropdownElement(String name, BaseElement parentElement, By locator) {
        super(name, parentElement, locator);
    }
    
    public String getSelectedText() {
        return (new Select(element())).getFirstSelectedOption().getText();
    }
    
    public void checkSelectedText(String expected) {
        String actual = getSelectedText();
        
        if (actual.equalsIgnoreCase(expected)) {
            Log.logInfo("Expected value equals to the actual: " + actual);
        } else {
            Log.logFail(String.format("Actual value '%1$s' is not equal to the expected '%2$s'",
                    actual, expected));
        }
    }
    
    public void selectOptionFromDropdownByValue(String value) {
        new Select(element()).selectByValue(value);
        Log.logInfo("Selected value '" + value + "' from element '" + name + "'");
    }
    
    public void selectOptionFromDropdownByVisibleText(String value) {
        new Select(element()).selectByVisibleText(value);
        Log.logInfo("Selected value '" + value + "' from element '" + name + "'");
    }
    
    public void selectOptionFromHugeDropdownByValue(String value) {
        List<WebElement> options = element().findElements(By.xpath(".//huge-select-list-item"));
        boolean matched = false;
    
        for (WebElement option : options) {
            if (option.getAttribute("value").equals(value)) {
                this.clickViaJS();
                ((JavascriptExecutor) currentDriver).executeScript("arguments[0].click();", option);
                matched = true;
            }
        }
        
        if (!matched) {
            throw new NoSuchElementException("Cannot locate option with value: " + value);
        }
        
        Log.logInfo("Selected value '" + value + "' from element '" + name + "'");
    }
    
}
