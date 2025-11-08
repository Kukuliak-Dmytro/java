package ui;

import commands.BaseCommand;
import storage.Storage;
import commands.CommandHistory;
import utils.InputUtil;

public class ConsoleMenu {
    private CommandMenu commandMenu = new CommandMenu();
    
    public void showMenu(){
        CommandHistory commandHistory = CommandHistory.getInstance();
        Storage storage = Storage.getInstance();
        
        System.out.println("\nAvailable commands:");
        for(int i = 0; i < commandMenu.commandList.size(); i++) {
            System.out.print((i + 1) + ". ");
            commandMenu.commandList.get(i).getInfo();
        }
    }
    
    public void handleInput(){
        while(true) {
            int commandNumber = InputUtil.readInt("\nEnter command number (or 0 to exit): ");
            
            if (commandNumber == 0) {
                System.out.println("Exiting...");
                break;
            }
            
            int index = commandNumber - 1;
            if (index >= 0 && index < commandMenu.commandList.size()) {
                commandMenu.commandList.get(index).execute();
            } else {
                System.out.println("Invalid command number. Please try again.");
            }
        }
    }
}
