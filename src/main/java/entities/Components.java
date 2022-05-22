package entities;

public class Components {
    public static final Components ADVANCED_TEASER = new Components("Advanced Teaser Component", "content/responsivegrid/teaser_advanced", "teaser-advanced");
    public static final Components SIMPLE_TEASER = new Components("Simple Teaser Component", "content/responsivegrid/teaser_simple", "teaser-simple");
    public static final Components ARTICLE = new Components("Article Component", "content/responsivegrid/article", "article");
    public static final Components TEASER_LIST = new Components("Teaser List Component", "content/responsivegrid/teaser_list", "teaser-list");
    
    private String name;
    private String dataPath;
    private String value;
    
    private Components(String name, String dataPath, String value) {
        this.name = name;
        this.dataPath = dataPath;
        this.value = value;
    }
    
    public Components() {
    }
    
    public String getName() {
        return name;
    }
    
    public Components setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getDataPath() {
        return dataPath;
    }
    
    public Components setDataPath(String dataPath) {
        this.dataPath = dataPath;
        return this;
    }
    
    public String getValue() {
        return value;
    }
    
    public Components setValue(String value) {
        this.value = value;
        return this;
    }
    
}
