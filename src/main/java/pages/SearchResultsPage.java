package pages;

import base.BasePage;
import elements.LabelElement;
import org.openqa.selenium.By;

public class SearchResultsPage extends BasePage {
    private static final By SEARCH_PAGE_LOCATOR = By.id("hugesearch-result-header");
    private static final String SEARCH_RESULT_LOCATOR_TEMPLATE = "//huge-card-title[contains(text(),'%1$s')]";
    
    public SearchResultsPage() {
        super("Search result page", SEARCH_PAGE_LOCATOR);
    }
    
    public LabelElement getSearchResultByText(String value) {
        return new LabelElement("Search result", By.xpath(String.format(SEARCH_RESULT_LOCATOR_TEMPLATE, value)));
    }
}
