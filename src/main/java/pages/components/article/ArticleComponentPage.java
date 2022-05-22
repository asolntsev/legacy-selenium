package pages.components.article;

import elements.ButtonElement;
import entities.Components;
import org.openqa.selenium.By;
import pages.components.ComponentPage;

public class ArticleComponentPage extends ComponentPage {
    private static final By ARTICLE_PAGE_LOCATOR
            = By.xpath(String.format("//form[contains(@action,'%1$s')]", Components.ARTICLE.getDataPath()));
    private static final By TAB_TEXT_LOCATOR = By.xpath("//a[@aria-controls='article-dialog-text-section-tab']");
    private static final By TAB_MEDIA_LOCATOR = By.xpath("//a[@aria-controls='article-dialog-media-section-tab']");
    
    public ArticleComponentPage() {
        super("Article page", ARTICLE_PAGE_LOCATOR);
    }
    
    public ArticleComponentPage(String name, By locator) {
        super(name, locator);
    }
    
    public ArticleComponentTextTabPage navigateTextTab() {
        new ButtonElement("Article text tab button", TAB_TEXT_LOCATOR).click();
        return new ArticleComponentTextTabPage();
    }
    
    public ArticleComponentMediaTabPage navigateMediaTab() {
        new ButtonElement("Article media tab button", TAB_MEDIA_LOCATOR).click();
        return new ArticleComponentMediaTabPage();
    }
    
}
