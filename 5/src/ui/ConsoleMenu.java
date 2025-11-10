package ui;

import commands.*;
import commands.BaseCommand;
import utils.InputUtil;
import java.util.ArrayList;
import java.util.List;

public class ConsoleMenu {
    private List<BaseCommand> commandList = new ArrayList<>();
    
    public ConsoleMenu(){
        init();
    }
    
    private void init(){
        commandList.add(new CreateFlowerCommand());
        commandList.add(new RemoveFlowerCommand());
        commandList.add(new CreateBouquetCommand());
        commandList.add(new AddFlowerToBouquetCommand());
        commandList.add(new RemoveFlowerFromBouquetCommand());
        commandList.add(new AddAccessoryToBouquetCommand());
        commandList.add(new SortBouquetByFreshnessCommand());
        commandList.add(new FindFlowersByStemLengthCommand());
        commandList.add(new LoadFromJsonCommand());
        commandList.add(new SaveToJsonCommand());
        commandList.add(new UndoCommand());
        commandList.add(new DisplayFlowersCommand());
        commandList.add(new DisplayBouquetsCommand());
        commandList.add(new DisplayHistoryCommand());
    }
    
    public void showMenu(){
        System.out.println("\nAvailable commands:");
        for(int i = 0; i < commandList.size(); i++) {
            System.out.print((i + 1) + ". ");
            commandList.get(i).getInfo();
        }
    }
    
    public void handleInput(){
        showMenu();
        
        while(true) {
            int commandNumber = InputUtil.readInt("\nEnter command number (or 0 to exit): ");
            
            if (commandNumber == 0) {
                System.out.println("Exiting...");
                break;
            }
            
            int index = commandNumber - 1;
            if (index >= 0 && index < commandList.size()) {
                BaseCommand command = commandList.get(index);
                System.out.print("\n>>> Executing: ");
                command.getInfo();
                System.out.println();
                command.execute();
                System.out.println();
                showMenu();
            } else {
                System.out.println("Invalid command number. Please try again.");
                showMenu();
            }
        }
    }
}
