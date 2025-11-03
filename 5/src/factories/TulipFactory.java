package factories;

import factories.AbstractFlowerFactory;
import entities.Flower;
import entities.Tulip;
public class TulipFactory extends AbstractFlowerFactory {
    private static final TulipFactory instance = new TulipFactory();


    private TulipFactory(){
        loadConfigFromJSON();
    }

    public static TulipFactory getFactory(){return instance;}

    @Override public Tulip getFlower(){return new Tulip();}
}
