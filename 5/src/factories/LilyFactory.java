package factories;


import entities.Flower;
import entities.Lily;
import utils.JSONUtil;

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
        
        String config = getConfigJson();
        if (config != null) {
            lily.bloomLevel = JSONUtil.getFloat(config, "bloomLevel");
        }
        
        return lily;
    }
}
