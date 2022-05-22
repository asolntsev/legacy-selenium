package components.simpleteaser;

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
import pages.components.simpleteaser.SimpleTeaserHeadingTabPage;
import pages.components.simpleteaser.SimpleTeaserMainTextTabPage;
import pages.components.simpleteaser.SimpleTeaserPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import scenarios.ComponentScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;

class EditSimpleTeaserComponentUiTest extends AbstractTest {
    
    private Page page;
    private Components simpleTeaser = Components.SIMPLE_TEASER;
    private EditorsViewPage editorsPage;
    private SimpleTeaserPage simpleTeaserPage;
    
    @BeforeEach
    void precondition() {
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        simpleTeaser = ApiHelper.addComponentsToPage(page.getName(), simpleTeaser);
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
    }
    
    @Test
    void verifySimpleTeaserEditing() {
        String heading = StringManager.getRandomAlphanumeric();
        String mainText = StringManager.getRandomAlphanumeric();
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, simpleTeaser);
        
        navigateThroughTabsAndCheckTheyAreOpened();
        fillDataAndSaveChanges(heading, mainText);
        verifyDataExistsInPreviewMode(heading, mainText);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, heading, mainText);
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, simpleTeaser);
        
        String headingNew = StringManager.getRandomAlphanumeric();
        String mainTextNew = StringManager.getRandomAlphanumeric();
        
        simpleTeaserPage = new SimpleTeaserPage();
        fillDataAndSaveChanges(headingNew, mainTextNew);
        verifyDataExistsInPreviewMode(headingNew, mainTextNew);
        
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, heading, mainText);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        verifyDataExistsOnPublishEnvironment(publishedViewPage, headingNew, mainTextNew);
    }
    
    @AfterEach
    void afterTest() {
        ApiHelper.deletePage(page.getName());
    }
    
    private void navigateThroughTabsAndCheckTheyAreOpened() {
        simpleTeaserPage = new SimpleTeaserPage();
        simpleTeaserPage.navigateCtaButtonLinkTab()
                .navigateBackgroundTab()
                .navigateHeadingTab()
                .navigateMainTextTab();
    }
    
    private void fillDataAndSaveChanges(String heading, String mainText) {
        SimpleTeaserHeadingTabPage headingTab = simpleTeaserPage.navigateHeadingTab();
        headingTab.fillHeading(heading);
        
        SimpleTeaserMainTextTabPage mainTextTab = simpleTeaserPage.navigateMainTextTab();
        mainTextTab.fillMainText(mainText);
        
        mainTextTab.clickDone();
    }
    
    private void verifyDataExistsInPreviewMode(String header, String mainText) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        
        previewModePage.verifySimpleTeaserHeaderInfo(header);
        previewModePage.verifySimpleTeaserMainTextInfo(mainText);
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void verifyDataExistsOnPublishEnvironment(PublishedViewPage publishedViewPage, String header, String mainText) {
        publishedViewPage.verifySimpleTeaserHeaderInfo(header);
        publishedViewPage.verifySimpleTeaserMainTextInfo(mainText);
    }
    
}
