package storage;

import entities.Flower;
import utils.StorageParser;
import java.util.ArrayList;
import java.util.List;

public class Storage {

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
        flowersInStorage.add(flower);
    }
    
    public void removeFlower(Flower flower){
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
        bouquetsInStorage.add(bouquet);
    }
    
    public void removeBouquet(Bouquet bouquet){
        bouquetsInStorage.remove(bouquet);
    }
    
    public void moveFlowerToBouquet(Flower flower, Bouquet bouquet){
        if (flower == null || bouquet == null) {
            return;
        }
        removeFlower(flower);
        bouquet.addFlower(flower);
    }
    
    public void readFromJson(){
        String filePath = "storage.json";
        StorageParser.parseStorageFromJson(this, filePath);
    }
    
    public void writeToJson(){
        String filePath = "storage.json";
        StorageParser.serializeStorageToJson(this, filePath);
    }
}
