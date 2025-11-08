package commands;

import factories.TulipFactory;
import storage.Storage;
import entities.Tulip;
public class CreateFlowerCommand extends BaseCommand  {

    private static final TulipFactory tulipFactory = new TulipFactory();

    @Override
    protected void execute(){
        Tulip flower= tulipFactory.getFlower();
        Storage.getInstance().addFlower(flower);
        CommandHistory.getInstance().push(this);
    }

    @Override
    public void getInfo(){
        System.out.println("Create Flower Command");
    }

}
