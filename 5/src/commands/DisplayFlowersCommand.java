package commands;

import storage.Storage;
import entities.Flower;

public class DisplayFlowersCommand extends BaseCommand {

    @Override
    public void execute(){
        Flower[] flowers = Storage.getInstance().getFlowersInStorage();
        
        if (flowers.length == 0) {
            System.out.println("No flowers in storage.");
            return;
        }
        
        System.out.println("\nFlowers in storage (" + flowers.length + "):");
        System.out.println("----------------------------------------");
        for (int i = 0; i < flowers.length; i++) {
            System.out.print((i + 1) + ". ");
            flowers[i].display();
        }
        System.out.println("----------------------------------------");
    }

    @Override
    public void getInfo(){
        System.out.println("Display Flowers Command");
    }
}

