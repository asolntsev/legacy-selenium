package entities.enums;

public enum AssetTypes {
    
    IMAGES("Images"),
    VIDEOS("Videos"),
    PRODUCTS("Products"),
    DOCUMENTS("Documents");
    
    AssetTypes(String value) {
        this.value = value;
    }
    
    private final String value;
    
    public String getValue() {
        return value;
    }
}
