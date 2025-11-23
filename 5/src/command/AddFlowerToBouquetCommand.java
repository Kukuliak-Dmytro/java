package command;

import storage.Storage;
import storage.Bouquet;
import model.Flower;
import utils.InputUtil;
import java.util.List;

public class AddFlowerToBouquetCommand extends BaseCommand {
    private Flower flower;
    private Bouquet bouquet;

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        List<Bouquet> bouquets = storage.getBouquetsInStorage();
        List<Flower> flowers = storage.getFlowersInStorage();
        
        if (bouquets.isEmpty()) {
            System.out.println("No bouquets in storage. Create a bouquet first.");
            return;
        }
        
        if (flowers.isEmpty()) {
            System.out.println("No flowers in storage to add to bouquet.");
            return;
        }
        
        System.out.println("\nSelect a bouquet:");
        storage.displayAllBouquets();
        
        int bouquetChoice = InputUtil.readInt("Enter bouquet number (1-" + bouquets.size() + "): ", 1, bouquets.size());
        
        if (bouquetChoice <= 0) {
            System.out.println("Invalid choice.");
            return;
        }
        
        bouquet = bouquets.get(bouquetChoice - 1);
        
        System.out.println("\nSelect a flower to add:");
        storage.displayAllFlowers();
        
        int flowerChoice = InputUtil.readInt("Enter flower number (1-" + flowers.size() + "): ", 1, flowers.size());
        
        if (flowerChoice <= 0) {
            System.out.println("Invalid choice.");
            return;
        }
        
        flower = flowers.get(flowerChoice - 1);
        storage.moveFlowerToBouquet(flower, bouquet);
        CommandHistory.getInstance().push(this);
        System.out.println("Flower added to bouquet successfully!");
        // Logging is handled in Storage.moveFlowerToBouquet()
    }

    @Override
    public void undo(){
        if (flower != null && bouquet != null) {
            Storage storage = Storage.getInstance();
            bouquet.removeFlower(flower);
            storage.addFlower(flower);
            System.out.println("Undone: Flower removed from bouquet and returned to storage.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Add Flower To Bouquet");
    }
    
    @Override
    public BaseCommand copy(){
        AddFlowerToBouquetCommand copy = new AddFlowerToBouquetCommand();
        copy.flower = this.flower;
        copy.bouquet = this.bouquet;
        return copy;
    }
}

