package helpers;

public enum Timeouts {
    NULL(0),
    VERY_FAST(1),
    FAST(5),
    SLOW(15);
    
    private int value;
    
    Timeouts(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
