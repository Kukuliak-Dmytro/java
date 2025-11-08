package factories;


import entities.Flower;
import entities.Lily;
public class LilyFactory extends AbstractFlowerFactory {
    public LilyFactory(){
        loadConfigFromJSON();
    }

    @Override
    public Flower getFlower(){
        return new Lily();
    }
}
