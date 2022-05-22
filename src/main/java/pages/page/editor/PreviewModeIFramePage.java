package pages.page.editor;

import elements.LabelElement;
import entities.Components;
import entities.enums.ParagraphFormats;
import org.openqa.selenium.By;

public class PreviewModeIFramePage extends PreviewModePage {
    private static final By PAGE_LOCATOR = By.id("content");
    private static final String PREVIEW_MODE_PAGE_NAME = "Preview mode";
    private ContentAbstractPage contentAbstractPage = new ContentAbstractPage(PREVIEW_MODE_PAGE_NAME);
    
    public PreviewModeIFramePage() {
        super("Preview mode IFrame", PAGE_LOCATOR);
    }
    
    public LabelElement getComponentContainerInPreviewMode(Components component) {
        return contentAbstractPage.getComponentContainer(component);
    }
    
    // Common
    
    public void verifyImageExistence(String fullFileName, boolean exists) {
        contentAbstractPage.verifyImageExistence(fullFileName, exists);
    }
    
    public void verifyVideoIFrameExistence(String youtubeLinkId) {
        contentAbstractPage.verifyVideoIFrameExistence(youtubeLinkId);
    }
    
    // --------------------------------
    // Advanced teaser
    
    public void verifyAdvancedTeaserHeaderInfo(ParagraphFormats headerSize, String value) {
        contentAbstractPage.verifyAdvancedTeaserHeaderInfo(headerSize, value);
    }
    
    public void verifyAdvancedTeaserHeaderInfo(String value) {
        contentAbstractPage.verifyAdvancedTeaserHeaderInfo(value);
    }
    
    public void verifyAdvancedTeaserMainTextInfo(ParagraphFormats format, String value) {
        contentAbstractPage.verifyAdvancedTeaserMainTextInfo(format, value);
    }
    
    public void verifyAdvancedTeaserMainTextInfo(String value) {
        contentAbstractPage.verifyAdvancedTeaserMainTextInfo(value);
    }
    
    public void verifyAdvancedTeaserDisclaimerText(String value) {
        contentAbstractPage.verifyAdvancedTeaserDisclaimerText(value);
    }
    
    public void verifyAdvancedTeaserImageExistence(String fullFileName, boolean exists) {
        contentAbstractPage.verifyImageExistence(fullFileName, exists);
    }
    
    public void verifyAdvancedTeaserVideoUrlExists(String youtubeUrlID) {
        contentAbstractPage.verifyAdvancedTeaserVideoUrlExists(youtubeUrlID);
    }
    
    public void verifyAdvancedTeaserVideoTitle(String videoTitle) {
        contentAbstractPage.verifyAdvancedTeaserVideoTitle(videoTitle);
    }
    
    public void verifyAdvancedTeaserVideoInfo(String videoInfoTitle, String videoInfo) {
        contentAbstractPage.verifyAdvancedTeaserVideoInfo(videoInfoTitle, videoInfo);
    }
    
    // ----------------------------------------
    
    // Simple teaser
    
    public void verifySimpleTeaserHeaderInfo(ParagraphFormats headerSize, String value) {
        contentAbstractPage.verifySimpleTeaserHeaderInfo(headerSize, value);
    }
    
    public void verifySimpleTeaserHeaderInfo(String value) {
        contentAbstractPage.verifySimpleTeaserHeaderInfo(value);
    }
    
    public void verifySimpleTeaserMainTextInfo(ParagraphFormats headerSize, String value) {
        contentAbstractPage.verifySimpleTeaserMainTextInfo(headerSize, value);
    }
    
    public void verifySimpleTeaserMainTextInfo(String value) {
        contentAbstractPage.verifySimpleTeaserMainTextInfo(value);
    }
    
    // ----------------------------------------
    
    // Article
    
    public void verifyArticleComponentMainHeadingInfo(ParagraphFormats headerSize, String value) {
        contentAbstractPage.verifyArticleComponentMainHeadingInfo(headerSize, value);
    }
    
    public void verifyArticleComponentMainHeadingInfo(String value) {
        contentAbstractPage.verifyArticleComponentMainHeadingInfo(value);
    }
    
    public void verifyArticleComponentLeadTextInfo(ParagraphFormats headerSize, String value) {
        contentAbstractPage.verifyArticleComponentLeadTextInfo(headerSize, value);
    }
    
    public void verifyArticleComponentLeadTextInfo(String value) {
        contentAbstractPage.verifyArticleComponentLeadTextInfo(value);
    }
    
    public void verifyArticleComponentMainTextInfo(ParagraphFormats headerSize, String value) {
        contentAbstractPage.verifyArticleComponentMainTextInfo(headerSize, value);
    }
    
    public void verifyArticleComponentMainTextInfo(String value) {
        contentAbstractPage.verifyArticleComponentMainTextInfo(value);
    }
    
    public void verifyArticleComponentDisclaimerTextInfo(String value) {
        contentAbstractPage.verifyArticleComponentDisclaimerTextInfo(value);
    }
    
    // ----------------------------------------
    
    // General links
    
    public void verifyLinkIsOpenedInSameWindow(String linkUrl, String linkText) {
        contentAbstractPage.verifyLinkIsOpenedInSameWindow(linkUrl, linkText);
    }
    
    public void verifyLinkIsOpenedInNewWindow(String linkUrl, String linkText) {
        contentAbstractPage.verifyLinkIsOpenedInNewWindow(linkUrl, linkText);
    }
    
    public void verifyThatLinkIsAbsent(String linkUrl, String linkText) {
        contentAbstractPage.verifyThatLinkIsAbsent(linkUrl, linkText);
    }
    
    // ----------------------------------------
    
}
