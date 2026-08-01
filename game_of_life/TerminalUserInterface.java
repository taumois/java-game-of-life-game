
import java.util.Scanner;

/**
 * The UserInterface deals with the UI:
 * Input handling
 * Displaying info
 * 
 * This is an implementation of 'UserInterface' that uses the terminal
 */
class TerminalUserInterface implements UserInterface {
    private static final char UNICODE_CLEAR_SCREEN_COMMAND = '\u000C';
    private static final char DEFAULT_ALIVE_CELL_SYMBOL = '@';
    private static final char DEFAULT_DEAD_CELL_SYMBOL = '`';
    private static final char HIGH_CONTRAST_ALIVE_CELL_SYMBOL = 'O';
    private static final char HIGH_CONTRAST_DEAD_CELL_SYMBOL = '\u25A0';
    private static final String DELIMITER_REGEX = "-|\\n";
    
    private char aliveCellSymbol;
    private char deadCellSymbol;
    private Scanner scanner;
    private boolean isDisplayingPrompt;
    private String gridToDisplay;
    private String menuToDisplay;
    private int numberOfLastSelectedOption;
    private String lastSelectedOptionText;
    
    /**
     * Constructor for objects of class TerminalUserInterface
     */
    TerminalUserInterface() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter(DELIMITER_REGEX);
        gridToDisplay = "";
        menuToDisplay = "";
        lastSelectedOptionText = "";
        aliveCellSymbol = DEFAULT_ALIVE_CELL_SYMBOL;
        deadCellSymbol = DEFAULT_DEAD_CELL_SYMBOL;
    }
    
    /**
     * Update the grid displayed to the user
     * 
     * @param grid the new grid to replace the displayed with
     */
    public void updateGrid(Cell[][] grid) {
        gridToDisplay = charGridFromCellGrid(grid);
        
        refresh();
    }
    
    /**
     * Refresh what is displayed to the user in the terminal with the updated info from the other methods
     */
    private void refresh() {
        String display = 
                UNICODE_CLEAR_SCREEN_COMMAND +
                lastSelectedOptionText + "\n" +
                "========" + "\n" +
                gridToDisplay +
                "========" + "\n" +
                menuToDisplay;

        System.out.println(display);
    }
    
    /**
     * Create and display a menu to display to the user for them to choose from
     * 
     * @param prompt a prompt for the menu
     * @param options the array of options for the user to select from after looking at the menu's prompt
     */
    public void createInputMenu(String prompt, String[] options) {
        String menu = prompt;
        for(int n=0;n<options.length;n++) {
            menu = menu + "\n"+(n+1)+") - "+options[n];
            // E.g. "1) - Well"
        }
        menuToDisplay = menu;
        
        refresh();
        
        numberOfLastSelectedOption = 
                intInRangeInput("Please enter a number picked from one of the options \n" +
                "[E.g. you could type and enter '1', to select menu item '1)']",
                1, 
                options.length) - 1;
        
        lastSelectedOptionText =
                "Option #"+(numberOfLastSelectedOption+1)+": " +
                options[numberOfLastSelectedOption]+" was selected. ";
    }
    
    /**
     * Create and display a prompt for the user to get an input in range of two numbers
     * Range is inclusive for bounding numbers.
     * 
     * @param prompt a prompt for the menu
     * @param upperBound a lower bound
     * @param lowerBound a upper bound
     */
    public void createInputRangeMenu(String prompt, int lowerBound, int upperBound) {
        menuToDisplay = prompt;

        refresh();
        
        numberOfLastSelectedOption = intInRangeInput("Please enter a valid whole number within the range", lowerBound, upperBound);
    }
    
    /**
     * Create and display a menu to display to the user for them to choose from
     * 
     * @param prompt a prompt for the menu
     */
    public String stringInput(String prompt) {
        menuToDisplay = prompt;

        refresh();
        
        return safeInput();
    }
    
    /**
     * Returns an integer within a specified range
     * range is inclusive of its specified bounds
     * 
     * @param  inputRequirementsMessage a message/prompt informing the user they need to input a number(should also inform them of the range)
     * @param  lowerBound               the maximum value of the range
     * @param  upperBound               the minimum value of the range
     * @return                          the number the user enters after
     */
    private int intInRangeInput(String inputRequirementsMessage, int lowerBound, int upperBound) {
        assert(upperBound >= lowerBound);
        int intInRange;
        intInRange = intInput(inputRequirementsMessage);
        while(intInRange < lowerBound || intInRange > upperBound) {
            System.out.println(inputRequirementsMessage);
            intInRange = intInput(inputRequirementsMessage);
        }
        return intInRange;
    }
    
    /**
     * A safe(i.e. doesn't break or return an error) alternative to 'Scanner.nextInt()'
     * 
     * @param  inputRequirementsMessage a message informing the user both they need to input & that input needs to be an int
     * @return                          the int input recieved from the user
     */
    private int intInput(String inputRequirementsMessage) {
        while(!scanner.hasNextInt()) {
            System.out.println(inputRequirementsMessage);
            safeInput();
        }
        return scanner.nextInt();
    }
    
    /**
     * Made as an alternative to 'Scanner.next()', that's identical, except, it doesn't break from "Ctrl+Z" in the terminal
     */
    private String safeInput() {
        String input = null;
        while(input == null) {
            try {
                input = scanner.next();
            } catch(java.util.NoSuchElementException exception) {
                scanner = new Scanner(System.in);
                scanner.useDelimiter(DELIMITER_REGEX);
            }
        }
        return input;
    }
    
    /**
     * Be returned the number of the option the player last chose in a input menu
     * 
     * @return the number
     */
    public int numberOfLastSelectedOptionByUser() {
        return numberOfLastSelectedOption;
    }
    
    /**
     * Clear the terminal
     */
    private void clearTerminal() {
        System.out.print(UNICODE_CLEAR_SCREEN_COMMAND);
    }
    
    /**
     * Return a ready to print buffer of a given grid's cell representations
     * 
     * @param the grid to get the cells from
     */
    private String charGridFromCellGrid(Cell[][] grid) {
        char[] symbolBuffer = new char[grid.length * (grid[0].length + 1)];
        
        for(int row=0;row<grid.length;row++) {
            for(int column=0;column<grid[row].length;column++) {
                Cell cell = grid[row][column];
                char cellSymbol = charRepresentationFromCell(cell);
                
                int index = row * grid[row].length + column + row;
                symbolBuffer[index] = cellSymbol;
            }
            int index = row * grid[row].length + grid[row].length + row;
            symbolBuffer[index] = '\n';
        }
        
        return String.copyValueOf(symbolBuffer);
    }
    
    /**
     * Return a representation, to be used in the terminal, for a specified cell dependant on if it's alive or not
     *
     * @param  the cell
     * @return the representation
     */
    private char charRepresentationFromCell(Cell cell) {
        if(cell == Cell.ALIVE) {
            return aliveCellSymbol;
        } else {
            return deadCellSymbol;
        }
    }
    
    /**
     * Switch to default grid display mode
     */
    public void useDefaultCellSymbols() {
        this.aliveCellSymbol = DEFAULT_ALIVE_CELL_SYMBOL;
        this.deadCellSymbol = DEFAULT_DEAD_CELL_SYMBOL;
    }
    
    /**
     * Switch to high contrast grid display mode
     */
    public void useHighContrastCellSymbols() {
        this.aliveCellSymbol = HIGH_CONTRAST_ALIVE_CELL_SYMBOL;
        this.deadCellSymbol = HIGH_CONTRAST_DEAD_CELL_SYMBOL;
    }
}
