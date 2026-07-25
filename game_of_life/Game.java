
import java.util.Scanner;

/**
 * 
 */
public class Game {
    private final UserInterface USER_INTERFACE;
    private Grid grid;
    
    private boolean running;
    
    Game(UserInterface userInterface, Grid grid) {
        this.USER_INTERFACE = userInterface;
        this.grid = grid;
    }
    
    public static Game standardGame() {
        return new Game(new TerminalUserInterface(), new UnborderedVariableSizeGameGrid(16,9));
    }
    
    public void run() {
        running = true;
        mainMenu();
        System.exit(0);
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
            String menuPrompt = "Game of Life";
            String[] menuOptions = {"Next Generation", "Expert Controls", "Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
            
            int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
            switch(option) {
                case 0: // Next Generation
                    grid.stepForwardGenerations(1);
                    USER_INTERFACE.updateGrid(grid.cells());
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
                    "Toggle a Cell",
                    "Fill Grid",
                    "Empty Grid",
                    "Invert Grid",
                    "Get Save Code",
                    "Load Save",
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
                    grid.stepForwardGenerations(x);
                }
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 1: // Toggle A Cell
                {
                    Cell[][] cells = grid.cells();
                    
                    int width = cells[0].length;
                    USER_INTERFACE.createInputRangeMenu("Please enter a column no. ("+1+"-"+width+")", 1, width);
                    int x = USER_INTERFACE.numberOfLastSelectedOptionByUser() - 1;
                    
                    int height = cells.length;
                    USER_INTERFACE.createInputRangeMenu("Please enter a row no. ("+1+"-"+height+")", 1, height);
                    int y = USER_INTERFACE.numberOfLastSelectedOptionByUser() - 1;
                    
                    cells[y][x] = cells[y][x] == Cell.ALIVE ? Cell.DEAD : Cell.ALIVE;
                }
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 2: // Fill Grid
                {
                    Cell[][] cells = grid.cells();
                    
                    for(int row=0;row<cells.length;row++) {
                        for(int column=0;column<cells[row].length;column++) {
                            cells[row][column] = Cell.ALIVE;
                        }
                    }
                }
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 3: // Empty Grid
                {
                    Cell[][] cells = grid.cells();
                    
                    for(int row=0;row<cells.length;row++) {
                        for(int column=0;column<cells[row].length;column++) {
                            cells[row][column] = Cell.DEAD;
                        }
                    }
                }
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 4: // Invert Grid
                {
                    Cell[][] cells = grid.cells();
                    
                    for(int row=0;row<cells.length;row++) {
                        for(int column=0;column<cells[row].length;column++) {
                            cells[row][column] = cells[row][column] == Cell.ALIVE ? Cell.DEAD : Cell.ALIVE;
                        }
                    }
                }
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 5: // Get Save Code
                getSaveCodeMenu();
                break;
            case 6: // Load Save
                loadSaveMenu();
                break;
            case 7: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
    
    private void loadSaveMenu() {
        {
            String menuPrompt = 
                    "You can enter a code from the 'Get Save Code' menu with \n"+
                    "the option below to load it." +
                    "\n";
            String[] menuOptions = {"Enter Save Code Option","Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
        switch(option) {
            case  0:
                {
                    String code = USER_INTERFACE.stringInput("The save code please").trim();
                    
                    Scanner codeParser = new Scanner(code);
                    codeParser.useDelimiter("a|\\n");
                    this.grid = new UnborderedVariableSizeGameGrid(codeParser.nextInt(), codeParser.nextInt());
                    for(int row=0;row<grid.cells().length;row++) {
                        for(int column=0;column<grid.cells()[0].length;column++) {
                            grid.cells()[row][column] = codeParser.nextInt() == 1 ? Cell.ALIVE : Cell.DEAD;
                        }
                    }
                }
                USER_INTERFACE.updateGrid(grid.cells());
            case 1:
                break;
            default:
                throw new RuntimeException();
        }
    }

    private void getSaveCodeMenu() {
    
        String menuPrompt = 
                "You may copy the code below; it can be pasted \n" +
                "into the 'Load Code' menu somewhere else \n" +
                "to reload your current grid/save. \n " +
                "\n" +
                grid.cells()[0].length + "a" +
                grid.cells().length + "a";
                
        for(Cell[] rowOfCells: grid.cells()) {
            for(Cell cell: rowOfCells) {
                menuPrompt += cell == Cell.ALIVE ? "1a" : "0a";
            }
            menuPrompt += "\n";
        }
        menuPrompt += "\n";
                
        String[] menuOptions = {"Return"};
        USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
    }
    
    private void creditsAndInfoMenu() {
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
