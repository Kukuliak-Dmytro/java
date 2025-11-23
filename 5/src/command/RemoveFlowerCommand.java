package command;

import storage.Storage;
import model.Flower;
import utils.InputUtil;
import java.util.List;

public class RemoveFlowerCommand extends BaseCommand {
    private Flower flower;

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
            flower = flowers.get(index);
            storage.removeFlower(flower);
            CommandHistory.getInstance().push(this);
            System.out.println("Flower removed successfully!");
        } else {
            System.out.println("Invalid choice. No flower removed.");
        }
    }

    @Override
    public void undo(){
        if (flower != null) {
            Storage storage = Storage.getInstance();
            storage.addFlower(flower);
            System.out.println("Undone: Flower added back to storage.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Remove Flower");
    }
    
    @Override
    public BaseCommand copy(){
        RemoveFlowerCommand copy = new RemoveFlowerCommand();
        copy.flower = this.flower;
        return copy;
    }

}



