package commands;

import storage.Storage;
import storage.Bouquet;
import entities.Flower;
import utils.InputUtil;
import java.util.List;

public class RemoveFlowerFromBouquetCommand extends BaseCommand {
    private Flower flower;
    private Bouquet bouquet;

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        List<Bouquet> bouquets = storage.getBouquetsInStorage();
        
        if (bouquets.isEmpty()) {
            System.out.println("No bouquets in storage.");
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
        List<Flower> flowersInBouquet = bouquet.getFlowers();
        
        if (flowersInBouquet.isEmpty()) {
            System.out.println("This bouquet has no flowers to remove.");
            return;
        }
        
        System.out.println("\nFlowers in selected bouquet:");
        bouquet.displayAllFlowers();
        
        int flowerChoice = InputUtil.readInt("Enter flower number to remove (1-" + flowersInBouquet.size() + "): ", 1, flowersInBouquet.size());
        
        if (flowerChoice <= 0) {
            System.out.println("Invalid choice.");
            return;
        }
        
        flower = flowersInBouquet.get(flowerChoice - 1);
        bouquet.removeFlower(flower);
        storage.addFlower(flower);
        CommandHistory.getInstance().push(this);
        System.out.println("Flower removed from bouquet and returned to storage!");
    }

    @Override
    public void undo(){
        if (flower != null && bouquet != null) {
            Storage storage = Storage.getInstance();
            storage.removeFlower(flower);
            bouquet.addFlower(flower);
            System.out.println("Undone: Flower added back to bouquet.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Remove Flower From Bouquet Command");
    }
    
    @Override
    public BaseCommand copy(){
        RemoveFlowerFromBouquetCommand copy = new RemoveFlowerFromBouquetCommand();
        copy.flower = this.flower;
        copy.bouquet = this.bouquet;
        return copy;
    }
}

