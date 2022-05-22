package entities.enums;

/**
 * All content items
 */
public enum ContentItemsPages implements ContentItems {
    SITES("sites", "Sites"),
    MASTERS("masters", "Masters"),
    HUGEBANK("hugebank", "Huge Bank, inc."),
    SV("sv", "sv"),
    HOME("home", "Home"),
    TESTS("tests", "Tests"),
    AUTOMATION_TESTS("automation-tests", "Automation tests");
    
    ContentItemsPages(String urlValue, String title) {
        this.value = urlValue;
        this.title = title;
    }
    
    private final String value;
    private final String title;
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public ContentItemsPages[] getListOfItems() {
        return getDeclaringClass().getEnumConstants();
    }
    
    public String getTitle() {
        return title;
    }
}
