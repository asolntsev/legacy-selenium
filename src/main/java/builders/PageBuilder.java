package builders;

import entities.Page;

public class PageBuilder {
    
    private String name;
    private String title;
    private String parentPath;
    private String templatePath;
    
    public static PageBuilder newPage() {
        return new PageBuilder();
    }
    
    public PageBuilder withTitle(String title) {
        this.title = title;
        return this;
    }
    
    public PageBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public PageBuilder withParentPath(String parentPath) {
        this.parentPath = parentPath;
        return this;
    }
    
    public PageBuilder withTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }
    
    public Page build() {
        Page page = new Page();
        page.setTitle(title);
        page.setName(name);
        page.setParentPath(parentPath);
        page.setTemplatePath(templatePath);
        
        return page;
    }
    
}
