package commands;

import storage.Storage;
import entities.Flower;

public class RemoveFlowerCommand extends BaseCommand {

    private Flower flower;

    @Override
    public void execute(){
        Storage.getInstance().removeFlower(flower);
        CommandHistory.getInstance().push(this);
    }

    @Override
    public void getInfo(){
        System.out.println("Remove Flower Command");
    }

}



