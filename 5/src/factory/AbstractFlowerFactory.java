package factory;

import model.Flower;
import utils.ConfigLoader;
import utils.ConfigLoader.FlowerConfig;

public abstract class AbstractFlowerFactory {
    protected String defaultName;
    protected String defaultColor;
    protected float defaultFreshness;
    protected float defaultStemLength;
    protected float price;

    public abstract Flower createFlower();
    
    public void loadConfigFromJSON(String flowerType){
        FlowerConfig config = ConfigLoader.getConfig(flowerType);
        
        if (config == null) {
            setDefaults();
            return;
        }
        
        defaultName = config.name;
        defaultColor = config.color;
        defaultFreshness = config.freshness;
        defaultStemLength = config.stemLength;
        price = config.price;
    }
    
    protected FlowerConfig getConfigJson() {
        return ConfigLoader.getConfig(getFlowerType());
    }
    
    protected abstract String getFlowerType();
    
    public void getInfo(){
        System.out.println(getFlowerType().substring(0, 1).toUpperCase() + getFlowerType().substring(1));
    }
    
    private void setDefaults() {
        defaultName = "Default";
        defaultColor = "Default";
        defaultFreshness = 0.0f;
        defaultStemLength = 0.0f;
        price = 0.0f;
    }
}
