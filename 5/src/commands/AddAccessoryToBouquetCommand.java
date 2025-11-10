package commands;

import storage.Storage;
import storage.Bouquet;
import utils.InputUtil;
import java.util.List;

public class AddAccessoryToBouquetCommand extends BaseCommand {
    private Bouquet bouquet;
    private String accessoryName;
    private float accessoryPrice;

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        List<Bouquet> bouquets = storage.getBouquetsInStorage();
        
        if (bouquets.isEmpty()) {
            System.out.println("No bouquets in storage. Create a bouquet first.");
            return;
        }
        
        System.out.println("\nSelect a bouquet:");
        storage.displayAllBouquets();
        
        int bouquetChoice = InputUtil.readInt("Enter bouquet number (1-" + bouquets.size() + "): ", 1, bouquets.size());
        
        if (bouquetChoice <= 0) {
            System.out.println("Invalid choice.");
            return;
        }
        
        bouquet = bouquets.get(bouquetChoice - 1);
        
        System.out.println("\nAvailable accessories:");
        System.out.println("1. Ribbon (10 UAH)");
        System.out.println("2. Wrapping Paper (15 UAH)");
        System.out.println("3. Decorative Leaves (20 UAH)");
        System.out.println("4. Gift Card (5 UAH)");
        System.out.println("5. Custom accessory");
        
        int choice = InputUtil.readInt("Enter accessory choice (1-5): ", 1, 5);
        
        switch(choice) {
            case 1:
                accessoryName = "Ribbon";
                accessoryPrice = 10.0f;
                break;
            case 2:
                accessoryName = "Wrapping Paper";
                accessoryPrice = 15.0f;
                break;
            case 3:
                accessoryName = "Decorative Leaves";
                accessoryPrice = 20.0f;
                break;
            case 4:
                accessoryName = "Gift Card";
                accessoryPrice = 5.0f;
                break;
            case 5:
                accessoryName = InputUtil.readString("Enter accessory name: ");
                accessoryPrice = InputUtil.readFloat("Enter accessory price (UAH): ", 0.0f, 1000.0f);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        bouquet.addAccessory(accessoryName, accessoryPrice);
        CommandHistory.getInstance().push(this);
        System.out.println("Accessory '" + accessoryName + "' added to bouquet successfully!");
        System.out.println("Bouquet total price: " + bouquet.getPrice() + " UAH");
    }

    @Override
    public void undo(){
        if (bouquet != null && accessoryName != null) {
            // Remove the last added accessory (simple undo)
            List<String> accessories = bouquet.getAccessories();
            List<Float> prices = bouquet.getAccessoryPrices();
            if (!accessories.isEmpty() && accessories.get(accessories.size() - 1).equals(accessoryName)) {
                accessories.remove(accessories.size() - 1);
                prices.remove(prices.size() - 1);
                bouquet.calculatePrice();
                System.out.println("Undone: Accessory '" + accessoryName + "' removed from bouquet.");
            }
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Add Accessory To Bouquet");
    }
    
    @Override
    public BaseCommand copy(){
        AddAccessoryToBouquetCommand copy = new AddAccessoryToBouquetCommand();
        copy.bouquet = this.bouquet;
        copy.accessoryName = this.accessoryName;
        copy.accessoryPrice = this.accessoryPrice;
        return copy;
    }
}

