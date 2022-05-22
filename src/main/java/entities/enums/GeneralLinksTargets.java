package entities.enums;

public enum GeneralLinksTargets {
    DEFAULT("default"),
    SAME_WINDOW("same-window"),
    NEW_WINDOW("new-window");
    
    String value;
    
    GeneralLinksTargets(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
