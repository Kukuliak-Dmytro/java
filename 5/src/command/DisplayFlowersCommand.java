package command;

import storage.Storage;

public class DisplayFlowersCommand extends BaseCommand {

    @Override
    public void execute(){
        Storage.getInstance().displayAllFlowers();
    }

    @Override
    public void getInfo(){
        System.out.println("Display Flowers");
    }
}

