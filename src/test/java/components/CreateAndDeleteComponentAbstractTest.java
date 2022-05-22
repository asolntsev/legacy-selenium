package components;

import base.AbstractTest;
import entities.Page;
import entities.User;
import entities.Components;
import entities.enums.PageTemplates;
import helpers.ApiHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.page.editor.PublishedViewPage;
import pages.page.editor.EditorsViewPage;
import scenarios.ComponentScenarios;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;

public abstract class CreateAndDeleteComponentAbstractTest extends AbstractTest {
    private static Page page;
    
    protected abstract void checkMandatoryData();
    
    protected abstract void fillDataAndSaveChanges();
    
    @BeforeEach
    void precondition() {
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
    }
    
    @AfterEach
    void deleteCreatedPage() {
        ApiHelper.deletePage(page.getName());
    }
    
    protected void verifyCreationAndDeletionOfComponent(Components component) {
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
        EditorsViewPage editorsPage = PageScenarios.openPageInAuthorMode(page);
        
        ComponentScenarios.addNewComponent(editorsPage, component);
        ComponentScenarios.assertComponentExistsInEditMode(editorsPage, component);
        ComponentScenarios.assertComponentExistsInPreviewMode(editorsPage, component);
        ComponentScenarios.openComponentInEditMode(editorsPage, component);
        
        checkMandatoryData();
        fillDataAndSaveChanges();
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        PublishedViewPage publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        ComponentScenarios.assertComponentExistsOnPublishEnvironment(publishedViewPage, component);
        
        editorsPage = PageScenarios.openPageInAuthorMode(page);
        ComponentScenarios.deleteComponent(editorsPage, component);
        ComponentScenarios.assertComponentDoesNotExistInEditMode(editorsPage, component);
        
        PageScenarios.publishPageViaPublishManager(page.getTitle());
        publishedViewPage = PageScenarios.openPageOnPublishDispatcher(page);
        ComponentScenarios.assertComponentDoesNotExistOnPublishEnvironment(publishedViewPage, component);
    }
    
}
