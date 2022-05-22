package cookies;

import base.AbstractTest;
import entities.Page;
import entities.User;
import entities.enums.PageTemplates;
import helpers.ApiHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.page.editor.PublishedViewPage;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;
import utils.WebDriverFactory;

class AcceptCookiesUiTest extends AbstractTest {
    
    private Page pageFirst;
    private Page pageSecond;
    
    @BeforeEach
    void precondition() {
        pageFirst = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        pageSecond = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
        
        PageScenarios.publishPageViaPublishManager(pageFirst.getTitle());
        PageScenarios.publishPageViaPublishManager(pageSecond.getTitle());
    }
    
    @Test
    void verifyCookiesBanner() {
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(pageFirst);
        publishedViewPage.acceptCookiePolicy();
        
        WebDriverFactory.instance().refreshPage();
        publishedViewPage.verifyCookieBannerDoesNotExist();
        
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(pageSecond);
        publishedViewPage.verifyCookieBannerDoesNotExist();
    }
    
    @AfterEach
    void afterTest() {
        ApiHelper.deletePage(pageFirst.getName());
        ApiHelper.deletePage(pageSecond.getName());
    }
    
}
