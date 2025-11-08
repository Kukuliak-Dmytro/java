//with the new update, Java allows to NOT write static public void main
import commands.CommandHistory;
import storage.Storage;
import ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        //    do the rest of the stuff here
        System.out.println("Hello and welcome!");
        ConsoleMenu menu = new ConsoleMenu();

        menu.showMenu();
        menu.handleInput();
    }
}
