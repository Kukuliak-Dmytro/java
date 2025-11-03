package commands;


public class CommandHistory {
    private static final CommandHistory instance = new CommandHistory();

    private CommandHistory(){}

    public static CommandHistory getInstance(){return instance;}

    public void push(BaseCommand command){}

    public void pop(){
        instance.pop();
    }
}
