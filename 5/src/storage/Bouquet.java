package storage;

import entities.Flower;

public class Bouquet {
    public Flower[] flowersInBouquet;
    private float price;

    public Bouquet(){
        flowersInBouquet = new Flower[0];
        price = 0.0f;
    }

    public float getPrice(){
        return price;
    }
    
    public float calculatePrice(){
        if (flowersInBouquet == null) {
            return 0.0f;
        }
        float total = 0.0f;
        for (Flower flower : flowersInBouquet) {
            if (flower != null) {
                total += flower.getPrice();
            }
        }
        price = total;
        return total;
    }
}
