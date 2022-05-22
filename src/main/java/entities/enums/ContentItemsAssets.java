package entities.enums;

public enum ContentItemsAssets implements ContentItems {
    TEST_AUTOMATION("test-automation");
    
    ContentItemsAssets(String urlValue) {
        this.value = urlValue;
    }
    
    private final String value;
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public ContentItemsAssets[] getListOfItems() {
        return getDeclaringClass().getEnumConstants();
    }
}
