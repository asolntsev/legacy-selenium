package elements;

import base.BaseElement;
import org.openqa.selenium.By;

public class ButtonElement extends BaseElement {
    public ButtonElement(String name, By locator) {
        super(name, locator);
    }
    
    public ButtonElement(String name, BaseElement parentElement, By locator) {
            super(name, parentElement, locator);
    }
    
}
