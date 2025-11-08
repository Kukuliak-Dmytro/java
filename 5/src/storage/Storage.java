package storage;

import entities.Flower;
import utils.JSONUtil;
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
            Bouquet bouquet = bouquetsInStorage.get(i);
            System.out.println((i + 1) + ". Bouquet - Flowers: " + bouquet.getFlowers().size() +
                             ", Accessories: " + bouquet.getAccessories().size() +
                             ", Price: " + bouquet.getPrice() + " UAH");
            
            if (!bouquet.getFlowers().isEmpty()) {
                System.out.println("   Flowers in bouquet:");
                for (Flower flower : bouquet.getFlowers()) {
                    if (flower != null) {
                        System.out.println("     - " + flower.getClass().getSimpleName() + 
                                         " (" + (flower.name != null ? flower.name : "N/A") + 
                                         ", " + (flower.color != null ? flower.color : "N/A") + ")");
                    }
                }
            }
            
            if (!bouquet.getAccessories().isEmpty()) {
                System.out.println("   Accessories:");
                for (int j = 0; j < bouquet.getAccessories().size(); j++) {
                    System.out.println("     - " + bouquet.getAccessories().get(j) + 
                                     " (" + bouquet.getAccessoryPrices().get(j) + " UAH)");
                }
            }
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
    
    private void readFromJson(){
        // Placeholder for JSON reading
    }
    
    private void writeToJson(){
        // Placeholder for JSON writing
    }
}
