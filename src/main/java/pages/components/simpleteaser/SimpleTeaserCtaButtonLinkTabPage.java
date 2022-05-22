package pages.components.simpleteaser;

import base.BaseElement;
import elements.ButtonElement;
import elements.DropdownElement;
import elements.TextElement;
import entities.enums.GeneralLinksActionTypes;
import entities.enums.GeneralLinksTargets;
import entities.enums.GeneralLinksViewTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SimpleTeaserCtaButtonLinkTabPage extends SimpleTeaserPage {
    private static final String LINK_CONTAINER_LOCATOR_TEMPLATE = "//huge-multi-field-item[%s]";
    private static final By ADD_LINK_LOCATOR = By.xpath("//button[@huge-multi-field-add]");
    private static final By LINK_ACTION_TYPE_LOCATOR = By.name("./linkAction");
    private static final By LINK_TEXT_LOCATOR = By.name("./linkText");
    private static final By LINK_URL_LOCATOR = By.name("./linkPath");
    private static final By LINK_TARGET_LOCATOR = By.xpath(".//huge-select[@name='./linkTarget']");
    private static final By LINK_VIEW_TYPE_LOCATOR = By.xpath(".//huge-select[@name='./linkType']");
    private static final By DELETE_BUTTON_LOCATOR = By.xpath("//huge-multi-field-item//huge-icon[@aria-label='delete']");
    
    public SimpleTeaserCtaButtonLinkTabPage() {
        super("Simple teaser cta button link tab page", ADD_LINK_LOCATOR);
    }
    
    public void clickAddButton() {
        ButtonElement addLink = new ButtonElement("Add link button", ADD_LINK_LOCATOR);
        addLink.scrollToElement();
        addLink.click();
    }
    
    private BaseElement getLinkContainerByNumber(int numberOfLink) {
        return new BaseElement("Link container", By.xpath(String.format(LINK_CONTAINER_LOCATOR_TEMPLATE, numberOfLink)));
    }
    
    public void selectLinkType(int numberOfLink, GeneralLinksActionTypes linksType) {
        new DropdownElement("Link type", getLinkContainerByNumber(numberOfLink), LINK_ACTION_TYPE_LOCATOR).selectOptionFromDropdownByValue(linksType.getValue());
    }
    
    public void setLinkText(int numberOfLink, String linkText) {
        new TextElement("Link text", getLinkContainerByNumber(numberOfLink), LINK_TEXT_LOCATOR).setValue(linkText);
    }
    
    public void setLinkUrl(int numberOfLink, String linkURL) {
        new TextElement("Link URL", getLinkContainerByNumber(numberOfLink), LINK_URL_LOCATOR).setValue(linkURL);
    }
    
    public void selectLinkTarget(int numberOfLink, GeneralLinksTargets linkTarget) {
        DropdownElement linkTargetDropdown = new DropdownElement("Link target", getLinkContainerByNumber(numberOfLink), LINK_TARGET_LOCATOR);
        linkTargetDropdown.scrollToElement();
        linkTargetDropdown.selectOptionFromHugeDropdownByValue(linkTarget.getValue());
    }
    
    public void setLinkViewType(int numberOfLink, GeneralLinksViewTypes linkViewType) {
        DropdownElement linkType = new DropdownElement("Link target", getLinkContainerByNumber(numberOfLink), LINK_VIEW_TYPE_LOCATOR);
        linkType.scrollToElement();
        linkType.selectOptionFromHugeDropdownByValue(linkViewType.toString().toLowerCase());
    }
    
    public void deleteAllCtaLinks() {
        ButtonElement deleteButton = new ButtonElement("Delete CTA link button", DELETE_BUTTON_LOCATOR);
        List<WebElement> allDeleteButtons = deleteButton.elements();
        for (int i = 1; i <= allDeleteButtons.size(); i++) {
            deleteButton.clickViaJS();
        }
    }
}
