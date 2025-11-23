package commands;

import storage.Storage;

public class SaveToJsonCommand extends BaseCommand {

    @Override
    public void execute(){
        Storage storage = Storage.getInstance();
        
        System.out.println("Saving storage to JSON...");
        storage.writeToJson();
        
        System.out.println("Storage saved successfully to storage.json!");
        System.out.println("Flowers saved: " + storage.getFlowersInStorage().size());
        System.out.println("Bouquets saved: " + storage.getBouquetsInStorage().size());
        // Logging is handled in Storage.writeToJson()
    }

    @Override
    public void getInfo(){
        System.out.println("Save To JSON");
    }
}

