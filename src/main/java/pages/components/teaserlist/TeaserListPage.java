package pages.components.teaserlist;

import entities.Components;
import org.openqa.selenium.By;
import pages.components.ComponentPage;

public class TeaserListPage extends ComponentPage {
    private static final By TEASER_LIST_PAGE_LOCATOR
            = By.xpath(String.format("//form[contains(@action,'%1$s')]", Components.TEASER_LIST.getDataPath()));
    
    public TeaserListPage() {
        super("Teaser list page", TEASER_LIST_PAGE_LOCATOR);
    }
    
}
