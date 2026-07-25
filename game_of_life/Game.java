
/**
 * 
 */
public class Game {
    private final UserInterface USER_INTERFACE;
    private final Grid GRID;
    
    private boolean running;
    
    Game(UserInterface userInterface, Grid grid) {
        this.USER_INTERFACE = userInterface;
        this.GRID = grid;
    }
    
    public static Game standardGame() {
        return new Game(new TerminalUserInterface(), new UnborderedVariableSizeGameGrid(16,9));
    }
    
    public void run() {
        running = true;
        mainMenu();
    }
    
    private void mainMenu() {
        while(running) {
            String menuPrompt = "Welcome to Game of Life";
            String[] menuOptions = {"Play Game of Life", "Credits(John Conway) & Info", "Settings", "Exit"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
            
            int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
            switch(option) {
                case 0:
                    playMenu();
                    break;
                case 1:
                    creditsAndInfoMenu();
                    break;
                case 2:
                    settingsMenu();
                    break;
                case 3:
                    exitMenu();
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
            
            int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
            switch(option) {
                case 0: // Advance One Generation
                    GRID.stepForwardGenerations(1);
                    USER_INTERFACE.updateGrid(GRID.cells());
                    break;
                case 1: // Expert Controls
                    expertMenu();
                    break;
                case 2: // Return
                    playing = false;
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }
    
    private void expertMenu() {
        {
            String menuPrompt = "Expert Controls?";
            String[] menuOptions = {
                    "Advance 'x' Gen's",
                    "Toggle Cell",
                    "Fill Grid",
                    "Invert Cells",
                    "Save Seed",
                    "Load Seed",
                    "Return"
            };
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
        switch(option) {
            case 0: // Advance 'x' Gen's
                {
                    USER_INTERFACE.createInputRangeMenu("Enter the number of gens to advance(1-999)", 1, 999);
                    int x = USER_INTERFACE.numberOfLastSelectedOptionByUser();
                    GRID.stepForwardGenerations(x);
                }
                USER_INTERFACE.updateGrid(GRID.cells());
                break;
            case 1: // Toggle Cell
                {
                    Cell[][] cells = GRID.cells();
                    
                    int width = cells[0].length;
                    USER_INTERFACE.createInputRangeMenu("Cell X-coord("+1+"-"+width+")", 1, width);
                    int x = USER_INTERFACE.numberOfLastSelectedOptionByUser() - 1;
                    
                    int height = cells.length;
                    USER_INTERFACE.createInputRangeMenu("Cell Y-coord("+1+"-"+height+")", 1, height);
                    int y = USER_INTERFACE.numberOfLastSelectedOptionByUser() - 1;
                    
                    cells[y][x] = cells[y][x] == Cell.ALIVE ? Cell.DEAD : Cell.ALIVE;
                }
                USER_INTERFACE.updateGrid(GRID.cells());
                break;
            case 2: // Fill Grid
                break;
            case 3: // Invert Cells
                break;
            case 4: // Save Seed
                break;
            case 5: // Load Seed
                break;
            case 6: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
    
    private void creditsAndInfoMenu() {
        {
            String menuPrompt = 
                    "Credits & Info \n" +
                    "\n" +
                    "This game, 'Game of Life', is a recreation of \n" +
                    "the 'cellular automata' created by the famous mathematician 'John Conway'. \n" +
                    "His game is known by the name of mine, but it is aka, \n" +
                    "'Conways Game of Life', or even simply as 'Life'. \n" +
                    "\n" +
                    "Good info here: https://en.wikipedia.org/wiki/Conway's_Game_of_Life' \n" +
                    "But very basically Life is a game about watching interesting patterns \n" +
                    "emerge from its rules about the cells on its grid, \n" +
                    "so, if in doubt, just do that; I wouldn't overthink it.";
            
            String[] menuOptions = {"Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        return;
    }
    
    private void settingsMenu() {
        {
            String menuPrompt = "Settings";
            String[] menuOptions = {"N/A", "N/A","Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
        switch(option) {
            case 0:
                break;
            case 1:
                break;
            case 2: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
    
    private void exitMenu() {
        {
            String menuPrompt = "Please confirm that you wish to exit the program?";
            String[] menuOptions = {"Confirm; Exit Program", "Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
        boolean exitConfirmed;
        switch(option) {
            case 0: // Confirm; Exit Program
                running = false;
                break;
            case 1: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
}
