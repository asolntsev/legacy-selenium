package components.article;

import base.AbstractTest;
import entities.Assets;
import entities.Components;
import entities.Page;
import entities.User;
import entities.enums.AssetTypes;
import entities.enums.PageTemplates;
import helpers.ApiHelper;
import helpers.DataConstants;
import helpers.StringManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.components.article.ArticleComponentMediaTabPage;
import pages.components.article.ArticleComponentPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import pages.page.editor.SidePanelPage;
import scenarios.ComponentScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;
import scenarios.SidePanelScenarios;

public class AddMediaToArticleComponentUiTest extends AbstractTest {
    private static final Assets FILE_JPG = Assets.FILE_JPG;
    private static final String YOUTUBE_LINK_ID = StringManager.getRandomAlphabetic();
    private static final String YOUTUBE_LINK = DataConstants.YOUTUBE_LINK + YOUTUBE_LINK_ID;
    
    private Components article = Components.ARTICLE;
    
    private Page page;
    private String fullFileName;
    private EditorsViewPage editorsViewPage;
    private SidePanelPage sidePanel;
    private ArticleComponentPage articleComponentPage;
    
    private enum MediaTypes {
        IMAGE, VIDEO
    }
    
    @BeforeEach
    void precondition() {
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        ApiHelper.addComponentsToPage(page.getName(), Components.ARTICLE);
        fullFileName = ApiHelper.uploadFile(FILE_JPG);
        
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
    }
    
    @Test
    void verifyAddingImageToArticleComponent() {
        openSidePanelAndComponentInEditMode();
        
        navigateToMediaTabAndSelectMediaType(MediaTypes.IMAGE);
        addMainImageToArticle();
        
        verifyImageExistsInPreviewMode();
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        verifyImageExistsOnPublishEnvironment();
    }
    
    @Test
    void verifyAddingYoutubeVideoToArticleComponent() {
        openSidePanelAndComponentInEditMode();
        
        navigateToMediaTabAndSelectMediaType(MediaTypes.VIDEO);
        addVideoFromYoutube();
        
        verifyImageExistsInPreviewMode();
        verifyVideoExistsInPreviewMode();
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        verifyImageExistsOnPublishEnvironment();
        verifyVideoExistsOnPublishEnvironment();
    }
    
    @AfterEach
    void cleanUp() {
        ApiHelper.deletePage(page.getName());
        ApiHelper.deleteFile(fullFileName);
    }
    
    private void openSidePanelAndComponentInEditMode() {
        editorsViewPage = PageScenarios.openPageInAuthorMode(page);
        sidePanel = SidePanelScenarios.openPanelAndPerformSearchForFileOnAuthorPart(editorsViewPage, fullFileName,
                AssetTypes.IMAGES);
        ComponentScenarios.openComponentInEditMode(editorsViewPage, article);
    }
    
    private void navigateToMediaTabAndSelectMediaType(MediaTypes mediaType) {
        articleComponentPage = new ArticleComponentPage();
        articleComponentPage.navigateMediaTab()
                .selectMediaType(mediaType.toString().toLowerCase());
    }
    
    private void addMainImageToArticle() {
        articleComponentPage = new ArticleComponentPage();
        SidePanelScenarios.dragAndDropMainImageToArticleComponent(sidePanel, articleComponentPage, fullFileName);
        
        articleComponentPage.clickDone();
    }
    
    private void verifyImageExistsInPreviewMode() {
        editorsViewPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsViewPage.switchToPreviewMode();
        previewModePage.verifyImageExistence(fullFileName, true);
        
        editorsViewPage = previewModePage.switchToEditMode(editorsViewPage.getPageTitle());
    }
    
    private void verifyImageExistsOnPublishEnvironment() {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        publishedViewPage.verifyImageExistence(fullFileName, true);
    }
    
    private void addVideoFromYoutube() {
        articleComponentPage = new ArticleComponentPage();
        ArticleComponentMediaTabPage mediaTab = articleComponentPage.navigateMediaTab();
        mediaTab.selectVideoTypeExternal();
        
        SidePanelScenarios.dragAndDropThumbnailImageToArticleComponent(sidePanel, articleComponentPage, fullFileName);
        mediaTab.setVideoUrl(YOUTUBE_LINK);
        
        articleComponentPage.clickDone();
    }
    
    private void verifyVideoExistsInPreviewMode() {
        editorsViewPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsViewPage.switchToPreviewMode();
        previewModePage.verifyVideoIFrameExistence(YOUTUBE_LINK_ID);
        
        editorsViewPage = previewModePage.switchToEditMode(editorsViewPage.getPageTitle());
    }
    
    private void verifyVideoExistsOnPublishEnvironment() {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        publishedViewPage.verifyVideoIFrameExistence(YOUTUBE_LINK_ID);
    }
    
}
