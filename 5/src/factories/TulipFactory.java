package factories;

import factories.AbstractFlowerFactory;
import entities.Flower;
import entities.Tulip;
public class TulipFactory extends AbstractFlowerFactory {
    public TulipFactory(){
        loadConfigFromJSON();
    }

    @Override public Tulip getFlower(){return new Tulip();}
}
