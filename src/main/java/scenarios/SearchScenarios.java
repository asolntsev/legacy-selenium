package scenarios;

import base.BaseComponent;
import helpers.Timeouts;
import helpers.Verifications;
import pages.MainPage;
import pages.SearchResultsPage;
import pages.dialogs.SearchDialogPage;

public class SearchScenarios extends BaseComponent {
    
    public static void performSearchAndVerifyResult(String value, boolean exists) {
        MainPage mainPage = new MainPage();
        mainPage.clickOnSearchButton();
        SearchDialogPage searchDialog = new SearchDialogPage();
        searchDialog.setSearchText(value);
        searchDialog.getSearchTextElement().hitEnter();
        
        verifySearchResultFound(value, exists);
    }
    
    private static void verifySearchResultFound(String value, boolean exists) {
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        
        Verifications.assertTrue(exists == searchResultsPage.getSearchResultByText(value)
                        .exists(Timeouts.SLOW),
                "Expecting element existence: " + exists + ", but was: " + !exists);
    }
}
