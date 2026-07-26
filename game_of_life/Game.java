
import java.util.Scanner;

/**
 * 
 */
public class Game {
    private final UserInterface USER_INTERFACE;
    private Grid grid;
    
    private boolean highConstrastEnabled;
    private boolean usingBorderedGrid;
    private boolean usingBigGrid;
    private boolean running;
    
    Game(UserInterface userInterface, Grid grid) {
        this.USER_INTERFACE = userInterface;
        this.grid = grid;
        this.highConstrastEnabled = false;
        this.usingBorderedGrid = false;
        this.usingBigGrid = false;
    }
    
    public static Game standardGame() {
        return new Game(new TerminalUserInterface(), new UnborderedGrid(25,20));
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
                    "Randomise Grid",
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
            case 2: // Randomise Grid
                {
                    Cell[][] cells = grid.cells();
                    
                    for(int row=0;row<cells.length;row++) {
                        for(int column=0;column<cells[row].length;column++) {
                            cells[row][column] = Math.random() > 0.5 ? Cell.ALIVE : Cell.DEAD;
                        }
                    }
                }
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 3: // Fill Grid
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
            case 4: // Empty Grid
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
            case 5: // Invert Grid
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
            case 6: // Get Save Code
                getSaveCodeMenu();
                break;
            case 7: // Load Save
                loadSaveMenu();
                break;
            case 8: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
    
    private void loadSaveMenu() {
        {
            String menuPrompt = 
                    "You can get save codes from the 'Get Save Code' menu, \n"+
                    "then keep them safe to load here later." +
                    "\n";
            String[] menuOptions = {"I Have a Code to Use","Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
        switch(option) {
            case  0:
                {
                    String code = USER_INTERFACE.stringInput(
                    "Paste the save code now; \n" +
                    "warning, if the code is invalid, nothing will happen").trim();
                    
                    Scanner codeParser = new Scanner(code);
                    codeParser.useDelimiter("a|\\n");
                    try {
                        this.grid = new UnborderedGrid(codeParser.nextInt(), codeParser.nextInt());
                        for(int row=0;row<grid.cells().length;row++) {
                            for(int column=0;column<grid.cells()[0].length;column++) {
                                grid.cells()[row][column] = codeParser.nextInt() == 1 ? Cell.ALIVE : Cell.DEAD;
                            }
                        }
                        USER_INTERFACE.updateGrid(grid.cells());
                    } catch(java.util.NoSuchElementException exception) {}
                }
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
                "so, if in doubt, just do that; I wouldn't overthink it. \n" +
                "\n" +
                "[Pro tip: You can chain input cmds with dash('-')           ] \n" +
                "[E.g. instead of just entering '1' to leave this menu,      ] \n" +
                "[try entering '1-1-1', to leave this menu AND start playing ] \n" +
                "[AND advance a generation, all in one swift action.         ] \n";
        
        String[] menuOptions = {"Return"};
        USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
    }
    
    private void settingsMenu() {
        {
            String menuPrompt = "Settings";
            String[] menuOptions = {"Toggle High Contrast Cells", "Treat External Cells Alive(bordered grid)", "Return"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = USER_INTERFACE.numberOfLastSelectedOptionByUser();
        switch(option) {
            case 0: // Toggle High Contrast Cells
                if(!highConstrastEnabled) {
                    USER_INTERFACE.useCellSymbols('\u23FA','\u25CB');
                } else {
                    USER_INTERFACE.useCellSymbols('@','`');
                }
                highConstrastEnabled = !highConstrastEnabled;
                USER_INTERFACE.updateGrid(grid.cells());
                break;
            case 1: // Treat External Cells Alive(bordered grid)
                if(!usingBorderedGrid) {
                    grid = new BorderedGrid();
                } else {
                    grid = new UnborderedGrid();
                }
                usingBorderedGrid = !usingBorderedGrid;
                break;
            case 2: // N/A
                break;
            case 3: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
    
    private void exitMenu() {
        {
            String menuPrompt = 
                    "Please confirm that you wish to exit the program? ";
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
