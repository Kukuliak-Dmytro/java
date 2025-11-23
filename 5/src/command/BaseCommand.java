package command;

public abstract class BaseCommand {

    // Метод для виконання дії
    public void execute(){};

    // Метод для скасування дії
    public void undo(){};

    public void getInfo(){}
    
    // Метод для створення копії команди з поточним станом
    // Використовується для збереження в історії, щоб уникнути проблеми з перезаписом стану
    public BaseCommand copy(){
        return this; // За замовчуванням повертаємо себе (для статичних команд)
    }
}