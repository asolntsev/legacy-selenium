package components.article;

import components.CreateAndDeleteComponentAbstractTest;
import entities.Components;
import org.junit.jupiter.api.Test;
import pages.components.article.ArticleComponentPage;

public class CreateAndDeleteArticleComponentUiTest extends CreateAndDeleteComponentAbstractTest {
    private static final Components ARTICLE_COMPONENT = Components.ARTICLE;
    
    @Test
    void verifyCreationAndDeletionOfArticleComponent() {
        verifyCreationAndDeletionOfComponent(ARTICLE_COMPONENT);
    }
    
    @Override
    protected void checkMandatoryData() {
        // Currently no mandatory data is set for Article component
    }
    
    @Override
    protected void fillDataAndSaveChanges() {
        new ArticleComponentPage().clickDone();
    }
}
