package scenarios;

import base.BaseComponent;
import entities.Page;
import entities.enums.ContentItemsPages;
import helpers.Environment;
import helpers.NavigationHelper;
import helpers.Pauses;
import helpers.StringManager;
import helpers.Timeouts;
import pages.SitesPage;
import pages.dialogs.DeleteDialogPage;
import pages.page.editor.PublishedViewPage;
import pages.page.editor.EditorsViewPage;
import pages.publish.ManagePublicationPage;

public class PageScenarios extends BaseComponent {
    private static final String PUBLISH_URL_POSTFIX = ".html";
    
    public static void publishPageViaPublishManager(String pageTitle) {
        ManagePublicationPage managePublicationPage = navigateToManagePublication(pageTitle);
        managePublicationPage.selectPublishAction();
        managePublicationPage.clickNextButton();
        Pauses.sleep(Timeouts.SLOW);
        
        managePublicationPage.clickNextButton();
        Pauses.sleep(Timeouts.FAST);
        managePublicationPage.verifyPagePublishedAlert();
        Environment.waitForJs();
    }
    
    public static void unpublishPageViaPublishManager(String pageTitle) {
        ManagePublicationPage managePublicationPage = navigateToManagePublication(pageTitle);
        managePublicationPage.selectUnpublishAction();
        managePublicationPage.clickNextButton();
        
        Environment.waitForJs();
        managePublicationPage.clickNextButton();
    }
    
    private static ManagePublicationPage navigateToManagePublication(String pageTitle) {
        SitesPage sitesPage = NavigationHelper.navigateToAutomationTestsContentFolder();
        sitesPage.selectContentItem(pageTitle);
        sitesPage.clickManagePublicationButton();
        
        return new ManagePublicationPage();
    }
    
    public static PublishedViewPage openPageOnPublishDispatcher(Page page) {
        String publishedPageUrl = Environment.getPublishUrl() + NavigationHelper.getPathToContentItem(ContentItemsPages.AUTOMATION_TESTS) +
                StringManager.URL_SEPARATOR + page.getName() + PUBLISH_URL_POSTFIX;
        Environment.navigateUrl(publishedPageUrl);
        return new PublishedViewPage();
    }
    
    public static void deletePage(Page page) {
        SitesPage sitesPage = NavigationHelper.navigateToAutomationTestsContentFolder();
        sitesPage.selectContentItem(page.getTitle());
        sitesPage.clickDeleteActionButton();
        
        new DeleteDialogPage().clickDeleteButton();
        Environment.waitForJs();
    }
    
    public static EditorsViewPage openPageInAuthorMode(Page page) {
        Environment.navigateUrl(Environment.getEditorsPageUrl(page.getName()));
        return new EditorsViewPage(page.getTitle());
    }
    
}
