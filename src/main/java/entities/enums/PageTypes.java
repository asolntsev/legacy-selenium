package entities.enums;

public enum PageTypes {
    PRODUCT("product"),
    SITE("site"),
    FLEX("flex");
    
    private String pageType;
    
    PageTypes(String pageType) {
        this.pageType = pageType;
    }
    
    public String getValue() {
        return pageType;
    }
}
