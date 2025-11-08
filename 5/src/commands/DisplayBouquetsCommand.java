package commands;

import storage.Storage;
import storage.Bouquet;
import entities.Flower;

public class DisplayBouquetsCommand extends BaseCommand {

    @Override
    public void execute(){
        Bouquet[] bouquets = Storage.getInstance().getBouquetsInStorage();
        
        if (bouquets.length == 0) {
            System.out.println("No bouquets in storage.");
            return;
        }
        
        System.out.println("\nBouquets in storage (" + bouquets.length + "):");
        System.out.println("----------------------------------------");
        for (int i = 0; i < bouquets.length; i++) {
            Bouquet bouquet = bouquets[i];
            System.out.println((i + 1) + ". Bouquet - Flowers: " + 
                             (bouquet.flowersInBouquet != null ? bouquet.flowersInBouquet.length : 0) +
                             ", Price: " + bouquet.getPrice() + " UAH");
            
            if (bouquet.flowersInBouquet != null && bouquet.flowersInBouquet.length > 0) {
                System.out.println("   Flowers in bouquet:");
                for (int j = 0; j < bouquet.flowersInBouquet.length; j++) {
                    Flower flower = bouquet.flowersInBouquet[j];
                    if (flower != null) {
                        System.out.println("     - " + flower.getClass().getSimpleName() + 
                                         " (" + (flower.name != null ? flower.name : "N/A") + 
                                         ", " + (flower.color != null ? flower.color : "N/A") + ")");
                    }
                }
            }
        }
        System.out.println("----------------------------------------");
    }

    @Override
    public void getInfo(){
        System.out.println("Display Bouquets Command");
    }
}

