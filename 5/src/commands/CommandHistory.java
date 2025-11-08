package commands;

import java.util.Stack;

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
}
