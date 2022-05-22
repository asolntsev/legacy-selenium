package pages.publish;

import elements.ButtonElement;
import org.openqa.selenium.By;
import pages.dialogs.AlertPage;
import pages.page.DetailsPage;

public class ManagePublicationPage extends DetailsPage {
    
    private static final By MANAGE_PUBLICATION_PAGE_LOCATOR = By.xpath("//div[contains(@class,'cq-sites-managepublication')]");
    private static final By ACTIVATE_BUTTON_LOCATOR = By.xpath("//button[@value='Activate']");
    private static final By DEACTIVATE_BUTTON_LOCATOR = By.xpath("//button[@value='Deactivate']");
    private static final String ALERT_MESSAGE = "Resource(s) have been published.";
    
    public ManagePublicationPage() {
        super("Manage publication", MANAGE_PUBLICATION_PAGE_LOCATOR);
    }
    
    public void selectPublishAction() {
        new ButtonElement("Publish button", ACTIVATE_BUTTON_LOCATOR).clickViaJS();
    }
    
    public void selectUnpublishAction() {
        new ButtonElement("Unpublish button", DEACTIVATE_BUTTON_LOCATOR).clickViaJS();
    }
    
    public void verifyPagePublishedAlert() {
        AlertPage alertPage = new AlertPage();
        alertPage.verifyAlertInfo(ALERT_MESSAGE);
    }
}
