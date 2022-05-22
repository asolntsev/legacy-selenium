package entities.enums;

public enum ParagraphFormats {
    HEADER_1("Header 1", "h1"),
    HEADER_2("Header 2", "h2"),
    HEADER_3("Header 3", "h3"),
    HEADER_4("Header 4", "h4"),
    PARAGRAPH("Paragraph", "p");
    
    private String tag;
    private String value;
    
    ParagraphFormats(String value, String tag) {
        this.tag = tag;
        this.value = value;
    }
    
    public String getTagValue() {
        return tag;
    }
    
    public String getValue() {
        return value;
    }
    
}
