package pages.components.advancedteaser;

import elements.LabelElement;
import elements.TextElement;
import org.openqa.selenium.By;

public class AdvancedTeaserVideoTeaserTabPage extends AdvancedTeaserPage {
    private static final By VIDEO_TITLE_LOCATOR = By.name("./main-video/videoTitle");
    private static final By VIDEO_URL_LOCATOR = By.name("./main-video/videoExternalUrl");
    private static final By VIDEO_INFO_TITLE_LOCATOR = By.name("./main-video/videoInfoTitle");
    private static final By VIDEO_INFO_LOCATOR = By.name("./main-video/videoInfo");
    private static final By VIDEO_PREVIEW_IMAGE_LOCATOR = By.name("./main-video/thumbnail-image/image");
    
    public AdvancedTeaserVideoTeaserTabPage() {
        super("Advanced teaser video teaser tab", VIDEO_TITLE_LOCATOR);
    }
    
    public void setVideoTitle(String videoTitle) {
        new TextElement("Video title", VIDEO_TITLE_LOCATOR).setValueByKeyPress(videoTitle);
    }
    
    public void setVideoUrl(String videoUrl) {
        new TextElement("Video title", VIDEO_URL_LOCATOR).setValueByKeyPress(videoUrl);
    }
    
    public void setVideoInfoTitle(String videoInfoTitle) {
        new TextElement("Video title", VIDEO_INFO_TITLE_LOCATOR).setValueByKeyPress(videoInfoTitle);
    }
    
    public void setVideoInfo(String videoInfo) {
        new TextElement("Video title", VIDEO_INFO_LOCATOR).setValueByKeyPress(videoInfo);
    }
    
    public LabelElement getVideoPreviewImagePlaceholder() {
        return new LabelElement("Video preview image placeholder", VIDEO_PREVIEW_IMAGE_LOCATOR);
    }
}
