
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
    
    public static Game standardGame() {
        return new Game(new TerminalUserInterface(), new UnborderedVariableSizeGameGrid(16,9));
    }
    
    public void run() {
        mainMenu();
    }
    
    private void mainMenu() {
        boolean running = true;
        
        while(running) {
            String menuPrompt = "Welcome to Game of Life";
            String[] menuOptions = {"Play Game of Life", "Credits(John Conway)", "Settings", "Exit"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
            
            int option = USER_INTERFACE.indexOflastSelectedOptionByUser();
            switch(option) {
                case 0:
                    playMenu();
                    break;
                case 1:
                    creditsMenu();
                    break;
                case 2:
                    settingsMenu();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }
    
    private void playMenu() {
        boolean playing = true;
        
        while(playing) {
            String menuPrompt = "What would you like to do?";
            String[] menuOptions = {"Advance One Generation", "Expert Controls", "Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
            
            int option = USER_INTERFACE.indexOflastSelectedOptionByUser();
            switch(option) {
                case 0: // Advance One Generation
                    GRID.stepForwardGenerations(1);
                    USER_INTERFACE.updateGrid(GRID.cells());
                    break;
                case 1: // Expert Controls
                    expertMenu();
                    break;
                case 2: // Return
                    return;
                default:
                    throw new RuntimeException();
            }
        }
    }
    
    private void expertMenu() {
          
    }
    
    private void creditsMenu() {
        String menuPrompt = 
        "Credits \n"+
        "This game, 'Game of Life', is a recreation of \n"+
        "a game created by the famous mathematician 'John Conway'. \n"+
        "His game is known by the name of mine, but it is aka, \n"+
        "'Conways Game of Life', or even simply as 'Life'.";
        
        String[] menuOptions = {"Return"};
        USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        
        return;
    }
    
    private void settingsMenu() {
        String menuPrompt = "Settings";
        String[] menuOptions = {"N/A", "N/A","Return"};
        USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        
        int option = USER_INTERFACE.indexOflastSelectedOptionByUser();
        switch(option) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                return;
            default:
                throw new RuntimeException();
        }
    }
}
