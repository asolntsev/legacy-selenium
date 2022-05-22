package entities.enums;

public enum GeneralLinksActionTypes {
    GO_TO_PAGE("regular"),
    OPEN_LAYER("top-layer"),
    EMAIL("email"),
    PHONE_NUMBER("phonenumber"),
    DOCUMENT("document");
    
    String value;
    
    GeneralLinksActionTypes(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
