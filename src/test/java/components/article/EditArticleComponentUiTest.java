package components.article;

import base.AbstractTest;
import entities.Components;
import entities.Page;
import entities.User;
import entities.enums.PageTemplates;
import helpers.ApiHelper;
import helpers.StringManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.components.article.ArticleComponentPage;
import pages.components.article.ArticleComponentTextTabPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import scenarios.ComponentScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;

public class EditArticleComponentUiTest extends AbstractTest {
    
    private Page page;
    private Components article = Components.ARTICLE;
    private EditorsViewPage editorsPage;
    private ArticleComponentPage articleComponent;
    
    @BeforeEach
    void precondition() {
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        article = ApiHelper.addComponentsToPage(page.getName(), article);
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
    }
    
    @Test
    void verifyArticleComponentEditing() {
        String heading = StringManager.getRandomAlphanumeric();
        String leadText = StringManager.getRandomAlphanumeric();
        String mainText = StringManager.getRandomAlphanumeric();
        String disclaimerText = StringManager.getRandomAlphanumeric();
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, article);
        navigateThroughTabsAndCheckTheyAreOpened();
        fillDataAndSaveChanges(heading, leadText, mainText, disclaimerText);
        verifyDataExistsInPreviewMode(heading, leadText, mainText, disclaimerText);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, heading, leadText, mainText, disclaimerText);
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, article);
        
        String headingNew = StringManager.getRandomAlphanumeric();
        String leadTextNew = StringManager.getRandomAlphanumeric();
        String mainTextNew = StringManager.getRandomAlphanumeric();
        String disclaimerTextNew = StringManager.getRandomAlphanumeric();
        
        articleComponent = new ArticleComponentPage();
        fillDataAndSaveChanges(headingNew, leadTextNew, mainTextNew, disclaimerTextNew);
        verifyDataExistsInPreviewMode(headingNew, leadTextNew, mainTextNew, disclaimerTextNew);
        
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, heading, leadText, mainText, disclaimerText);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, headingNew, leadTextNew, mainTextNew, disclaimerTextNew);
    }
    
    @AfterEach
    void afterTest() {
        ApiHelper.deletePage(page.getName());
    }
    
    private void navigateThroughTabsAndCheckTheyAreOpened() {
        articleComponent = new ArticleComponentPage();
        articleComponent.navigateMediaTab()
                .navigateTextTab();
    }
    
    private void fillDataAndSaveChanges(String heading, String leadText, String mainText, String disclaimerText) {
        ArticleComponentTextTabPage textTab = articleComponent.navigateTextTab();
        textTab.setMainHeading(heading);
        textTab.setLeadText(leadText);
        textTab.setMainText(mainText);
        textTab.setDisclaimerText(disclaimerText);
        
        textTab.clickDone();
    }
    
    private void verifyDataExistsInPreviewMode(String heading, String leadText, String mainText, String disclaimerText) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        
        previewModePage.verifyArticleComponentMainHeadingInfo(heading);
        previewModePage.verifyArticleComponentLeadTextInfo(leadText);
        previewModePage.verifyArticleComponentMainTextInfo(mainText);
        previewModePage.verifyArticleComponentDisclaimerTextInfo(disclaimerText);
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void verifyDataExistsOnPublishEnvironment(PublishedViewPage publishedViewPage, String heading, String leadText,
                                                      String mainText, String disclaimerText) {
        publishedViewPage.verifyArticleComponentMainHeadingInfo(heading);
        publishedViewPage.verifyArticleComponentLeadTextInfo(leadText);
        publishedViewPage.verifyArticleComponentMainTextInfo(mainText);
        publishedViewPage.verifyArticleComponentDisclaimerTextInfo(disclaimerText);
    }
}
