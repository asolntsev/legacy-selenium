package builders;

import entities.components.SimpleTeaserEntity;

public class SimpleTeaserBuilder {
    private String heading = "";
    private String mainText = "";
    
    public static SimpleTeaserBuilder createInstance() {
        return new SimpleTeaserBuilder();
    }
    
    public SimpleTeaserBuilder withHeading(String heading) {
        this.heading = heading;
        return this;
    }
    
    public SimpleTeaserBuilder withMainText(String mainText) {
        this.mainText = mainText;
        return this;
    }
    
    public SimpleTeaserEntity build() {
        SimpleTeaserEntity simpleTeaserEntity = new SimpleTeaserEntity();
        simpleTeaserEntity.setHeading(heading);
        simpleTeaserEntity.setMainText(mainText);
        
        return simpleTeaserEntity;
    }
    
}
