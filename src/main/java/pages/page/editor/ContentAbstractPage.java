package pages.page.editor;

import base.BasePage;
import elements.LabelElement;
import elements.LinkElement;
import entities.Components;
import entities.enums.ContentItemsAssets;
import entities.enums.ParagraphFormats;
import helpers.*;
import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import scenarios.ComponentScenarios;
import utils.WebDriverFactory;

import java.util.ArrayList;

public class ContentAbstractPage extends BasePage {
    private static final By CONTENT_PAGE_LOCATOR = By.id("content");
    
    private static final String COMPONENT_CONTAINER_PREVIEW_MODE_LOCATOR_TEMPLATE = "//div[contains(@class,'%1$s aem')]";
    private static final String HEADER_LOCATOR_TEMPLATE = "//%1$s[contains(@class,'general-header') and contains(.,'%2$s')]";
    private static final String IMAGE_LOCATOR_TEMPLATE = "//img[@src='%1$s']";
    private static final String DISCLAIMER_TEXT_LOCATOR_TEMPLATE = "//div[contains(@class,'disclaimer')]//p[contains(text(),'%1$s')]";
    private static final String VIDEO_IFRAME_LOCATOR_TEMPLATE = "//iframe[contains(@src, '%1$s')]";
    private static final String GENERAL_LINK_LOCATOR_TEMPLATE = "//a[contains(@href,'%1$s') and contains(., '%2$s')]";
    
    private static final String ADVANCED_TEASER_MAIN_TEXT_LOCATOR_TEMPLATE = "//div[contains(@class,'main-text')]//%1$s[contains(text(),'%2$s')]";
    private static final String ADVANCED_TEASER_VIDEO_URL_LOCATOR_TEMPLATE = "//div[@class='video']//div[@data-video-id='%s']";
    private static final String ADVANCED_TEASER_VIDEO_INFO_LOCATOR_TEMPLATE = "//div[contains(@class,'video-info')]/%s";
    private static final By ADVANCED_TEASER_VIDEO_TITLE_LOCATOR = By.xpath("//div[@class='video']//div[@role='button']");
    
    private static final String SIMPLE_TEASER_MAIN_TEXT_LOCATOR_TEMPLATE = "//div[@class='content']//%1$s[contains(text(),'%2$s')]";
    
    private static final String ARTICLE_HEADING_LOCATOR_TEMPLATE = "//%1$s[contains(@class,'main-heading') and contains(text(),'%2$s')]";
    private static final String ARTICLE_LEAD_TEXT_LOCATOR_TEMPLATE = "//div[contains(@class,'lead')]//%1$s[contains(text(),'%2$s')]";
    private static final String ARTICLE_MAIN_TEXT_LOCATOR_TEMPLATE = "//div[contains(@class,'main-content')]//%1$s[contains(text(),'%2$s')]";
    
    ContentAbstractPage(String name) {
        super(name, CONTENT_PAGE_LOCATOR);
    }
    
    public LabelElement getComponentContainer(Components component) {
        return new LabelElement("Component [" + component + "]",
                By.xpath(String.format(COMPONENT_CONTAINER_PREVIEW_MODE_LOCATOR_TEMPLATE, component.getValue())));
    }
    
    private LabelElement getLabelElement(Components component, String name, String template, Object... values) {
        LabelElement element = new LabelElement(name, getComponentContainer(component), By.xpath(String.format(template, values)));
        if (!PreviewModePage.iFrameActive) {
            ComponentScenarios.waitForPublishedPageToBeUpdated(this, element, true);
        }
        
        return element;
    }
    
    // Common
    
    public void verifyImageExistence(String fullFileName, boolean exists) {
        String imagePath = NavigationHelper.getPathToContentItem(ContentItemsAssets.TEST_AUTOMATION) + StringManager.URL_SEPARATOR
                + fullFileName;
        
        LabelElement imageLabel = new LabelElement("Image label", By.xpath(String.format(IMAGE_LOCATOR_TEMPLATE, imagePath)));
        Verifications.assertTrue(exists ? imageLabel.exists(Timeouts.FAST) : imageLabel.absent(Timeouts.FAST),
                "Image existence should be: " + exists + ", but was: " + !exists);
    }
    
    public void verifyVideoIFrameExistence(String youtubeLinkId) {
        String videoUrl = DataConstants.YOUTUBE_LINK_TRANSFORMED + youtubeLinkId;
        LabelElement videoIframe = new LabelElement("Video iframe element", By.xpath(String.format(VIDEO_IFRAME_LOCATOR_TEMPLATE, videoUrl)));
        Verifications.assertTrue(videoIframe.exists(Timeouts.FAST),
                "Video URL should exist, but was not found");
    }
    
