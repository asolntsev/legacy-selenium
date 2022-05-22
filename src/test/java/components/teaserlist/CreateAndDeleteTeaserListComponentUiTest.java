package components.teaserlist;

import components.CreateAndDeleteComponentAbstractTest;
import entities.Components;
import org.junit.jupiter.api.Test;
import pages.components.teaserlist.TeaserListPage;

class CreateAndDeleteTeaserListComponentUiTest extends CreateAndDeleteComponentAbstractTest {
    private static final Components TEASER_LIST = Components.TEASER_LIST;
    
    @Test
    void verifyTeaserListCreationAndDeletion() {
        verifyCreationAndDeletionOfComponent(TEASER_LIST);
    }
    
    @Override
    protected void checkMandatoryData() {
        // Currently no mandatory data is set for Teaser list component
    }
    
    @Override
    protected void fillDataAndSaveChanges() {
        new TeaserListPage().clickDone();
    }
}
