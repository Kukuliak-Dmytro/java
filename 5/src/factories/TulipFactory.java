package factories;

import factories.AbstractFlowerFactory;
import entities.Flower;
import entities.Tulip;
import utils.JSONUtil;

public class TulipFactory extends AbstractFlowerFactory {
    public TulipFactory(){
        loadConfigFromJSON("tulip");
    }

    @Override
    protected String getFlowerType() {
        return "tulip";
    }

    @Override 
    public Tulip createFlower(){
        Tulip tulip = new Tulip();
        tulip.name = defaultName;
        tulip.color = defaultColor;
        tulip.setFreshness(defaultFreshness);
        tulip.setStemLength(defaultStemLength);
        tulip.setPrice(price);
        
        String config = getConfigJson();
        if (config != null) {
            tulip.leavesNumber = JSONUtil.getInt(config, "leavesNumber");
        }
        
        return tulip;
    }
}
