package pages.components;

import base.BasePage;
import elements.LinkElement;
import elements.TextElement;
import entities.Components;
import helpers.Environment;
import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

public class InsertNewComponentPage extends BasePage {
    
    private static final By INSERT_COMPONENT_DIALOG_LOCATOR =
            By.xpath("//huge-dialog[contains(@class,'huge-Dialog InsertComponentDialog')]");
    
    private static final By KEYWORD_TEXT_LOCATOR = By.xpath("//input[contains(@class,'input huge-Search-input')]");
    private static final String RESULT_LOCATOR_TEMPLATE = "//huge-select-list-item[text()= '%1$s']";
    
    public InsertNewComponentPage() {
        super("Insert new component", INSERT_COMPONENT_DIALOG_LOCATOR);
    }
    
    private TextElement getInputKeywordField() {
        return new TextElement("Enter keyword", this.getPageWebElement(), KEYWORD_TEXT_LOCATOR);
    }
    
    public void enterKeyword(String value) {
        getInputKeywordField().click();
        getInputKeywordField().setValueByKeyPress(value);
    }
    
    public void selectResult(Components component) {
        selectResult(component.getName());
    }
    
    private void selectResult(String result, boolean retry) {
        Environment.waitForJs();
        try {
            new LinkElement("Result value " + result,
                    By.xpath(String.format(RESULT_LOCATOR_TEMPLATE, result))).clickViaJS();
        } catch (StaleElementReferenceException ex) {
            if (retry) {
                Log.logDebug("I WAS HERE");
                selectResult(result, false);
            }
        }
    }
    
    public void selectResult(String result) {
        selectResult(result, true);
    }
    
}
