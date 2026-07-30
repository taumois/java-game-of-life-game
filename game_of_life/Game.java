
import java.util.Scanner;

/**
 * 
 */
public class Game {
    private UserInterface userInterface;
    private Grid grid;
    private Grid defaultGrid;
    private UserInterface defaultUserInterface;
    private boolean highConstrastEnabled;
    private boolean usingBorderedGrid;
    private boolean usingBigGrid;
    private boolean running;
    
    Game(UserInterface userInterface, Grid grid) {
        this.userInterface = userInterface;
        this.grid = grid;
        this.defaultUserInterface = this.userInterface;
        this.defaultGrid = this.grid;
        
        this.highConstrastEnabled = false;
        this.usingBorderedGrid = false;
        this.usingBigGrid = false;
        
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
            userInterface.createInputMenu(menuPrompt, menuOptions);
            
            int option = userInterface.numberOfLastSelectedOptionByUser();
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
            userInterface.createInputMenu(menuPrompt, menuOptions);
            
            int option = userInterface.numberOfLastSelectedOptionByUser();
            switch(option) {
                case 0: // Next Generation
                    grid.stepForwardGenerations(1);
                    userInterface.updateGrid(grid.cells());
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
                    "Current Save Code",
                    "Curated Save Codes",
                    "Load Save",
                    "Return"
            };
            userInterface.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = userInterface.numberOfLastSelectedOptionByUser();
        switch(option) {
            case 0: // Advance 'x' Gen's
                {
                    userInterface.createInputRangeMenu("Enter the number of gens to advance(1-999)", 1, 999);
                    int x = userInterface.numberOfLastSelectedOptionByUser();
                    grid.stepForwardGenerations(x);
                }
                userInterface.updateGrid(grid.cells());
                break;
            case 1: // Toggle a Cell
                {
                    Cell[][] cells = grid.cells();
                    
                    int width = cells[0].length;
                    userInterface.createInputRangeMenu("Please enter a column no. ("+1+"-"+width+")", 1, width);
                    int x = userInterface.numberOfLastSelectedOptionByUser() - 1;
                    
                    int height = cells.length;
                    userInterface.createInputRangeMenu("Please enter a row no. ("+1+"-"+height+")", 1, height);
                    int y = userInterface.numberOfLastSelectedOptionByUser() - 1;
                    
                    cells[y][x] = cells[y][x] == Cell.ALIVE ? Cell.DEAD : Cell.ALIVE;
                }
                userInterface.updateGrid(grid.cells());
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
                userInterface.updateGrid(grid.cells());
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
                userInterface.updateGrid(grid.cells());
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
                userInterface.updateGrid(grid.cells());
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
                userInterface.updateGrid(grid.cells());
                break;
            case 6: // Current Save Code
                currentSaveCodeMenu();
                break;
            case 7: // Currated Save Codes
                curatedSaveCodesMenu();
                break;
            case 8: // Load Save
                loadSaveMenu();
                break;
            case 9: // Return
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
            userInterface.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = userInterface.numberOfLastSelectedOptionByUser();
        switch(option) {
            case  0:
                {
                    String code = userInterface.stringInput(
                    "Paste the save code now; \n" +
                    "(general)warning, if the code is invalid, nothing will happen; \n" +
                    "(technical)warning, the loaded grid will be unbordered & use the default ruleset. ").trim();
                    
                    Scanner codeParser = new Scanner(code);
                    codeParser.useDelimiter("a|\\n");
                    try {
                        this.grid = new UnborderedGrid(CellRuler.defaultLifeRulesetRuler(), codeParser.nextInt(), codeParser.nextInt());
                        for(int row=0;row<grid.cells().length;row++) {
                            for(int column=0;column<grid.cells()[0].length;column++) {
                                grid.cells()[row][column] = codeParser.nextInt() == 1 ? Cell.ALIVE : Cell.DEAD;
                            }
                        }
                        userInterface.updateGrid(grid.cells());
                    } catch(RuntimeException exception) {}
                }
            case 1:
                break;
            default:
                throw new RuntimeException();
        }
    }

    private void currentSaveCodeMenu() {
    
        String menuPrompt = 
                "You may copy the code below; it can be pasted \n" +
                "into the 'Load Game' menu to reload \n" +
                "your current grid/save. \n " +
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
        userInterface.createInputMenu(menuPrompt, menuOptions);
    }

    private void curatedSaveCodesMenu() {
    
        String menuPrompt = 
                "Curated codes; each block can be pasted \n" +
                "into the 'Load Game' menu to load it in \n" +
                "\n" +
                "5a5a0a0a1a0a0a\n" +
                "0a1a1a1a0a\n" +
                "0a0a1a0a0a\n" +
                "\n" +
                "25a20a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" + 
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" + 
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a1a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a\n" +
                "\n" +
                "50a40a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a1a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a1a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a1a0a0a1a0a0a0a0a0a0a0a0a0a0a0a1a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a1a1a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a0a\n" +
                "\n";

                
        String[] menuOptions = {"Return"};
        userInterface.createInputMenu(menuPrompt, menuOptions);
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
        userInterface.createInputMenu(menuPrompt, menuOptions);
    }
    
    private void settingsMenu() {
        {
            String menuPrompt = 
                    "Settings \n" +
                    "Note: switching to use a grid will reset the cells, if you care about them";
            String[] menuOptions = {"Toggle High Contrast Cells", "Use Standard Grid", "Use Bordered Grid", "Use Huge Grid", "Use Diamoeba Grid", "Return"};
            userInterface.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = userInterface.numberOfLastSelectedOptionByUser();
        switch(option) {
            case 0: // Toggle High Contrast Cells
                if(!highConstrastEnabled) {
                    userInterface.useHighContrastCellSymbols();
                } else {
                    userInterface.useDefaultCellSymbols();
                }
                highConstrastEnabled = !highConstrastEnabled;
                userInterface.updateGrid(grid.cells());
                break;
            case 1: // Use Standard Grid
                grid = GridFactory.standard();
                userInterface.updateGrid(grid.cells());
                break;
            case 2: // Use Bordered Grid
                grid = GridFactory.bordered();
                userInterface.updateGrid(grid.cells());;
                break;
            case 3: // Use Huge Grid
                grid = GridFactory.huge();
                userInterface.updateGrid(grid.cells());
                break;
            case 4: // Use Diamoeba
                grid = GridFactory.diamoeba();
                userInterface.updateGrid(grid.cells());
                break;
            case 5: // Return
                break;
            default:
                throw new RuntimeException();
        }
    }
    
    private void exitMenu() {
        {
            String menuPrompt = 
                    "Please confirm if you wish to exit the program? ";
            String[] menuOptions = {"Confirm; Exit Program", "Return"};
            userInterface.createInputMenu(menuPrompt, menuOptions);
        }
        
        int option = userInterface.numberOfLastSelectedOptionByUser();
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
