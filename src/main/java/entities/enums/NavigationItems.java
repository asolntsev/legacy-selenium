package entities.enums;

public enum NavigationItems {
    PROJECTS("project"),
    SITES("pages"),
    EXPERIENCE_FRAGMENTS("filing cabinet"),
    ASSETS("asset"),
    CONTENT_SERVICES("device phone"),
    FORMS("form"),
    SCREENS("rail bottom"),
    PERSONALIZATION("image profile"),
    COMMERCE("shopping cart"),
    COMMUNITIES("users"),
    FILES("folder");
    
    NavigationItems(String value) {
        this.value = value;
    }
    
    private final String value;
    
    public String getValue() {
        return value;
    }
}
