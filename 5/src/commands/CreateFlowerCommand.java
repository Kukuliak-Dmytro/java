package commands;

import factories.AbstractFlowerFactory;
import factories.TulipFactory;
import factories.LilyFactory;
import factories.RoseFactory;
import storage.Storage;
import entities.Flower;
import utils.InputUtil;
import java.util.ArrayList;
import java.util.List;

public class CreateFlowerCommand extends BaseCommand  {

    private static final List<AbstractFlowerFactory> factoryList = new ArrayList<>();

    static {
        factoryList.add(new TulipFactory());
        factoryList.add(new LilyFactory());
        factoryList.add(new RoseFactory());
    }

    @Override
    public void execute(){
        System.out.println("\nSelect flower type to create:");
        for (int i = 0; i < factoryList.size(); i++) {
            System.out.print((i + 1) + ". ");
            factoryList.get(i).getInfo();
        }
        
        int choice = InputUtil.readInt("Enter choice (1-" + factoryList.size() + "): ", 1, factoryList.size());
        
        if (choice > 0) {
            int index = choice - 1;
            Flower flower = factoryList.get(index).createFlower();
            Storage.getInstance().addFlower(flower);
            CommandHistory.getInstance().push(this);
            System.out.println("Flower created successfully!");
        } else {
            System.out.println("Invalid choice. No flower created.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Create Flower Command");
    }

}
