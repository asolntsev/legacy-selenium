package helpers;

import entities.enums.ContentItems;
import entities.enums.ContentItemsPages;
import entities.enums.NavigationItems;
import pages.NavigationPage;
import pages.SitesPage;
import pages.assetsmanagement.AssetsPage;
import pages.page.editor.EditorsViewPage;

public class NavigationHelper {
    private static final String DEFAULT_SITES_URL_PREFIX = "/sites.html";
    private static final String TEST_AUTOMATION_FOLDER = "TEST AUTOMATION";
    private static final String ASSETS_FOLDER = "Assets";

    /**
     * Open default url (configured in e2e.properties)
     */
    public static void navigateToDefaultUrl() {
        Environment.navigateUrl(Environment.URL);
    }
    
    public static void navigateToTests(SitesPage sitesPage) {
        Environment.navigateUrl(getFullUrlForContentItem(ContentItemsPages.TESTS, sitesPage));
    }
    
    public static void navigateToAutomationTestsContentFolder(SitesPage sitesPage) {
        Environment.navigateUrl(getFullUrlForContentItem(ContentItemsPages.AUTOMATION_TESTS, sitesPage));
    }
    
    public static SitesPage navigateToAutomationTestsContentFolder() {
        navigateToDefaultUrl();
        SitesPage sitesPage = navigateToSitesSection();
        
        navigateToAutomationTestsContentFolder(sitesPage);
        return sitesPage;
    }
    
    private static String getFullUrlForContentItem(ContentItemsPages itemToNavigate, SitesPage sitesPage) {
        sitesPage.assertPageIsOpened();
        return Environment.URL + DEFAULT_SITES_URL_PREFIX + getPathToContentItem(itemToNavigate);
    }
    
    public static String getPathToContentItem(ContentItems itemToNavigate) {
        StringBuilder pathValue = new StringBuilder("/content");
        for (Enum item : itemToNavigate.getListOfItems()) {
            pathValue.append(StringManager.URL_SEPARATOR).append(item.toString());
            if (itemToNavigate.equals(item)) break;
        }
        return pathValue.toString();
    }
    
    public static SitesPage navigateToSitesSection() {
        NavigationPage navigationPage = new NavigationPage();
        navigationPage.selectMenuItem(NavigationItems.SITES);
        return new SitesPage();
    }
    
    public static EditorsViewPage switchToEditorsPage(String pageTitle) {
        Environment.switchToLastTab();
        return new EditorsViewPage(pageTitle);
    }
    
    public static AssetsPage navigateToAssets() {
        navigateToDefaultUrl();
        NavigationPage navigationPage = new NavigationPage();
        navigationPage.selectMenuItem(NavigationItems.ASSETS);
        
        Environment.waitForJs();
        navigationPage.selectMenuItem(NavigationItems.FILES);
        return new AssetsPage(ASSETS_FOLDER);
    }
    
    public static AssetsPage navigateToAutomationTestsAssetsFolder() {
        navigateToAssets().selectFolderByTitle(TEST_AUTOMATION_FOLDER);
        return new AssetsPage(ASSETS_FOLDER);
    }
}
