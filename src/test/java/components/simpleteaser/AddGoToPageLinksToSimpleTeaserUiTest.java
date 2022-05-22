package components.simpleteaser;

import builders.SimpleTeaserBuilder;
import base.AbstractTest;
import entities.Components;
import entities.Page;
import entities.User;
import entities.components.SimpleTeaserEntity;
import entities.enums.*;
import helpers.ApiHelper;
import helpers.NavigationHelper;
import helpers.StringManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.components.simpleteaser.SimpleTeaserCtaButtonLinkTabPage;
import pages.components.simpleteaser.SimpleTeaserPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.PreviewModeIFramePage;
import pages.page.editor.PublishedViewPage;
import scenarios.ComponentScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;
import utils.WebDriverFactory;

class AddGoToPageLinksToSimpleTeaserUiTest extends AbstractTest {
    private static final int CTA_LINKS_MAX_NUMBER = 3;
    private static final String ALERT_MESSAGE = "Maximum of " + CTA_LINKS_MAX_NUMBER + " multi-field items allowed";
    private static final String EXTERNAL_LINK_URL = "www.google.com";
    private static final int FIRST_LINK_NUMBER = 1;
    private static final int SECOND_LINK_NUMBER = 2;
    
    private Page page;
    private Page pageEmpty;
    private Components simpleTeaser = Components.SIMPLE_TEASER;
    private EditorsViewPage editorsPage;
    private SimpleTeaserCtaButtonLinkTabPage ctaButtonTab;
    
    @BeforeEach
    void precondition() {
        SimpleTeaserEntity simpleTeaserEntity = SimpleTeaserBuilder
                .createInstance()
                .withHeading(StringManager.getRandomAlphanumeric())
                .build();
        
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        simpleTeaser = ApiHelper.addComponentsToPage(page.getName(), simpleTeaser);
        ApiHelper.updateSimpleTeaser(simpleTeaserEntity, simpleTeaser, page);
        
        pageEmpty = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        ApiHelper.publishPage(pageEmpty.getName());
        
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
    }
    
    @Test
    void verifyAddingGoToPageLinksToSimpleTeaser() {
        String linkTextFirst = StringManager.getRandomAlphanumeric().toLowerCase();
        String linkTextSecond = StringManager.getRandomAlphanumeric().toLowerCase();
        String linkUrl = NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS) + StringManager.URL_SEPARATOR
                + pageEmpty.getName();
        
        openArticleComponentOnAuthorEnvironment();
        navigateToCtaButtonLinkTab();
        verifyMaxNumberOfCtaLinks();
        
        selectLinkType(FIRST_LINK_NUMBER, GeneralLinksActionTypes.GO_TO_PAGE);
        fillLinkDetails(FIRST_LINK_NUMBER, linkTextFirst, linkUrl, GeneralLinksTargets.DEFAULT, GeneralLinksViewTypes.BUTTON);
        selectLinkType(SECOND_LINK_NUMBER, GeneralLinksActionTypes.GO_TO_PAGE);
        fillLinkDetails(SECOND_LINK_NUMBER, linkTextSecond, EXTERNAL_LINK_URL, GeneralLinksTargets.DEFAULT, GeneralLinksViewTypes.BUTTON);
        
        ctaButtonTab.clickDone();
        ApiHelper.publishPage(page.getName());
        
        assertLinkIsOpenedInPreviewMode(linkUrl, linkTextFirst, true);
        assertLinkIsOpenedInPreviewMode(EXTERNAL_LINK_URL, linkTextSecond, false);
        
        assertLinkIsOpenedOnPublishEnvironment(linkUrl, linkTextFirst, true);
        assertLinkIsOpenedOnPublishEnvironment(EXTERNAL_LINK_URL, linkTextSecond, false);
        
        openArticleComponentOnAuthorEnvironment();
        navigateToCtaButtonLinkTab();
        
        fillLinkDetails(FIRST_LINK_NUMBER, linkTextFirst, linkUrl, GeneralLinksTargets.NEW_WINDOW, GeneralLinksViewTypes.LINK);
        fillLinkDetails(SECOND_LINK_NUMBER, linkTextSecond, EXTERNAL_LINK_URL, GeneralLinksTargets.SAME_WINDOW, GeneralLinksViewTypes.LINK);
        ctaButtonTab.clickDone();
        ApiHelper.publishPage(page.getName());
        
