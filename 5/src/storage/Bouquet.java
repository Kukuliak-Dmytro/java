package storage;

import entities.Flower;
import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    private List<Flower> flowersInBouquet;
    private float price;

    public Bouquet(){
        flowersInBouquet = new ArrayList<>();
        price = 0.0f;
    }

    public float getPrice(){
        return price;
    }
    
    public float calculatePrice(){
        float total = 0.0f;
        for (Flower flower : flowersInBouquet) {
            if (flower != null) {
                total += flower.getPrice();
            }
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
        }
        System.out.println("----------------------------------------");
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
}
