package ui;

import commands.BaseCommand;
import storage.Storage;
import commands.CommandHistory;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        
        while(true) {
            System.out.print("\nEnter command number (or 0 to exit): ");
            String input = scanner.nextLine().trim();
            
            if (input.equals("0")) {
                System.out.println("Exiting...");
                break;
            }
            
            try {
                int commandNumber = Integer.parseInt(input);
                int index = commandNumber - 1;
                
                if (index >= 0 && index < commandMenu.commandList.size()) {
                    commandMenu.commandList.get(index).execute();
                    System.out.println("Command executed successfully.");
                } else {
                    System.out.println("Invalid command number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        
        scanner.close();
    }
}