        assertLinkIsOpenedInPreviewMode(linkUrl, linkTextFirst, false);
        assertLinkIsOpenedInPreviewMode(EXTERNAL_LINK_URL, linkTextSecond, true);
        
        assertLinkIsOpenedOnPublishEnvironment(linkUrl, linkTextFirst, false);
        assertLinkIsOpenedOnPublishEnvironment(EXTERNAL_LINK_URL, linkTextSecond, true);
        
        openArticleComponentOnAuthorEnvironment();
        navigateToCtaButtonLinkTab();
        deleteAllCTALinks();
        ctaButtonTab.clickDone();
        
        assertThatLinkIsRemovedFromComponentInPreviewMode(linkTextFirst, linkUrl);
        assertThatLinkIsRemovedFromComponentInPreviewMode(linkTextSecond, EXTERNAL_LINK_URL);
        
        ApiHelper.publishPage(page.getName());
        assertThatLinkIsRemovedFromComponentOnPublishEnvironment(linkTextFirst, linkUrl);
        assertThatLinkIsRemovedFromComponentOnPublishEnvironment(linkTextSecond, EXTERNAL_LINK_URL);
    }
    
    @AfterEach
    void afterTest() {
        ApiHelper.deletePage(page.getName());
        ApiHelper.deletePage(pageEmpty.getName());
    }
    
    private void openArticleComponentOnAuthorEnvironment() {
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.openComponentInEditMode(editorsPage, simpleTeaser);
    }
    
    private void navigateToCtaButtonLinkTab() {
        SimpleTeaserPage simpleTeaserPage = new SimpleTeaserPage();
        ctaButtonTab = simpleTeaserPage.navigateCtaButtonLinkTab();
    }
    
    private void verifyMaxNumberOfCtaLinks() {
        for (int i = 0; i <= CTA_LINKS_MAX_NUMBER; i++) {
            ctaButtonTab.clickAddButton();
        }
        WebDriverFactory.instance().checkAlertMessageAndCloseIt(ALERT_MESSAGE);
    }
    
    private void selectLinkType(int linkNumber, GeneralLinksActionTypes linkType) {
        ctaButtonTab.selectLinkType(linkNumber, linkType);
    }
    
    private void fillLinkDetails(int numberOfLink, String linkText, String linkURL, GeneralLinksTargets linkTarget, GeneralLinksViewTypes linkViewType) {
        ctaButtonTab.setLinkText(numberOfLink, linkText);
        ctaButtonTab.setLinkUrl(numberOfLink, linkURL);
        if (linkTarget != null) {
            ctaButtonTab.selectLinkTarget(numberOfLink, linkTarget);
        }
        ctaButtonTab.setLinkViewType(numberOfLink, linkViewType);
    }
    
    private void assertLinkIsOpenedInPreviewMode(String linkUrl, String linkText, boolean sameWindow) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        
        if (sameWindow) {
            previewModePage.verifyLinkIsOpenedInSameWindow(linkUrl, linkText);
        } else {
            previewModePage.verifyLinkIsOpenedInNewWindow(linkUrl, linkText);
        }
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void assertLinkIsOpenedOnPublishEnvironment(String linkUrl, String linkText, boolean sameWindow) {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        
        if (sameWindow) {
            publishedViewPage.verifyLinkIsOpenedInSameWindow(linkUrl, linkText);
        } else {
            publishedViewPage.verifyLinkIsOpenedInNewWindow(linkUrl, linkText);
        }
    }
    
    private void deleteAllCTALinks() {
        ctaButtonTab.deleteAllCtaLinks();
    }
    
    private void assertThatLinkIsRemovedFromComponentInPreviewMode(String linkUrl, String linkText) {
        editorsPage = new EditorsViewPage(page.getTitle());
        PreviewModeIFramePage previewModePage = editorsPage.switchToPreviewMode();
        
        previewModePage.verifyThatLinkIsAbsent(linkUrl, linkText);
        
        editorsPage = previewModePage.switchToEditMode(editorsPage.getPageTitle());
    }
    
    private void assertThatLinkIsRemovedFromComponentOnPublishEnvironment(String linkUrl, String linkText) {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        publishedViewPage.verifyThatLinkIsAbsent(linkUrl, linkText);
    }
}
