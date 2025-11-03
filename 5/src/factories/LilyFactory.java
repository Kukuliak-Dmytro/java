package factories;


import entities.Flower;
import entities.Rose;
public class LilyFactory extends AbstractFlowerFactory {
    private static final LilyFactory instance = new LilyFactory();


    private LilyFactory(){
        loadConfigFromJSON();
    }

    public static LilyFactory getFactory(){return instance;}

}
