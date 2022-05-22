package components.advancedteaser;

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
import pages.components.advancedteaser.AdvancedTeaserGeneralTabPage;
import pages.components.advancedteaser.AdvancedTeaserPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import scenarios.ComponentScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;

class EditAdvancedTeaserComponentUiTest extends AbstractTest {
    
    private Page page;
    private Components advancedTeaser = Components.ADVANCED_TEASER;
    
    private AdvancedTeaserPage advancedTeaserPage;
    private EditorsViewPage editorsPage;
    
    @BeforeEach
    void precondition() {
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        advancedTeaser = ApiHelper.addComponentsToPage(page.getName(), advancedTeaser);
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
    }
    
    @Test
    void verifyAdvancedTeaserEditing() {
        String heading = StringManager.getRandomAlphanumeric();
        String mainText = StringManager.getRandomAlphanumeric();
        String disclaimerText = StringManager.getRandomAlphanumeric();
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        
        navigateThroughTabsAndCheckTheyAreOpened();
        fillDataAndSaveChanges(heading, mainText, disclaimerText);
        verifyDataExistsInPreviewMode(heading, mainText, disclaimerText);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, heading, mainText, disclaimerText);
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, advancedTeaser);
        
        String headingNew = StringManager.getRandomAlphanumeric();
        String mainTextNew = StringManager.getRandomAlphanumeric();
        String disclaimerTextNew = StringManager.getRandomAlphanumeric();
        
        advancedTeaserPage = new AdvancedTeaserPage();
        fillDataAndSaveChanges(headingNew, mainTextNew, disclaimerTextNew);
        verifyDataExistsInPreviewMode(headingNew, mainTextNew, disclaimerTextNew);
        
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, heading, mainText, disclaimerText);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, headingNew, mainTextNew, disclaimerTextNew);
    }
    
    @AfterEach
    void afterTest() {
        ApiHelper.deletePage(page.getName());
    }
    
    private void navigateThroughTabsAndCheckTheyAreOpened() {
        advancedTeaserPage = new AdvancedTeaserPage();
        advancedTeaserPage.navigateBackgroundTab()
                .navigateCtaLinksTab()
                .navigateGeneralTab()
                .navigateVideoTeaserTab();
    }
    
    private void fillDataAndSaveChanges(String heading, String mainText, String disclaimerText) {
        AdvancedTeaserGeneralTabPage generalTab = advancedTeaserPage.navigateGeneralTab();
        generalTab.setHeading(heading);
        generalTab.setMainText(mainText);
        generalTab.setDisclaimerText(disclaimerText);
        
        generalTab.clickDone();
    }
    
    private void verifyDataExistsInPreviewMode(String header, String mainText, String disclaimerText) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        previewModePage.verifyAdvancedTeaserHeaderInfo(header);
        previewModePage.verifyAdvancedTeaserMainTextInfo(mainText);
        previewModePage.verifyAdvancedTeaserDisclaimerText(disclaimerText);
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void verifyDataExistsOnPublishEnvironment(PublishedViewPage publishedViewPage, String header, String mainText, String disclaimerText) {
        publishedViewPage.verifyAdvancedTeaserHeaderInfo(header);
        publishedViewPage.verifyAdvancedTeaserMainTextInfo(mainText);
        publishedViewPage.verifyAdvancedTeaserDisclaimerText(disclaimerText);
    }
    
}
