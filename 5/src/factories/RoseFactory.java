package factories;


import entities.Flower;
import entities.Rose;
public class RoseFactory extends AbstractFlowerFactory {
    private static final RoseFactory instance = new RoseFactory();


    private RoseFactory(){
        loadConfigFromJSON();
    }

    public static RoseFactory getFactory(){return instance;}

}
