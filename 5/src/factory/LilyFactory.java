package factory;

import model.Flower;
import model.Lily;
import utils.ConfigLoader.FlowerConfig;

public class LilyFactory extends AbstractFlowerFactory {
    public LilyFactory(){
        loadConfigFromJSON("lily");
    }

    @Override
    protected String getFlowerType() {
        return "lily";
    }

    @Override
    public Flower createFlower(){
        Lily lily = new Lily();
        lily.name = defaultName;
        lily.color = defaultColor;
        lily.setFreshness(defaultFreshness);
        lily.setStemLength(defaultStemLength);
        lily.setPrice(price);
        
        FlowerConfig config = getConfigJson();
        if (config != null && config.bloomLevel != null) {
            lily.bloomLevel = config.bloomLevel;
        }
        
        return lily;
    }
}
