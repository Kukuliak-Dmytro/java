package commands;

import storage.Storage;
import entities.Flower;
import utils.InputUtil;
import java.util.List;

public class RemoveFlowerCommand extends BaseCommand {

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        List<Flower> flowers = storage.getFlowersInStorage();
        
        if (flowers.isEmpty()) {
            System.out.println("No flowers in storage to remove.");
            return;
        }
        
        storage.displayAllFlowers();
        
        int choice = InputUtil.readInt("Enter flower number to remove (1-" + flowers.size() + "): ", 1, flowers.size());
        
        if (choice > 0) {
            int index = choice - 1;
            Flower flowerToRemove = flowers.get(index);
            storage.removeFlower(flowerToRemove);
            CommandHistory.getInstance().push(this);
            System.out.println("Flower removed successfully!");
        } else {
            System.out.println("Invalid choice. No flower removed.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Remove Flower Command");
    }

}



