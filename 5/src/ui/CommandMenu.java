package ui;

import commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandMenu {
    public List<BaseCommand> commandList = new ArrayList<>();

    public CommandMenu(){
        commandList.add(new CreateFlowerCommand());
        commandList.add(new RemoveFlowerCommand());
    }
}
