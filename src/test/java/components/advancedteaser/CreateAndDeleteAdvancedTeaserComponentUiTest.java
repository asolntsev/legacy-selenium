package components.advancedteaser;

import components.CreateAndDeleteComponentAbstractTest;
import entities.Components;
import helpers.StringManager;
import org.junit.jupiter.api.Test;
import pages.components.advancedteaser.AdvancedTeaserPage;

class CreateAndDeleteAdvancedTeaserComponentUiTest extends CreateAndDeleteComponentAbstractTest {
    private Components advancedTeaserComponent = Components.ADVANCED_TEASER;
    private static AdvancedTeaserPage advancedTeaserPage;
    
    @Test
    void verifyCreationAndDeletionOfAdvancedTeaser() {
        verifyCreationAndDeletionOfComponent(advancedTeaserComponent);
    }
    
    @Override
    protected void checkMandatoryData() {
        advancedTeaserPage = new AdvancedTeaserPage();
        advancedTeaserPage.clickDone();
        advancedTeaserPage.verifyFieldErrorExists();
    }
    
    @Override
    protected void fillDataAndSaveChanges() {
        advancedTeaserPage.navigateGeneralTab().setHeading(StringManager.getRandomAlphanumeric());
        advancedTeaserPage.clickDone();
    }
    
}
