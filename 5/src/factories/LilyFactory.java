package factories;


import entities.Flower;
import entities.Lily;
public class LilyFactory extends AbstractFlowerFactory {
    public LilyFactory(){
        loadConfigFromJSON("lily");
    }

    @Override
    public Flower createFlower(){
        Lily lily = new Lily();
        lily.name = defaultName;
        lily.color = defaultColor;
        lily.setFreshness(defaultFreshness);
        lily.setStemLength(defaultStemLength);
        lily.setPrice(price);
        return lily;
    }
}
