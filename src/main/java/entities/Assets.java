package entities;

import java.io.File;

public class Assets {
    
    public static final Assets FILE_JPG = new Assets("file1", ".jpg", "image/jpeg");
    public static final Assets FILE_PNG = new Assets("file2", ".png", "image/png");
    public static final Assets FILE_GIF = new Assets("file3", ".gif", "image/gif");
    public static final Assets FILE_DOC = new Assets("file4", ".doc", "application/msword");
    public static final Assets FILE_XML = new Assets("file5", ".xml", "text/xml");
    public static final Assets FILE_XLSX = new Assets("file6", ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final Assets FILE_PPTX = new Assets("file7", ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    
    private static final String FILE_FOLDER_PATH = "C:\\files";
    
    private String name;
    private String path;
    private String extension;
    private String contentType;
    
    private Assets(String name, String extension, String contentType) {
        this.name = name;
        this.path = FILE_FOLDER_PATH + File.separator + name + extension;
        this.extension = extension;
        this.contentType = contentType;
    }
    
    public Assets() {
    
    }
    
    public String getExtension() {
        return extension;
    }
    
    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getFullName() {
        return name + extension;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
