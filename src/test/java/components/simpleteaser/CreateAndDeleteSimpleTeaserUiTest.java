package components.simpleteaser;

import components.CreateAndDeleteComponentAbstractTest;
import entities.Components;
import helpers.StringManager;
import org.junit.jupiter.api.Test;
import pages.components.simpleteaser.SimpleTeaserPage;

class CreateAndDeleteSimpleTeaserUiTest extends CreateAndDeleteComponentAbstractTest {
    private static final Components SIMPLE_TEASER = Components.SIMPLE_TEASER;
    
    private SimpleTeaserPage simpleTeaserPage;
    
    @Test
    void verifySimpleTeaserCreationAndDeletion() {
        verifyCreationAndDeletionOfComponent(SIMPLE_TEASER);
    }
    
    @Override
    protected void checkMandatoryData() {
        simpleTeaserPage = new SimpleTeaserPage();
        simpleTeaserPage.clickDone();
        simpleTeaserPage.verifyFieldErrorExists();
    }
    
    @Override
    protected void fillDataAndSaveChanges() {
        simpleTeaserPage.navigateHeadingTab().fillHeading(StringManager.getRandomAlphanumeric());
        simpleTeaserPage.clickDone();
    }
    
}
