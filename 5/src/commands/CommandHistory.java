package commands;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CommandHistory {
    private static final CommandHistory instance = new CommandHistory();
    private Stack<BaseCommand> history = new Stack<>();

    private CommandHistory(){}

    public static CommandHistory getInstance(){return instance;}

    public void push(BaseCommand command){
        if (command != null) {
            history.push(command);
        }
    }

    public BaseCommand pop(){
        return history.isEmpty() ? null : history.pop();
    }
    
    public List<BaseCommand> getAllCommands(){
        return new ArrayList<>(history);
    }
    
    public int size(){
        return history.size();
    }
}
