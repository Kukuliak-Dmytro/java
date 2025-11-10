package commands;

import java.util.List;

public class DisplayHistoryCommand extends BaseCommand {

    @Override
    public void execute(){
        CommandHistory history = CommandHistory.getInstance();
        List<BaseCommand> commands = history.getAllCommands();
        
        if (commands.isEmpty()) {
            System.out.println("No commands in history.");
            return;
        }
        
        System.out.println("\nCommand history (" + commands.size() + "):");
        System.out.println("----------------------------------------");
        for (int i = 0; i < commands.size(); i++) {
            System.out.print((i + 1) + ". ");
            commands.get(i).getInfo();
        }
        System.out.println("----------------------------------------");
    }

    @Override
    public void getInfo(){
        System.out.println("Display History");
    }
}

