package ui;

import commands.BaseCommand;
import storage.Storage;
import commands.CommandHistory;

public class ConsoleMenu {
    public void showMenu(){
        CommandHistory commandHistory = CommandHistory.getInstance();
        Storage storage = Storage.getInstance();
        CommandMenu commandMenu = new CommandMenu();
        for(BaseCommand command: commandMenu.commandList) {
            command.getInfo();
        }
    }
    
    public void handleInput(){
        // Basic implementation for now
        System.out.println("Menu displayed. Input handling not yet implemented.");
    }
}
