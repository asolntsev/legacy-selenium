package pages;

import builders.PageBuilder;
import base.AbstractTest;
import entities.Page;
import entities.User;
import entities.enums.ContentItemsPages;
import entities.enums.PageTypes;
import helpers.Environment;
import helpers.NavigationHelper;
import helpers.StringManager;
import helpers.Verifications;
import org.junit.jupiter.api.Test;
import pages.dialogs.SuccessDialogPage;
import pages.page.SelectTypePage;
import pages.page.config.BasicTabDetailsPage;
import pages.page.editor.EditorsViewPage;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;
import scenarios.SearchScenarios;

import java.util.ArrayList;

class CreateNewPageUiTest extends AbstractTest {
    private static final PageTypes TYPE_PRODUCT = PageTypes.PRODUCT;
    private static final PageTypes TYPE_FLEX = PageTypes.FLEX;
    private static final PageTypes TYPE_SITE = PageTypes.SITE;
    
    private static final int EXPECTED_NUMBER_OF_TABS = 2;
    
    private SitesPage sitesPage;
    private BasicTabDetailsPage detailsPage;
    
    @Test
    void verifyProductPageCreation() {
        String pageTitle = StringManager.getRandomAlphanumeric();
        String navigationTitle = StringManager.getRandomAlphanumeric();
        
        openApplicationAndNavigateToAutomationTests();
        startPageCreation(TYPE_PRODUCT);
        verifyButtonCreateAvailability(pageTitle);
        fillDataAndCreatePage(pageTitle, navigationTitle);
        clickOpenAndVerifyThatPageIsOpenedInNewTab(pageTitle);
        verifyThatPageExistsInSitesSection(pageTitle);
        
        deletePageAndVerifyPageWasDeleted(
                PageBuilder
                        .newPage()
                        .withTitle(pageTitle)
                        .withName(pageTitle.toLowerCase())
                        .build());
    }
    
    @Test
    void verifyFlexPageCreation() {
        String pageTitle = StringManager.getRandomAlphanumeric();
        String navigationTitle = StringManager.getRandomAlphanumeric();
        
        verifyPageCreation(TYPE_FLEX, pageTitle, navigationTitle);
        
        deletePageAndVerifyPageWasDeleted(
                PageBuilder
                        .newPage()
                        .withTitle(pageTitle)
                        .withName(pageTitle.toLowerCase())
                        .build());
    }
    
    @Test
    void verifySitePageCreation() {
        String pageTitle = StringManager.getRandomAlphanumeric();
        String navigationTitle = StringManager.getRandomAlphanumeric();
        
        verifyPageCreation(TYPE_SITE, pageTitle, navigationTitle);
        
        deletePageAndVerifyPageWasDeleted(
                PageBuilder
                        .newPage()
                        .withTitle(pageTitle)
                        .withName(pageTitle.toLowerCase())
                        .build());
    }
    
    private void verifyPageCreation(PageTypes type, String pageTitle, String navigationTitle) {
        openApplicationAndNavigateToAutomationTests();
        startPageCreation(type);
        fillDataAndCreatePage(pageTitle, navigationTitle);
        clickDoneAndVerifyThatPageIsNotOpened();
        
        NavigationHelper.navigateToAutomationTestsContentFolder(sitesPage);
        SearchScenarios.performSearchAndVerifyResult(pageTitle, true);
    }
    
    private void openApplicationAndNavigateToAutomationTests() {
        NavigationHelper.navigateToDefaultUrl();
        LoginScenarios.loginWithCredentials(User.ADMINISTRATOR);
        
        sitesPage = NavigationHelper.navigateToSitesSection();
        NavigationHelper.navigateToTests(sitesPage);
        sitesPage.selectContentItem(ContentItemsPages.AUTOMATION_TESTS);
    }
    
    private void startPageCreation(PageTypes pageType) {
        sitesPage.clickCreateActionButton();
        sitesPage.selectTypePage();
        SelectTypePage createPage = new SelectTypePage();
        createPage.selectPageType(pageType);
        detailsPage = new BasicTabDetailsPage();
    }
    
    private void verifyButtonCreateAvailability(String pageTitle) {
        detailsPage.getNextCreateButton().assertDisabled();
        
        detailsPage.fillTitle(pageTitle);
        Verifications.assertTrue(detailsPage.getNameUrlElement().getValue().equals(pageTitle.toLowerCase()),
                "Name URL should be equal to title in lower case");
        detailsPage.getNextCreateButton().assertEnabled();
    }
    
    private void fillDataAndCreatePage(String pageTitle, String navigationTitle) {
        detailsPage.fillTitle(pageTitle);
        detailsPage.fillNavigationTitle(navigationTitle);
        detailsPage.clickNextButton();
    }
    
    private void clickDoneAndVerifyThatPageIsNotOpened() {
        SuccessDialogPage successDialog = new SuccessDialogPage();
        successDialog.clickDoneButton();
        sitesPage.assertPageIsOpened();
    }
    
    private void clickOpenAndVerifyThatPageIsOpenedInNewTab(String pageTitle) {
        SuccessDialogPage successDialog = new SuccessDialogPage();
        successDialog.clickOpenButton();
        
        ArrayList<String> tabs = Environment.getCurrentTabs();
        Verifications.assertTrue(tabs.size() == EXPECTED_NUMBER_OF_TABS, "Expected number of tabs: " +
                EXPECTED_NUMBER_OF_TABS + ", but was: " + tabs.size());
        Environment.switchToTab(tabs.get(1));
        new EditorsViewPage(pageTitle);
        
        Environment.closeCurrentTab();
        Environment.switchToTab(tabs.get(0));
    }
    
    private void verifyThatPageExistsInSitesSection(String pageTitle) {
        sitesPage.assertPageIsOpened();
        NavigationHelper.navigateToAutomationTestsContentFolder(sitesPage);
        sitesPage.selectContentItem(pageTitle);
    }
    
    private void deletePageAndVerifyPageWasDeleted(Page page) {
        PageScenarios.deletePage(page);
        sitesPage = NavigationHelper.navigateToAutomationTestsContentFolder();
        sitesPage.verifyContentItemDoesNotExist(page.getTitle());
    }
    
}
