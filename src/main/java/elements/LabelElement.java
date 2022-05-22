package elements;

import base.BaseElement;
import org.openqa.selenium.By;

public class LabelElement extends BaseElement {
    
    public LabelElement(String name, By locator) {
        super(name, locator);
    }
    
    public LabelElement(String name, BaseElement parent, By locator) {
        super(name, parent, locator);
    }
    
}
