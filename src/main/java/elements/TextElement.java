package elements;

import base.BaseElement;
import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class TextElement extends BaseElement {
    public TextElement(String name, By locator) {
        super(name, locator);
    }
    
    public TextElement(String name, BaseElement parentElement, By locator) {
        super(name, parentElement, locator);
    }
    
    /**
     * Set value using javascript
     *
     * @param value
     */
    public void setValue(String value) {
        element().clear();
        ((JavascriptExecutor) currentDriver).executeScript(String.format("arguments[0].value='%1$s'", value),
                element());
        
        Log.logInfo("Value '" + value + "' successfully set");
    }
    
    /**
     * Set value by key press
     *
     * @param value
     */
    public void setValueByKeyPress(String value) {
        element().clear();
        element().sendKeys(value);
        
        Log.logInfo("Value '" + value + "' successfully set");
    }
    
}
