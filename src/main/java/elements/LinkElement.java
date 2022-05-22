package elements;

import base.BaseElement;
import org.openqa.selenium.By;

public class LinkElement extends BaseElement {
    
    public LinkElement(String name, By locator) {
        super(name, locator);
    }
    
    public LinkElement(String name, BaseElement parentElement, By locator) {
        super(name, parentElement, locator);
    }
}
