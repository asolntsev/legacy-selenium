package pages.page.editor;

import base.BasePage;
import elements.DropdownElement;
import elements.LabelElement;
import elements.LinkElement;
import elements.TextElement;
import entities.enums.AssetTypes;
import helpers.Environment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SidePanelPage extends BasePage {
    private static final By SIDE_PANEL_LOCATOR = By.id("SidePanel");
    private static final By ASSET_TYPE_LOCATOR = By.xpath("//huge-select[@name='assetfilter_type_selector']/select");
    private static final By ASSET_SEARCH_INPUT_LOCATOR = By.id("asset-search");
    private static final By SEARCH_RESULTS_LOCATOR = By.xpath("//div[contains(@class,'SidePanel-results')]//huge-card-title");
    private static final String SEARCH_RESULT_LOCATOR_TEMPLATE = "//div[contains(@class,'SidePanel-results')]//huge-card-title[@title='%s']";
    
    public SidePanelPage() {
        super("Side panel", SIDE_PANEL_LOCATOR);
    }
    
    public void selectAssetType(AssetTypes assetType) {
        new DropdownElement("Assets type dropdown", ASSET_TYPE_LOCATOR).selectOptionFromDropdownByValue(assetType.getValue());
    }
    
    public void setAssetNameAndPerformSearch(String assetName) {
        TextElement assetSearch = new TextElement("Assets search", ASSET_SEARCH_INPUT_LOCATOR);
        assetSearch.setValueByKeyPress(assetName);
        assetSearch.hitEnter();
        
        Environment.waitForJs();
    }
    
    public List<WebElement> getSearchResults() {
        return new LabelElement("label", SEARCH_RESULTS_LOCATOR).elements();
    }
    
    public LinkElement getSearchResult(String fullFileName) {
        return new LinkElement("Search result", By.xpath(String.format(SEARCH_RESULT_LOCATOR_TEMPLATE, fullFileName)));
    }
    
}
