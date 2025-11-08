package factories;


import entities.Flower;
import entities.Rose;
import utils.JSONUtil;

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
        
        String config = getConfigJson();
        if (config != null) {
            rose.hasThorns = JSONUtil.getBoolean(config, "hasThorns");
        }
        
        return rose;
    }
}
