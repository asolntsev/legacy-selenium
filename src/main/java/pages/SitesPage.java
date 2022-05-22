package pages;

import elements.ButtonElement;
import entities.enums.ContentItemsPages;
import helpers.Environment;
import helpers.Verifications;
import org.openqa.selenium.By;

public class SitesPage extends MainPage {
    private static final By SITES_PAGE_TITLE_LOCATOR = By.id("shell-actionbar");
    private static final String SITES_PAGE_LOCATOR_TEMPLATE = "//huge-column-view-item-thumbnail[..%1$s]";
    private static final String SITES_LINK_LOCATOR_TEMPLATE = "//huge-column-view-item-content/div[@title='%1$s']";
    
    private static final By ACTION_BUTTON_CREATE_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='add' and not(@hidden)]");
    private static final By ACTION_BUTTON_PROPERTIES_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='infoCircle' and not(@hidden)]");
    private static final By ACTION_BUTTON_EDIT_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='edit' and not(@hidden)]");
    private static final By ACTION_BUTTON_LOCK_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='lockOn' and not(@hidden)]");
    private static final By ACTION_BUTTON_COPY_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='copy' and not(@hidden)]");
    private static final By ACTION_BUTTON_MOVE_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='move' and not(@hidden)]");
    private static final By ACTION_BUTTON_QUICK_PUBLISH_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='globe' and not(@hidden)]");
    private static final By ACTION_BUTTON_MANAGE_PUBLICATION_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='globeClock' and not(@hidden)]");
    private static final By ACTION_BUTTON_DELETE_LOCATOR = By.xpath
            ("//huge-actionbar-item[@class='huge-ActionBar-item']/button[@icon='delete' and not(@hidden)]");
    
    private static final By CREATE_OPTION_PAGE = By.xpath("//huge-popover[contains(@class, 'is-open')" +
            "]//a[@icon='experience']");
    
    public SitesPage() {
        super("Sites page", SITES_PAGE_TITLE_LOCATOR);
    }
    
    public void selectContentItem(ContentItemsPages contentItem) {
        selectContentItem(contentItem.getTitle());
    }
    
    public void selectContentItem(String contentItem) {
        Environment.waitForJs();
        
        ButtonElement button = getContentItemElement(contentItem);
        button.scrollToElement();
        button.moveMouseToElement();
        button.clickViaJS();
    }
    
    public ButtonElement getContentItemElement(String contentItem) {
        String siteLinkLocator = String.format(SITES_LINK_LOCATOR_TEMPLATE, contentItem);
        return new ButtonElement(contentItem, By.xpath(String.format(SITES_PAGE_LOCATOR_TEMPLATE, siteLinkLocator)));
    }
    
    public void verifyContentItemDoesNotExist(String contentItem) {
        Environment.waitForJs();
        Verifications.assertFalse(getContentItemElement(contentItem).exists(),
                "Content item [" + contentItem + "] should not exist, but was found");
        
    }
    
    public void openPageContent(ContentItemsPages contentItem) {
        ButtonElement button = new ButtonElement(contentItem.toString(), By.xpath(String.format
                (SITES_LINK_LOCATOR_TEMPLATE, contentItem.toString())));
        button.clickViaJS();
    }
    
    public void clickCreateActionButton() {
        ButtonElement createButton = new ButtonElement("Create", ACTION_BUTTON_CREATE_LOCATOR);
        createButton.clickViaJS();
    }
    
    public void clickEditActionButton() {
        ButtonElement editButton = new ButtonElement("Edit button", ACTION_BUTTON_EDIT_LOCATOR);
        editButton.clickViaJS();
    }
    
    public void clickDeleteActionButton() {
        ButtonElement deleteButton = new ButtonElement("Delete button", ACTION_BUTTON_DELETE_LOCATOR);
        deleteButton.clickViaJS();
    }
    
    public void clickManagePublicationButton() {
        ButtonElement managePublicationButton = new ButtonElement("Manage publication", ACTION_BUTTON_MANAGE_PUBLICATION_LOCATOR);
        managePublicationButton.clickViaJS();
    }
    
    public void selectTypePage() {
        ButtonElement pageButton = new ButtonElement("Page", CREATE_OPTION_PAGE);
        pageButton.clickViaJS();
    }
}
