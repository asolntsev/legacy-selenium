package components.advancedteaser;

import builders.AdvancedTeaserBuilder;
import base.AbstractTest;
import entities.*;
import entities.components.AdvancedTeaserEntity;
import entities.enums.AssetTypes;
import entities.enums.PageTemplates;
import helpers.ApiHelper;
import helpers.StringManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import pages.components.advancedteaser.AdvancedTeaserPage;
import pages.page.editor.EditorsViewPage;
import pages.page.editor.SidePanelPage;
import scenarios.LoginScenarios;
import scenarios.PageScenarios;
import scenarios.SidePanelScenarios;

public abstract class AddMediaToAdvancedTeaserAbstractTest extends AbstractTest {
    protected Page page;
    protected String fullFileName;
    protected SidePanelPage sidePanel;
    protected Components advancedTeaser;
    protected EditorsViewPage editorsPage;
    protected AdvancedTeaserPage advancedTeaserPage;
    
    protected abstract Assets getFile();
    
    @BeforeEach
    void precondition() {
        AdvancedTeaserEntity advancedTeaserEntity = AdvancedTeaserBuilder
                .createInstance()
                .withHeading(StringManager.getRandomAlphanumeric())
                .build();
        
        page = ApiHelper.createNewPageWithRandomTitle(PageTemplates.FLEX);
        advancedTeaser = ApiHelper.addComponentsToPage(page.getName(), Components.ADVANCED_TEASER);
        ApiHelper.updateAdvancedTeaser(advancedTeaserEntity, advancedTeaser, page);
        
        fullFileName = ApiHelper.uploadFile(getFile());
        
        LoginScenarios.openAppAndLoginWithCredentials(User.ADMINISTRATOR);
    }
    
    @AfterEach
    void deleteUsedData() {
        ApiHelper.deletePage(page.getName());
        ApiHelper.deleteFile(fullFileName);
    }
    
    protected void openEditorsPageAndFindTheImage() {
        EditorsViewPage editorsViewPage = PageScenarios.openPageInAuthorMode(page);
        sidePanel = SidePanelScenarios.openPanelAndPerformSearchForFileOnAuthorPart(editorsViewPage, fullFileName,
                AssetTypes.IMAGES);
    }
}
