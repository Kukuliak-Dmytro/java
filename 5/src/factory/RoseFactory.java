package factory;

import model.Flower;
import model.Rose;
import utils.ConfigLoader.FlowerConfig;

public class RoseFactory extends AbstractFlowerFactory {
    public RoseFactory(){
        loadConfigFromJSON("rose");
    }

    @Override
    protected String getFlowerType() {
        return "rose";
    }

    @Override
    public Flower createFlower(){
        Rose rose = new Rose();
        rose.name = defaultName;
        rose.color = defaultColor;
        rose.setFreshness(defaultFreshness);
        rose.setStemLength(defaultStemLength);
        rose.setPrice(price);
        
        FlowerConfig config = getConfigJson();
        if (config != null && config.hasThorns != null) {
            rose.hasThorns = config.hasThorns;
        }
        
        return rose;
    }
}
