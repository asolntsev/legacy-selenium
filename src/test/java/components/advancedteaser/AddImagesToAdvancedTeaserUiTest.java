package components.advancedteaser;

import entities.Assets;
import org.junit.jupiter.api.Test;
import pages.components.advancedteaser.AdvancedTeaserGeneralTabPage;
import pages.components.advancedteaser.AdvancedTeaserPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import scenarios.ComponentScenarios;
import scenarios.PageScenarios;
import scenarios.SidePanelScenarios;

/**
 * Test for adding images
 */
class AddImagesToAdvancedTeaserUiTest extends AddMediaToAdvancedTeaserAbstractTest {
    
    private static final Assets FILE_JPG = Assets.FILE_JPG;
    private static final String LAYOUT_1 = "1/2 text 1/2 image";
    private static final String LAYOUT_2 = "2/3 text 1/3 image";
    private static final String LAYOUT_3 = "Only text";
    
    @Test
    void verifyAddingMainImageToAdvancedTeaser() {
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        
        openEditorsPageAndFindTheImage();
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        
        addMainImageToAdvancedTeaser();
        selectLayout(LAYOUT_1);
        verifyImageExistsInPreviewMode(true);
        
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        selectLayout(LAYOUT_3);
        verifyImageExistsInPreviewMode(false);
        
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        selectLayout(LAYOUT_2);
        verifyImageExistsInPreviewMode(true);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        verifyImageExistsOnPublishEnvironment(fullFileName);
    }
    
    @Test
    void verifyAddingBackgroundImageToAdvancedTeaser() {
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        
        openEditorsPageAndFindTheImage();
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        
        addBackgroundImageToAdvancedTeaser();
        verifyImageExistsInPreviewMode(true);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        verifyImageExistsOnPublishEnvironment(fullFileName);
    }
    
    @Override
    protected Assets getFile() {
        return FILE_JPG;
    }
    
    private void selectLayout(String layout) {
        advancedTeaserPage = new AdvancedTeaserPage();
        AdvancedTeaserGeneralTabPage generalTab = advancedTeaserPage.navigateGeneralTab();
        generalTab.selectLayout(layout);
        advancedTeaserPage.clickDone();
    }
    
    private void addMainImageToAdvancedTeaser() {
        advancedTeaserPage = new AdvancedTeaserPage();
        SidePanelScenarios.dragAndDropMainImageToAdvancedTeaser(sidePanel, advancedTeaserPage, fullFileName);
    }
    
    private void addBackgroundImageToAdvancedTeaser() {
        advancedTeaserPage = new AdvancedTeaserPage();
        SidePanelScenarios.dragAndDropBackgroundImageToAdvancedTeaser(sidePanel, advancedTeaserPage, fullFileName);
        advancedTeaserPage.clickDone();
    }
    
    private void verifyImageExistsInPreviewMode(boolean exists) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        previewModePage.verifyAdvancedTeaserImageExistence(fullFileName, exists);
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void verifyImageExistsOnPublishEnvironment(String fullFileName) {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        publishedViewPage.verifyImageExistence(fullFileName, true);
    }
    
}
