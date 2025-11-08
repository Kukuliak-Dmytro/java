package ui;

import commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandMenu {
    public List<BaseCommand> commandList = new ArrayList<>();

    public CommandMenu(){
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
}
