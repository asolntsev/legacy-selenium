package pages.page.config;

import elements.TextElement;
import org.openqa.selenium.By;
import pages.page.DetailsPage;

public class BasicTabDetailsPage extends DetailsPage {
    private static final By BASIC_TAB_DETAILS_PAGE_LOCATOR = By.id("page-dialog-basic-tab");
    
    private static final By TITLE_FIELD_LOCATOR = By.name("./jcr:title");
    private static final By NAVIGATION_TITLE_FIELD_LOCATOR = By.name("./navTitle");
    private static final By NAME_URL_FIELD_LOCATOR = By.name("pageName");
    
    public BasicTabDetailsPage() {
        super("Basic tab details", BASIC_TAB_DETAILS_PAGE_LOCATOR);
    }
    
    public void fillTitle(String title) {
        TextElement titleField = new TextElement("Title", TITLE_FIELD_LOCATOR);
        titleField.setValueByKeyPress(title);
    }
    
    public void fillNavigationTitle(String navigationTitle) {
        TextElement navigationTitleField = new TextElement("Navigation title", NAVIGATION_TITLE_FIELD_LOCATOR);
        navigationTitleField.setValueByKeyPress(navigationTitle);
    }
    
    public TextElement getNameUrlElement() {
        return new TextElement("Page name", NAME_URL_FIELD_LOCATOR);
    }
    
    public void fillNameUrl(String value) {
        getNameUrlElement().setValueByKeyPress(value);
    }
    
}
