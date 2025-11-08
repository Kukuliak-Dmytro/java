package ui;

import commands.BaseCommand;
import storage.Storage;
import commands.CommandHistory;
import utils.InputUtil;

public class ConsoleMenu {
    private CommandMenu commandMenu = new CommandMenu();
    
    public void handleInput(){
        commandMenu.showMenu();
        
        while(true) {
            int commandNumber = InputUtil.readInt("\nEnter command number (or 0 to exit): ");
            
            if (commandNumber == 0) {
                System.out.println("Exiting...");
                break;
            }
            
            int index = commandNumber - 1;
            if (index >= 0 && index < commandMenu.commandList.size()) {
                BaseCommand command = commandMenu.commandList.get(index);
                System.out.print("\n>>> Executing: ");
                command.getInfo();
                System.out.println();
                command.execute();
                System.out.println();
                commandMenu.showMenu();
            } else {
                System.out.println("Invalid command number. Please try again.");
                commandMenu.showMenu();
            }
        }
    }
}
