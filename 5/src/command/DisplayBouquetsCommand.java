package command;

import storage.Storage;

public class DisplayBouquetsCommand extends BaseCommand {

    @Override
    public void execute(){
        Storage.getInstance().displayAllBouquets();
    }

    @Override
    public void getInfo(){
        System.out.println("Display Bouquets");
    }
}

