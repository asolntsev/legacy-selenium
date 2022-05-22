package pages.components.advancedteaser;

import elements.LabelElement;
import org.openqa.selenium.By;

public class AdvancedTeaserBackgroundTabPage extends AdvancedTeaserPage {
    private static final By FILE_UPLOAD_LABEL_LOCATOR = By.name("./background-image/image");
    private static final By BACKGROUND_IMAGE_DRAG_AND_DROP_LOCATOR = By.name("./background-image/image");
    
    public AdvancedTeaserBackgroundTabPage() {
        super("Advanced teaser background tab", FILE_UPLOAD_LABEL_LOCATOR);
    }
    
    public LabelElement getBackgroundImagePlaceholder() {
        return new LabelElement("Image placeholder", BACKGROUND_IMAGE_DRAG_AND_DROP_LOCATOR);
    }
}
