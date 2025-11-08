package commands;

public abstract class BaseCommand {

    // Метод для виконання дії
    public void execute(){};

    // Метод для скасування дії
    public void undo(){};

    public void getInfo(){}
}