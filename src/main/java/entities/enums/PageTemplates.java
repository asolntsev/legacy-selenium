package entities.enums;

public enum PageTemplates {
    SITE("/apps/huge-cms/templates/app-root/site"),
    FLEX("/apps/huge-cms/templates/app-root/pages/flex"),
    PRODUCT("/apps/huge-cms/templates/app-root/pages/product");
    
    String path;
    
    PageTemplates(String path) {
        this.path = path;
        
    }
    
    public String getPath() {
        return path;
    }
}
