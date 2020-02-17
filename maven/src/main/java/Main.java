import menus.MainMenu;

public class Main {

    MainMenu mainMenu = new MainMenu();

    public static void main(String[] args) {
        new Main().initialize();
    }

    public void initialize(){
        mainMenu.run();
    }
}
