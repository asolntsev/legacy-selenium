package pages.dialogs;

import base.BasePage;
import elements.TextElement;
import org.openqa.selenium.By;

public class SearchDialogPage extends BasePage {
    private static final By SEARCH_INPUT_FIELD_LOCATOR = By.name("fulltext");
    private static final By SEARCH_DIALOG_PAGE_LOCATOR = By.className("hugesearch-typeahead");
    
    public SearchDialogPage() {
        super("Search dialog", SEARCH_DIALOG_PAGE_LOCATOR);
    }
    
    public TextElement getSearchTextElement() {
        return new TextElement("Search input field", SEARCH_INPUT_FIELD_LOCATOR);
    }
    
    public void setSearchText(String value) {
        getSearchTextElement().setValueByKeyPress(value);
    }
}
