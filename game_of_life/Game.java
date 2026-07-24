
/**
 * 
 */
public class Game {
    private final UserInterface USER_INTERFACE;
    private final Grid GRID;
    
    Game(UserInterface userInterface, Grid grid) {
        this.USER_INTERFACE = userInterface;
        this.GRID = grid;
    }
    
    static Game standardGame() {
        return new Game(new TerminalUserInterface(), new StandardBorderedVariableSizeGameGrid(25,25));
    }
    
    void start() {
        mainMenu();
    }
    
    private void mainMenu() {            
        String menuPrompt = "Welcome to Game of Life";
        String[] menuOptions = {"Play Game of Life", "Credits(John Conway)", "Settings", "Exit"};
        USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        
        int option = USER_INTERFACE.indexOflastSelectedOptionByUser();
        switch(option) {
            case 0:
                playMenu();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                return; // Returns out of the program
            default:
                throw new RuntimeException();
        }
    }
    
    private void playMenu() {
        String menuPrompt = "What would you like to do?";
        String[] menuOptions = {"Advance One Generation", "Expert Controls", "Main Menu"};
        USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        
        int option = USER_INTERFACE.indexOflastSelectedOptionByUser();
        switch(option) {
            case 0:
                GRID.stepForwardGenerations(1);
                USER_INTERFACE.updateGrid(GRID.cells());
                playMenu();
                break;
            case 1:
                expertMenu();
                break;
            case 2:
                mainMenu();
            default:
                throw new RuntimeException();
        }
    }
    
    private void expertMenu() {
        
    }
}
