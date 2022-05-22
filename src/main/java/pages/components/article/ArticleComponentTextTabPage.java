package pages.components.article;

import elements.TextElement;
import org.openqa.selenium.By;

public class ArticleComponentTextTabPage extends ArticleComponentPage {
    private static final By MAIN_HEADING_FIELD_LOCATOR = By.name("./mainHeading");
    private static final By LEAD_TEXT_FIELD_LOCATOR = By.xpath("//div[./input[@name='./leadText']]/div[@data-editor-type]");
    private static final By MAIN_TEXT_FIELD_LOCATOR = By.xpath("//div[./input[@name='./mainContent']]/div[@data-editor-type]");
    private static final By DISCLAIMER_TEXT_FIELD_LOCATOR = By.xpath("//div[./input[@name='./disclaimerText']]/div[@data-editor-type]");
    
    public void setMainHeading(String heading) {
        TextElement element = new TextElement("Main heading", MAIN_HEADING_FIELD_LOCATOR);
        element.setValueByKeyPress(heading);
    }
    
    public void setLeadText(String leadText) {
        TextElement element = new TextElement("Lead text", LEAD_TEXT_FIELD_LOCATOR);
        element.setValueByKeyPress(leadText);
    }
    
    public void setMainText(String mainText) {
        TextElement element = new TextElement("Main Text", MAIN_TEXT_FIELD_LOCATOR);
        element.setValueByKeyPress(mainText);
    }
    
    public void setDisclaimerText(String disclaimerText) {
        TextElement element = new TextElement("Disclaimer text", DISCLAIMER_TEXT_FIELD_LOCATOR);
        element.setValueByKeyPress(disclaimerText);
    }
}
