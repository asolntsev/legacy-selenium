package scenarios;

import base.BaseComponent;
import entities.enums.AssetTypes;
import helpers.Timeouts;
import pages.components.advancedteaser.AdvancedTeaserBackgroundTabPage;
import pages.components.advancedteaser.AdvancedTeaserGeneralTabPage;
import pages.components.advancedteaser.AdvancedTeaserPage;
import pages.components.advancedteaser.AdvancedTeaserVideoTeaserTabPage;
import pages.components.article.ArticleComponentMediaTabPage;
import pages.components.article.ArticleComponentPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.SidePanelPage;

public class SidePanelScenarios extends BaseComponent {
    
    public static SidePanelPage openPanelAndPerformSearchForFileOnAuthorPart(EditorsViewPage editorsView, String fullFileName, AssetTypes assetType) {
        SidePanelPage sidePanel = editorsView.clickOnToggleSidePanelButton();
        
        sidePanel.selectAssetType(assetType);
        sidePanel.setAssetNameAndPerformSearch(fullFileName);
        return sidePanel;
    }
    
    public static void verifyResultFound(SidePanelPage sidePanel, String fullFileName) {
        sidePanel.getSearchResult(fullFileName).assertExists(Timeouts.SLOW);
    }
    
    public static void verifyResultNotFound(SidePanelPage sidePanel, String fullFileName) {
        sidePanel.getSearchResult(fullFileName).assertDoesNotExist(Timeouts.SLOW);
    }
    
    public static void dragAndDropMainImageToAdvancedTeaser(SidePanelPage sidePanel, AdvancedTeaserPage advancedTeaserPage, String fullImageName) {
        AdvancedTeaserGeneralTabPage generalTab = advancedTeaserPage.navigateGeneralTab();
        sidePanel.getSearchResult(fullImageName).dragAndDrop(generalTab.getMainImagePlaceholder());
    }
    
    public static void dragAndDropBackgroundImageToAdvancedTeaser(SidePanelPage sidePanel, AdvancedTeaserPage advancedTeaserPage, String fullImageName) {
        AdvancedTeaserBackgroundTabPage backgroundTab = advancedTeaserPage.navigateBackgroundTab();
        sidePanel.getSearchResult(fullImageName).dragAndDrop(backgroundTab.getBackgroundImagePlaceholder());
    }
    
    public static void dragAndDropVideoPreviewImageToAdvancedTeaser(SidePanelPage sidePanel, AdvancedTeaserPage advancedTeaserPage, String fullImageName) {
        AdvancedTeaserVideoTeaserTabPage videoTeaserTab = advancedTeaserPage.navigateVideoTeaserTab();
        sidePanel.getSearchResult(fullImageName).dragAndDrop(videoTeaserTab.getVideoPreviewImagePlaceholder());
    }
    
    public static void dragAndDropMainImageToArticleComponent(SidePanelPage sidePanel, ArticleComponentPage articleComponentPage, String fullImageName) {
        ArticleComponentMediaTabPage mediaTab = articleComponentPage.navigateMediaTab();
        sidePanel.getSearchResult(fullImageName).dragAndDrop(mediaTab.getMainImagePlaceholder());
    }
    
    public static void dragAndDropThumbnailImageToArticleComponent(SidePanelPage sidePanel, ArticleComponentPage articleComponentPage, String fullImageName) {
        ArticleComponentMediaTabPage mediaTab = articleComponentPage.navigateMediaTab();
        sidePanel.getSearchResult(fullImageName).dragAndDrop(mediaTab.getThumbnailImageThumbnail());
    }
}
