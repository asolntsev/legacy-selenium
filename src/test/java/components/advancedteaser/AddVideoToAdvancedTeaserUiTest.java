package components.advancedteaser;

import entities.Assets;
import helpers.DataConstants;
import helpers.StringManager;
import org.junit.jupiter.api.Test;
import pages.components.advancedteaser.AdvancedTeaserPage;
import pages.components.advancedteaser.AdvancedTeaserVideoTeaserTabPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import scenarios.ComponentScenarios;
import scenarios.PageScenarios;
import scenarios.SidePanelScenarios;

/**
 * Test for adding video
 */
public class AddVideoToAdvancedTeaserUiTest extends AddMediaToAdvancedTeaserAbstractTest {
    
    private static final Assets FILE_JPG = Assets.FILE_JPG;
    private static final String YOUTUBE_LINK_ID = StringManager.getRandomAlphabetic();
    private static final String YOUTUBE_LINK = DataConstants.YOUTUBE_LINK + YOUTUBE_LINK_ID;
    
    @Test
    void verifyAddingVideoToAdvancedTeaser() {
        String videoTitle = StringManager.getRandomAlphanumeric();
        String videoInfoTitle = StringManager.getRandomAlphanumeric();
        String videoInfo = StringManager.getRandomAlphanumeric();
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        
        openEditorsPageAndFindTheImage();
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        
        addVideoInformation(videoTitle, videoInfoTitle, videoInfo);
        verifyVideoExistsInPreviewMode(YOUTUBE_LINK_ID, videoInfoTitle, videoInfo, videoTitle);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        verifyVideoExistsOnPublishedPage(YOUTUBE_LINK_ID, videoInfoTitle, videoInfo, videoTitle);
    }
    
    private void addVideoInformation(String videoTitle, String videoInfoTitle, String videoInfo) {
        advancedTeaserPage = new AdvancedTeaserPage();
        SidePanelScenarios.dragAndDropVideoPreviewImageToAdvancedTeaser(sidePanel, advancedTeaserPage, fullFileName);
        
        AdvancedTeaserVideoTeaserTabPage videoTeaserTab = advancedTeaserPage.navigateVideoTeaserTab();
        videoTeaserTab.setVideoTitle(videoTitle);
        videoTeaserTab.setVideoInfoTitle(videoInfoTitle);
        videoTeaserTab.setVideoInfo(videoInfo);
        videoTeaserTab.setVideoUrl(YOUTUBE_LINK);
        
        advancedTeaserPage.clickDone();
    }
    
    @Override
    protected Assets getFile() {
        return FILE_JPG;
    }
    
    private void verifyVideoExistsInPreviewMode(String youtubeUrlID, String videoInfoTitle, String videoInfo, String videoTitle) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        previewModePage.verifyAdvancedTeaserVideoUrlExists(youtubeUrlID);
        previewModePage.verifyAdvancedTeaserVideoInfo(videoInfoTitle, videoInfo);
        previewModePage.verifyAdvancedTeaserVideoTitle(videoTitle);
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void verifyVideoExistsOnPublishedPage(String youtubeUrlID, String videoInfoTitle, String videoInfo, String videoTitle) {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        publishedViewPage.verifyAdvancedTeaserVideoUrlExists(youtubeUrlID);
        publishedViewPage.verifyAdvancedTeaserVideoInfo(videoInfoTitle, videoInfo);
        publishedViewPage.verifyAdvancedTeaserVideoTitle(videoTitle);
    }
}
