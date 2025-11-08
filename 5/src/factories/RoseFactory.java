package factories;


import entities.Flower;
import entities.Rose;
public class RoseFactory extends AbstractFlowerFactory {
    public RoseFactory(){
        loadConfigFromJSON("rose");
    }

    @Override
    public Flower createFlower(){
        Rose rose = new Rose();
        rose.name = defaultName;
        rose.color = defaultColor;
        rose.setFreshness(defaultFreshness);
        rose.setStemLength(defaultStemLength);
        rose.setPrice(price);
        return rose;
    }
}
