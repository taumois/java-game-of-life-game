
import java.util.Scanner;

/**
 * User Interface.
 */
class TerminalUserInterface implements UserInterface {
    private static final char UNICODE_CLEAR_SCREEN_COMMAND = '\u000C';
    private static final char DEFAULT_ALIVE_CELL_SYMBOL = '@';
    private static final char DEFAULT_DEAD_CELL_SYMBOL = '`';
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
     * Constructor for objects of class UI
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
     * Update the grid displayed to the user.
     * 
     * @param grid the new grid to replace the displayed with
     */
    public void updateGrid(Cell[][] grid) {
        gridToDisplay = charGridFromCellGrid(grid);
        /*
         * gridToDisplay E.g.
         * 
         */
        refresh();
    }
    
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
     * Create and display a menu to display to the user for them to choose from.
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
        /* 
         * menuToDisplay E.g. 
         * "How are you?
         * 1) - Well
         * 2) - Unwell"
         */ 

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
     * 
     */
    public void createInputRangeMenu(String prompt, int lowerBound, int upperBound) {
        menuToDisplay = prompt;

        refresh();
        
        numberOfLastSelectedOption = intInRangeInput("Please enter a valid whole number within the range", lowerBound, upperBound);
    }
    
    /**
     * 
     */
    public String stringInput(String prompt) {
        menuToDisplay = prompt;

        refresh();
        
        return safeInput();
    }
    
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
    
    private int intInput(String inputRequirementsMessage) {
        while(!scanner.hasNextInt()) {
            System.out.println(inputRequirementsMessage);
            safeInput();
        }
        return scanner.nextInt();
    }
    
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
    
    public void useCellSymbols(char aliveCellSymbol, char deadCellSymbol) {
        this.aliveCellSymbol = aliveCellSymbol;
        this.deadCellSymbol = deadCellSymbol;
    }
}
