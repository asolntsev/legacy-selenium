package pages.page.editor;

import elements.ButtonElement;
import elements.LabelElement;
import entities.Components;
import org.openqa.selenium.By;

public class EditorsViewPage extends AuthorViewNavigationPage {
    private static final By DRAG_COMPONENTS_HERE_LOCATOR = By.xpath("//div[contains(@data-path," +
            "'content/responsivegrid/*')]");
    private static final String EDITORS_PAGE_LOCATOR_TEMPLATE = "//div[@class='editor-GlobalBar-pageTitle' and " +
            "contains(text(),'%1$s')]";
    private static final String ACTION_LOCATOR_TEMPLATE = "//button[@data-action='%1$s']";
    private static final String COMPONENT_CONTAINER_EDIT_VIEW_LOCATOR_TEMPLATE = "//div[contains(@data-path,'%1$s')]";
    private static final By TOGGLE_SIDE_PANEL_BUTTON_LOCATOR = By.xpath("//button[./huge-icon[@aria-label='rail left']]");
    
    private String pageTitle;
    
    private enum Actions {
        INSERT, DELETE, CONFIGURE
    }
    
    protected EditorsViewPage switchToEditMode(String pageName) {
        selectEditMode();
        return this;
    }
    
    public EditorsViewPage switchToEditMode() {
        return switchToEditMode(pageTitle);
    }
    
    public String getPageTitle() {
        return pageTitle;
    }
    
    public EditorsViewPage(String pageTitle) {
        super(pageTitle, By.xpath(String.format(EDITORS_PAGE_LOCATOR_TEMPLATE, pageTitle)));
        this.pageTitle = pageTitle;
    }
    
    public void clickOnDragComponentsHere() {
        ButtonElement buttonAddComponent = new ButtonElement("Drag components here", DRAG_COMPONENTS_HERE_LOCATOR);
        buttonAddComponent.waitForElementToBeVisible();
        buttonAddComponent.clickViaJS();
    }
    
    public void clickOnInsertButton() {
        new ButtonElement("Insert button", getActionButtonLocator(Actions.INSERT)).click();
    }
    
    public void clickOnConfigureButton() {
        new ButtonElement("Configure button", getActionButtonLocator(Actions.CONFIGURE)).clickViaJS();
    }
    
    public void clickOnDeleteButton() {
        new ButtonElement("Delete button", getActionButtonLocator(Actions.DELETE)).clickViaJS();
    }
    
    private By getActionButtonLocator(Actions action) {
        return By.xpath(String.format(ACTION_LOCATOR_TEMPLATE,
                action.toString()));
    }
    
    public LabelElement getComponentContainerInEditView(Components component) {
        return new LabelElement("Component [" + component + "]",
                By.xpath(String.format(COMPONENT_CONTAINER_EDIT_VIEW_LOCATOR_TEMPLATE, component.getDataPath())));
    }
    
    public SidePanelPage clickOnToggleSidePanelButton() {
        new ButtonElement("Toggle side panel button", TOGGLE_SIDE_PANEL_BUTTON_LOCATOR).click();
        return new SidePanelPage();
    }
    
}
