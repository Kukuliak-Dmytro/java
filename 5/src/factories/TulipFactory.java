package factories;

import factories.AbstractFlowerFactory;
import entities.Flower;
import entities.Tulip;
public class TulipFactory extends AbstractFlowerFactory {
    public TulipFactory(){
        loadConfigFromJSON("tulip");
    }

    @Override 
    public Tulip createFlower(){
        Tulip tulip = new Tulip();
        tulip.name = defaultName;
        tulip.color = defaultColor;
        tulip.setFreshness(defaultFreshness);
        tulip.setStemLength(defaultStemLength);
        tulip.setPrice(price);
        return tulip;
    }
}
