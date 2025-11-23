package storage;

import model.Flower;
import utils.StorageParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Logger logger = LogManager.getLogger(Storage.class);

//    the actual instance of the storage
    private static final Storage instance = new Storage();

    private List<Flower> flowersInStorage;
    private List<Bouquet> bouquetsInStorage;

    public List<Flower> getFlowersInStorage() {
        return flowersInStorage;
    }
    
    public List<Bouquet> getBouquetsInStorage(){
        return bouquetsInStorage;
    }
    
//    we make this constructor private
//    singleton pattern
    private Storage(){
        flowersInStorage = new ArrayList<>();
        bouquetsInStorage = new ArrayList<>();
        readFromJson();
    }

//    returns the singleton Storage
    public static Storage getInstance(){return instance;}

    public void addFlower(Flower flower){
        if (flower == null) {
            return;
        }
        flowersInStorage.add(flower);
        logger.info("Flower created: {} ({})", flower.getName(), flower.getClass().getSimpleName());
    }
    
    public void removeFlower(Flower flower){
        if (flower == null) {
            return;
        }
        flowersInStorage.remove(flower);
    }
    
    public void displayAllFlowers(){
        if (flowersInStorage.isEmpty()) {
            System.out.println("No flowers in storage.");
            return;
        }
        
        System.out.println("\nFlowers in storage (" + flowersInStorage.size() + "):");
        System.out.println("----------------------------------------");
        for (int i = 0; i < flowersInStorage.size(); i++) {
            System.out.print((i + 1) + ". ");
            flowersInStorage.get(i).display();
        }
        System.out.println("----------------------------------------");
    }
    
    public void displayAllBouquets(){
        if (bouquetsInStorage.isEmpty()) {
            System.out.println("No bouquets in storage.");
            return;
        }
        
        System.out.println("\nBouquets in storage (" + bouquetsInStorage.size() + "):");
        System.out.println("----------------------------------------");
        for (int i = 0; i < bouquetsInStorage.size(); i++) {
            System.out.print((i + 1) + ". ");
            bouquetsInStorage.get(i).displayShort();
        }
        System.out.println("----------------------------------------");
    }
    
    public void addBouquet(Bouquet bouquet){
        if (bouquet == null) {
            return;
        }
        bouquetsInStorage.add(bouquet);
        logger.info("Bouquet created with {} flowers", bouquet.getFlowers().size());
    }
    
    public void removeBouquet(Bouquet bouquet){
        if (bouquet == null) {
            return;
        }
        bouquetsInStorage.remove(bouquet);
    }
    
    public void moveFlowerToBouquet(Flower flower, Bouquet bouquet){
        if (flower == null || bouquet == null) {
            return;
        }
        removeFlower(flower);
        bouquet.addFlower(flower);
        logger.info("Flower added to bouquet: {} ({})", flower.getName(), flower.getClass().getSimpleName());
    }
    
    public void readFromJson(){
        String filePath = "storage.json";
        try {
            StorageParser.parseStorageFromJson(this, filePath);
            logger.info("Storage loaded from JSON");
        } catch (Exception e) {
            logger.error("Failed to load storage from JSON", e);
        }
    }
    
    public void writeToJson(){
        String filePath = "storage.json";
        try {
            StorageParser.serializeStorageToJson(this, filePath);
            logger.info("Storage saved to JSON");
        } catch (Exception e) {
            logger.error("Failed to save storage to JSON", e);
        }
    }
}
