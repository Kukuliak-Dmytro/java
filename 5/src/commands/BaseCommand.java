package commands;

public abstract class BaseCommand {

    // Метод для виконання дії
    public void execute(){};

    // Метод для скасування дії
    protected void undo(){};

    public void getInfo(){}
}