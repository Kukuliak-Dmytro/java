//with the new update, Java allows to NOT write static public void main
import commands.CommandHistory;
import storage.Storage;
import ui.ConsoleMenu;
void main() {
   
//    do the rest of the stuff here
    IO.println("Hello and welcome!");
    ConsoleMenu menu = new ConsoleMenu();

    menu.showMenu();
    menu.handleInput();

}