    // ----------------------------------------
    
    // Advanced teaser
    
    public void verifyAdvancedTeaserHeaderInfo(ParagraphFormats headerSize, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ADVANCED_TEASER, "Advanced teaser header",
                HEADER_LOCATOR_TEMPLATE, headerSize.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Advanced teaser header info is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifyAdvancedTeaserHeaderInfo(String value) {
        verifyAdvancedTeaserHeaderInfo(ParagraphFormats.HEADER_1, value);
    }
    
    public void verifyAdvancedTeaserMainTextInfo(ParagraphFormats format, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ADVANCED_TEASER, "Advanced teaser main text",
                ADVANCED_TEASER_MAIN_TEXT_LOCATOR_TEMPLATE, format.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Advanced teaser main text is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifyAdvancedTeaserMainTextInfo(String value) {
        verifyAdvancedTeaserMainTextInfo(ParagraphFormats.PARAGRAPH, value);
    }
    
    public void verifyAdvancedTeaserDisclaimerText(String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ADVANCED_TEASER, "Advanced teaser disclaimer text",
                DISCLAIMER_TEXT_LOCATOR_TEMPLATE, value);
        
        Verifications.assertTrue(element.exists(),
                "Advanced teaser disclaimer text is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifyAdvancedTeaserVideoUrlExists(String youtubeUrlID) {
        LabelElement videoUrlLabel = new LabelElement("Video URL", By.xpath(String.format(ADVANCED_TEASER_VIDEO_URL_LOCATOR_TEMPLATE, youtubeUrlID)));
        
        Verifications.assertTrue(videoUrlLabel.exists(Timeouts.FAST), "Video url should exists, but wasn't found");
    }
    
    public void verifyAdvancedTeaserVideoTitle(String videoTitle) {
        LabelElement videoTitleLabel = new LabelElement("Video URL", ADVANCED_TEASER_VIDEO_TITLE_LOCATOR);
        
        String actual = videoTitleLabel.element().getAttribute("data-video-title");
        Verifications.assertContains(actual, videoTitle, String.format("Video title should be '%1$s', but was '%2$s'", videoTitle, actual));
    }
    
    public void verifyAdvancedTeaserVideoInfo(String videoInfoTitle, String videoInfo) {
        LabelElement videoInfoTitleLabel = new LabelElement("Video info title label", By.xpath(String.format(ADVANCED_TEASER_VIDEO_INFO_LOCATOR_TEMPLATE, ParagraphFormats.HEADER_4.getTagValue())));
        LabelElement videoInfoLabel = new LabelElement("Video info title label", By.xpath(String.format(ADVANCED_TEASER_VIDEO_INFO_LOCATOR_TEMPLATE, ParagraphFormats.PARAGRAPH.getTagValue())));
        
        String actualVideoInfoTitle = videoInfoTitleLabel.element().getText();
        String actualVideoInfo = videoInfoLabel.element().getText();
        
        Verifications.assertContains(actualVideoInfoTitle, videoInfoTitle, "Info title should be '"
                + videoInfoTitle + "', but was '" + actualVideoInfoTitle + "'");
        
        Verifications.assertContains(actualVideoInfo, videoInfo, "Info should be '"
                + videoInfo + "', but was '" + actualVideoInfo + "'");
    }
    
    // -------------------------------------------
    
    // Simple teaser
    
    public void verifySimpleTeaserHeaderInfo(ParagraphFormats headerSize, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.SIMPLE_TEASER, "Simple teaser header",
                HEADER_LOCATOR_TEMPLATE, headerSize.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Simple teaser header info is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifySimpleTeaserHeaderInfo(String value) {
        verifySimpleTeaserHeaderInfo(ParagraphFormats.HEADER_1, value);
    }
    
    public void verifySimpleTeaserMainTextInfo(ParagraphFormats headerSize, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.SIMPLE_TEASER, "Simple teaser main text",
                SIMPLE_TEASER_MAIN_TEXT_LOCATOR_TEMPLATE, headerSize.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Simple teaser main text info is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifySimpleTeaserMainTextInfo(String value) {
        verifySimpleTeaserMainTextInfo(ParagraphFormats.PARAGRAPH, value);
    }
    
    // -------------------------------------------
    
    // Article
    
    public void verifyArticleComponentMainHeadingInfo(ParagraphFormats headerSize, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ARTICLE, "Article main heading",
                ARTICLE_HEADING_LOCATOR_TEMPLATE, headerSize.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Article main heading info is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifyArticleComponentMainHeadingInfo(String value) {
        verifyArticleComponentMainHeadingInfo(ParagraphFormats.HEADER_1, value);
    }
    
    public void verifyArticleComponentLeadTextInfo(ParagraphFormats headerSize, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ARTICLE, "Article lead text",
                ARTICLE_LEAD_TEXT_LOCATOR_TEMPLATE, headerSize.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Article lead text info is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifyArticleComponentLeadTextInfo(String value) {
        verifyArticleComponentLeadTextInfo(ParagraphFormats.PARAGRAPH, value);
    }
    
    public void verifyArticleComponentMainTextInfo(ParagraphFormats headerSize, String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ARTICLE, "Article main text",
                ARTICLE_MAIN_TEXT_LOCATOR_TEMPLATE, headerSize.getTagValue(), value);
        
        Verifications.assertTrue(element.exists(),
                "Article main text info is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    public void verifyArticleComponentMainTextInfo(String value) {
        verifyArticleComponentMainTextInfo(ParagraphFormats.PARAGRAPH, value);
    }
    
    public void verifyArticleComponentDisclaimerTextInfo(String value) {
        Environment.waitForJs();
        LabelElement element = getLabelElement(Components.ARTICLE, "Article disclaimer text",
                DISCLAIMER_TEXT_LOCATOR_TEMPLATE, value);
        
        Verifications.assertTrue(element.exists(),
                "Article disclaimer text is not equal to '" + value + "'. See screenshot for more details.");
    }
    
    // -------------------------------------------
    
    // General links
    
    public void verifyLinkIsOpenedInSameWindow(String linkUrl, String linkText) {
        Environment.waitForJs();
        
        LinkElement link = getGeneralLink(linkUrl, linkText);
        link.clickViaJS();
        
        waitForCurrentUrlToBe(linkUrl, Timeouts.FAST);
        String currentUrl = Environment.getCurrentUrl();
        Verifications.assertContains(currentUrl, linkUrl, "Expected current url is '" + linkText + "', but was '"
                + currentUrl + "'");
        
        ArrayList<String> tabs = Environment.getCurrentTabs();
        Verifications.assertTrue(tabs.size() == 1, "Expected to have one tab opened, but was found '" + tabs.size() + "'");
        
        Environment.navigateBack();
    }
    
    public void verifyLinkIsOpenedInNewWindow(String linkUrl, String linkText) {
        Environment.waitForJs();
        
        LinkElement link = getGeneralLink(linkUrl, linkText);
        link.clickViaJS();
        
        Environment.waitForJs();
        waitForNumberOfTabsToBe(2, Timeouts.FAST);
        
        ArrayList<String> tabs = Environment.getCurrentTabs();
        Verifications.assertTrue(tabs.size() == 2, "Expected to have two tabs opened, but was found '" + tabs.size() + "'");
        
        Environment.switchToTab(tabs.get(1));
        
        waitForCurrentUrlToBe(linkUrl, Timeouts.FAST);
        String currentUrl = Environment.getCurrentUrl();
        Verifications.assertContains(currentUrl, linkUrl, "Expected current url is '" + linkText + "', but was '"
                + currentUrl + "'");
        
        Environment.closeCurrentTab();
        Environment.switchToTab(tabs.get(0));
    }
    
    private LinkElement getGeneralLink(String linkUrl, String linkText) {
        return new LinkElement("General Link", By.xpath(String.format(GENERAL_LINK_LOCATOR_TEMPLATE, linkUrl, linkText)));
    }
    
    private void waitForCurrentUrlToBe(String expectedUrl, Timeouts timeout) {
        try {
            new WebDriverWait(WebDriverFactory.instance().getInitializedDriver(), timeout.getValue())
                    .until(f -> urlContains(expectedUrl));
        } catch (TimeoutException ex) {
            Log.logInfo("Waiting for current url to be = " + expectedUrl);
        }
    }
    
    private void waitForNumberOfTabsToBe(int expectedNumberOfTabs, Timeouts timeout) {
        try {
            new WebDriverWait(WebDriverFactory.instance().getInitializedDriver(), timeout.getValue())
                    .until(f -> getCurrentNumberOfTabs() == expectedNumberOfTabs);
        } catch (TimeoutException ex) {
            Log.logInfo("Waiting for number of tabs to be = " + expectedNumberOfTabs);
        }
    }
    
    private boolean urlContains(String expectedUrl) {
        return Environment.getCurrentUrl().contains(expectedUrl);
    }
    
    private int getCurrentNumberOfTabs() {
        return Environment.getCurrentTabs().size();
    }
    
    public void verifyThatLinkIsAbsent(String linkUrl, String linkText) {
        Environment.waitForJs();
        getGeneralLink(linkUrl, linkText).assertDoesNotExist(Timeouts.VERY_FAST);
    }
    
    // -------------------------------------------
    
}
