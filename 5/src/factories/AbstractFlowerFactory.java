package factories;

import entities.Flower;

public abstract class AbstractFlowerFactory {
    protected String defaultName;
    protected String defaultColor;
    //    freshness is measured in hours since being harvested

    protected float defaultFreshness;
    protected float defaultStemLength;
    //    price is measured in UAH
    protected float price;

    public abstract Flower getFlower();
    
    public void loadConfigFromJSON(){
        // Placeholder for JSON loading
        defaultName = "Default";
        defaultColor = "Default";
        defaultFreshness = 0.0f;
        defaultStemLength = 0.0f;
        price = 0.0f;
    }
}
