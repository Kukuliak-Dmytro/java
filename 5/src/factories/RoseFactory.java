package factories;


import entities.Flower;
import entities.Rose;
public class RoseFactory extends AbstractFlowerFactory {
    public RoseFactory(){
        loadConfigFromJSON();
    }

    @Override
    public Flower getFlower(){
        return new Rose();
    }
}
