package pages.page;

import elements.ButtonElement;
import entities.enums.PageTypes;
import org.openqa.selenium.By;

public class SelectTypePage extends DetailsPage {
    private static final String PAGE_TYPE_LOCATOR_TEMPLATE = "//huge-item[contains" +
            "(@data-foundation-collection-item-id, '%1$s')]";
    
    public SelectTypePage() {
        super("Select page type", By.xpath(String.format(PAGE_TYPE_LOCATOR_TEMPLATE, "")));
    }
    
    public void selectPageType(PageTypes pageType) {
        ButtonElement pageTypeButton = new ButtonElement("Page type", By.xpath(String.format
                (PAGE_TYPE_LOCATOR_TEMPLATE, pageType.getValue())));
        pageTypeButton.click();
        clickNextButton();
    }
}
