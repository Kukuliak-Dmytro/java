package storage;

import entities.Flower;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Bouquet {
    private List<Flower> flowersInBouquet;
    private List<String> accessories; // Accessory names
    private List<Float> accessoryPrices; // Accessory prices
    private float price;

    public Bouquet(){
        flowersInBouquet = new ArrayList<>();
        accessories = new ArrayList<>();
        accessoryPrices = new ArrayList<>();
        price = 0.0f;
    }

    public float getPrice(){
        return price;
    }
    
    public float calculatePrice(){
        float total = 0.0f;
        // Add flower prices
        for (Flower flower : flowersInBouquet) {
            if (flower != null) {
                total += flower.getPrice();
            }
        }
        // Add accessory prices
        for (Float accessoryPrice : accessoryPrices) {
            total += accessoryPrice;
        }
        price = total;
        return total;
    }
    
    public void displayAllFlowers(){
        if (flowersInBouquet.isEmpty()) {
            System.out.println("No flowers in bouquet.");
            return;
        }
        
        System.out.println("\nFlowers in bouquet (" + flowersInBouquet.size() + "):");
        System.out.println("----------------------------------------");
        for (int i = 0; i < flowersInBouquet.size(); i++) {
            System.out.print((i + 1) + ". ");
            flowersInBouquet.get(i).display();
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }
    
    public void displayShort(){
        System.out.println("Bouquet - Flowers: " + flowersInBouquet.size() +
                         ", Accessories: " + accessories.size() +
                         ", Price: " + price + " UAH");
    }
    
    public void displayFullDetails(){
        System.out.println("\n=== Bouquet Details ===");
        System.out.println("Total Price: " + price + " UAH");
        
        if (!flowersInBouquet.isEmpty()) {
            displayAllFlowers();
        }
        
        if (!accessories.isEmpty()) {
            displayAccessories();
        }
        
        if (flowersInBouquet.isEmpty() && accessories.isEmpty()) {
            System.out.println("Bouquet is empty.");
        }
    }
    
    public void addFlower(Flower flower){
        flowersInBouquet.add(flower);
        calculatePrice();
    }
    
    public void removeFlower(Flower flower){
        flowersInBouquet.remove(flower);
        calculatePrice();
    }
    
    public List<Flower> getFlowers(){
        return flowersInBouquet;
    }
    
    // Accessory methods
    public void addAccessory(String accessoryName, float price){
        accessories.add(accessoryName);
        accessoryPrices.add(price);
        calculatePrice();
    }
    
    public List<String> getAccessories(){
        return accessories;
    }
    
    public List<Float> getAccessoryPrices(){
        return accessoryPrices;
    }
    
    public void displayAccessories(){
        if (accessories.isEmpty()) {
            System.out.println("No accessories in bouquet.");
            return;
        }
        System.out.println("\nAccessories in bouquet:");
        System.out.println("----------------------------------------");
        for (int i = 0; i < accessories.size(); i++) {
            System.out.println((i + 1) + ". " + accessories.get(i) + " - " + accessoryPrices.get(i) + " UAH");
        }
        System.out.println("----------------------------------------");
    }
    
    // freshest first
    public void sortByFreshness(){
        Collections.sort(flowersInBouquet, new Comparator<Flower>() {
            @Override
            public int compare(Flower f1, Flower f2) {
                // Lower freshness value = fresher (fewer hours since harvest)
                return Float.compare(f1.getFreshness(), f2.getFreshness());
            }
        });
    }
    

    public List<Flower> findFlowersByStemLength(float minLength, float maxLength){
        List<Flower> result = new ArrayList<>();
        for (Flower flower : flowersInBouquet) {
            float stemLength = flower.getStemLength();
            if (stemLength >= minLength && stemLength <= maxLength) {
                result.add(flower);
            }
        }
        return result;
    }
}
