package command;

import factory.AbstractFlowerFactory;
import factory.TulipFactory;
import factory.LilyFactory;
import factory.RoseFactory;
import storage.Storage;
import model.Flower;
import utils.InputUtil;
import java.util.ArrayList;
import java.util.List;

public class CreateFlowerCommand extends BaseCommand  {
    private Flower flower;

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
            flower = factoryList.get(index).createFlower();
            if (flower != null) {
                Storage.getInstance().addFlower(flower);
                CommandHistory.getInstance().push(this);
                System.out.println("Flower created successfully!");
            } else {
                System.out.println("Failed to create flower.");
            }
        } else {
            System.out.println("Invalid choice. No flower created.");
        }
    }

    @Override
    public void undo(){
        if (flower != null) {
            Storage storage = Storage.getInstance();
            storage.removeFlower(flower);
            System.out.println("Undone: Flower removed from storage.");
        }
    }

    @Override
    public void getInfo(){
        System.out.println("Create Flower");
    }
    
    @Override
    public BaseCommand copy(){
        CreateFlowerCommand copy = new CreateFlowerCommand();
        copy.flower = this.flower;
        return copy;
    }

}
