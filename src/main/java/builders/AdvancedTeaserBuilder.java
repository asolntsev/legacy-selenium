package builders;

import entities.components.AdvancedTeaserEntity;

public class AdvancedTeaserBuilder {
    private String disclaimerText = "";
    private String heading = "";
    private String mainText = "";
    
    public static AdvancedTeaserBuilder createInstance() {
        return new AdvancedTeaserBuilder();
    }
    
    public AdvancedTeaserBuilder withDisclaimerText(String disclaimerText) {
        this.disclaimerText = disclaimerText;
        return this;
    }
    
    public AdvancedTeaserBuilder withHeading(String heading) {
        this.heading = heading;
        return this;
    }
    
    public AdvancedTeaserBuilder withMainText(String mainText) {
        this.mainText = mainText;
        return this;
    }
    
    public AdvancedTeaserEntity build() {
        AdvancedTeaserEntity advancedTeaserEntity = new AdvancedTeaserEntity();
        advancedTeaserEntity.setHeading(heading);
        advancedTeaserEntity.setDisclaimerText(disclaimerText);
        advancedTeaserEntity.setMainText(mainText);
        
        return advancedTeaserEntity;
    }
}
