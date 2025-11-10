package commands;

public class UndoCommand extends BaseCommand {

    @Override
    public void execute(){
        CommandHistory history = CommandHistory.getInstance();
        BaseCommand lastCommand = history.pop();
        
        if (lastCommand == null) {
            System.out.println("No commands to undo.");
            return;
        }
        
        System.out.print("Undoing: ");
        lastCommand.getInfo();
        System.out.println();
        lastCommand.undo();
    }

    @Override
    public void getInfo(){
        System.out.println("Undo");
    }
}

