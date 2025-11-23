package command;

import storage.Storage;
import storage.Bouquet;
import model.Flower;
import utils.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

public class FindFlowersByStemLengthCommand extends BaseCommand {
    private static final Logger logger = LogManager.getLogger(FindFlowersByStemLengthCommand.class);

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
        
        Bouquet bouquet = bouquets.get(bouquetChoice - 1);
        
        if (bouquet.getFlowers().isEmpty()) {
            System.out.println("This bouquet has no flowers.");
            return;
        }
        
        System.out.println("\nEnter stem length range (in cm):");
        float minLength = InputUtil.readFloat("Minimum stem length: ", 0.0f, 1000.0f);
        float maxLength = InputUtil.readFloat("Maximum stem length: ", minLength, 1000.0f);
        
        List<Flower> foundFlowers = bouquet.findFlowersByStemLength(minLength, maxLength);
        
        if (foundFlowers.isEmpty()) {
            System.out.println("\nNo flowers found with stem length between " + minLength + " cm and " + maxLength + " cm.");
            logger.info("Search performed: No flowers found with stem length between {} cm and {} cm", minLength, maxLength);
        } else {
            System.out.println("\nFound " + foundFlowers.size() + " flower(s) with stem length between " + minLength + " cm and " + maxLength + " cm:");
            System.out.println("----------------------------------------");
            for (int i = 0; i < foundFlowers.size(); i++) {
                System.out.print((i + 1) + ". ");
                foundFlowers.get(i).display();
                System.out.println();
            }
            System.out.println("----------------------------------------");
            logger.info("Search performed: Found {} flower(s) with stem length between {} cm and {} cm", 
                       foundFlowers.size(), minLength, maxLength);
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Find Flowers By Stem Length");
    }
}

