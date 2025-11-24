package factory;

import model.Flower;
import model.Tulip;
import utils.ConfigLoader.FlowerConfig;

public class TulipFactory extends AbstractFlowerFactory {
    public TulipFactory(){
        loadConfigFromJSON("tulip");
    }

    @Override
    protected String getFlowerType() {
        return "tulip";
    }

    @Override
    public Flower createFlower(){
        Tulip tulip = new Tulip();
        tulip.name = defaultName;
        tulip.color = defaultColor;
        tulip.setFreshness(defaultFreshness);
        tulip.setStemLength(defaultStemLength);
        tulip.setPrice(price);
        
        FlowerConfig config = getConfigJson();
        if (config != null && config.leavesNumber != null) {
            tulip.leavesNumber = config.leavesNumber;
        }
        
        return tulip;
    }
}
