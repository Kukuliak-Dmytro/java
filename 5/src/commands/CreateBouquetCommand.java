package commands;

import storage.Storage;
import storage.Bouquet;
import entities.Flower;
import utils.InputUtil;
import java.util.List;

public class CreateBouquetCommand extends BaseCommand {
    private Bouquet bouquet;

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        List<Flower> flowers = storage.getFlowersInStorage();
        
        if (flowers.isEmpty()) {
            System.out.println("No flowers in storage to create a bouquet.");
            return;
        }
        
        bouquet = new Bouquet();
        
        while(true) {
            System.out.println("\nCurrent bouquet:");
            bouquet.displayFullDetails();
            
            System.out.println("\nAvailable flowers in storage:");
            storage.displayAllFlowers();
            
            System.out.println("\nOptions:");
            System.out.println("1. Add flower to bouquet");
            System.out.println("2. Finish bouquet creation");
            System.out.println("0. Cancel");
            
            int choice = InputUtil.readInt("Enter choice: ", 0, 2);
            
            if (choice == 0) {
                System.out.println("Bouquet creation cancelled.");
                return;
            }
            
            if (choice == 2) {
                if (bouquet.getFlowers().isEmpty()) {
                    System.out.println("Cannot create empty bouquet. Add at least one flower.");
                    continue;
                }
                storage.addBouquet(bouquet);
                CommandHistory.getInstance().push(this);
                System.out.println("Bouquet created successfully!");
                break;
            }
            
            if (choice == 1) {
                List<Flower> availableFlowers = storage.getFlowersInStorage();
                if (availableFlowers.isEmpty()) {
                    System.out.println("No more flowers available in storage.");
                    continue;
                }
                
                int flowerChoice = InputUtil.readInt("Enter flower number to add (1-" + availableFlowers.size() + "): ", 1, availableFlowers.size());
                
                if (flowerChoice > 0) {
                    int index = flowerChoice - 1;
                    Flower flowerToAdd = availableFlowers.get(index);
                    storage.moveFlowerToBouquet(flowerToAdd, bouquet);
                    System.out.println("Flower added to bouquet!");
                }
            }
        }
    }

    @Override
    public void undo(){
        if (bouquet != null) {
            Storage storage = Storage.getInstance();
            storage.removeBouquet(bouquet);
            List<Flower> flowers = bouquet.getFlowers();
            for (Flower flower : flowers) {
                storage.addFlower(flower);
            }
            System.out.println("Undone: Bouquet removed and all flowers returned to storage.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Create Bouquet");
    }
    
    @Override
    public BaseCommand copy(){
        CreateBouquetCommand copy = new CreateBouquetCommand();
        copy.bouquet = this.bouquet;
        return copy;
    }
}

