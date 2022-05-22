package pages.components.article;

import elements.DropdownElement;
import elements.LabelElement;
import elements.TextElement;
import org.openqa.selenium.By;

public class ArticleComponentMediaTabPage extends ArticleComponentPage {
    private static final String EXTERNAL_VIDEO_VALUE = "externalVideo";
    
    private static final By MEDIA_TYPE_LOCATOR = By.name("./imageOrVideo");
    private static final By MAIN_IMAGE_LOCATOR = By.name("./main-image/image");
    private static final By THUMBNAIL_IMAGE_LOCATOR = By.name("./main-video/thumbnailImage");
    private static final By VIDEO_URL_LOCATOR = By.name("./main-video/videoExternalUrl");
    private static final By VIDEO_TYPE_LOCATOR = By.name("./main-video/videoType");
    
    public ArticleComponentMediaTabPage() {
        super("Article Media tab", MEDIA_TYPE_LOCATOR);
    }
    
    public void selectMediaType(String value) {
        new DropdownElement("Media type dropdown", MEDIA_TYPE_LOCATOR).selectOptionFromDropdownByValue(value);
    }
    
    public LabelElement getMainImagePlaceholder() {
        return new LabelElement("Main image placeholder", MAIN_IMAGE_LOCATOR);
    }
    
    public LabelElement getThumbnailImageThumbnail() {
        return new LabelElement("Thumbnail image placeholder", THUMBNAIL_IMAGE_LOCATOR);
    }
    
    public void setVideoUrl(String value) {
        new TextElement("Video url placeholder", VIDEO_URL_LOCATOR).setValue(value);
    }
    
    public void selectVideoTypeExternal() {
        new DropdownElement("Video type", VIDEO_TYPE_LOCATOR).selectOptionFromDropdownByValue(EXTERNAL_VIDEO_VALUE);
    }
    
}
