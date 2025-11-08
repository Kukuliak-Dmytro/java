package commands;

import storage.Storage;
import storage.Bouquet;
import utils.InputUtil;
import java.util.List;

public class SortBouquetByFreshnessCommand extends BaseCommand {
    private Bouquet bouquet;
    private List<entities.Flower> originalOrder;

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        List<Bouquet> bouquets = storage.getBouquetsInStorage();
        
        if (bouquets.isEmpty()) {
            System.out.println("No bouquets in storage.");
            return;
        }
        
        System.out.println("\nSelect a bouquet to sort:");
        storage.displayAllBouquets();
        
        int bouquetChoice = InputUtil.readInt("Enter bouquet number (1-" + bouquets.size() + "): ", 1, bouquets.size());
        
        if (bouquetChoice <= 0) {
            System.out.println("Invalid choice.");
            return;
        }
        
        bouquet = bouquets.get(bouquetChoice - 1);
        
        if (bouquet.getFlowers().isEmpty()) {
            System.out.println("This bouquet has no flowers to sort.");
            return;
        }
        
        // Save original order for undo
        originalOrder = new java.util.ArrayList<>(bouquet.getFlowers());
        
        System.out.println("\nFlowers before sorting:");
        bouquet.displayAllFlowers();
        
        bouquet.sortByFreshness();
        
        System.out.println("\nFlowers after sorting by freshness (freshest first):");
        bouquet.displayAllFlowers();
        
        CommandHistory.getInstance().push(this);
        System.out.println("\nBouquet sorted by freshness successfully!");
    }

    @Override
    public void undo(){
        if (bouquet != null && originalOrder != null) {
            // Restore original order
            bouquet.getFlowers().clear();
            bouquet.getFlowers().addAll(originalOrder);
            System.out.println("Undone: Bouquet order restored.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Sort Bouquet By Freshness Command");
    }
    
    @Override
    public BaseCommand copy(){
        SortBouquetByFreshnessCommand copy = new SortBouquetByFreshnessCommand();
        copy.bouquet = this.bouquet;
        copy.originalOrder = this.originalOrder != null ? new java.util.ArrayList<>(this.originalOrder) : null;
        return copy;
    }
}

