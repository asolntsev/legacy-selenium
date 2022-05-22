package elements;

import base.BaseElement;
import logging.Log;
import org.openqa.selenium.By;

public class CheckBoxElement extends BaseElement {
    public CheckBoxElement(String name, By locator) {
        super(name, locator);
    }
    
    public boolean isChecked() {
        return element().isSelected();
    }
    
    public void uncheck() {
        check(false);
    }
    
    public void check() {
        check(true);
    }
    
    private void check(boolean state) {
        Log.logInfo("Setting value '" + state + "' to the element '" + element() + getName());
        if (state && !element().isSelected()) {
            click();
        } else if (!state && element().isSelected()) {
            click();
        }
        
    }
}
