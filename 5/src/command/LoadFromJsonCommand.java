package command;

import storage.Storage;

public class LoadFromJsonCommand extends BaseCommand {

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        
        System.out.println("Loading storage from JSON...");
        storage.readFromJson();
        
        System.out.println("Storage loaded successfully!");
        System.out.println("Flowers in storage: " + storage.getFlowersInStorage().size());
        System.out.println("Bouquets in storage: " + storage.getBouquetsInStorage().size());
        // Logging is handled in Storage.readFromJson()
    }

    @Override
    public void getInfo(){
        System.out.println("Load From JSON");
    }
}

