
import java.util.Scanner;

/**
 * User Interface.
 */
class TerminalUserInterface implements UserInterface {
    private static final char UNICODE_CLEAR_SCREEN_COMMAND = '\u000C';
    private static final char ALIVE_CELL_SYMBOL = 'W';
    private static final char DEAD_CELL_SYMBOL = '`';
    
    private Scanner scanner = new Scanner(System.in);
    private boolean isDisplayingPrompt;
    private String promptToDisplay;
    private int indexOfLastSelectedOption;
    private String lastSelectedOption;
    
    /**
     * Constructor for objects of class UI
     */
    TerminalUserInterface() {
        scanner.useDelimiter(",|\\n");
    }
    
    /**
     * Update the grid displayed to the user.
     * 
     * @param grid the new grid to replace the displayed with
     */
    public void updateGrid(Cell[][] grid) {
        char[] gridToDisplay = printableBufferFromGrid(grid);
        clearTerminal();
        System.out.print(gridToDisplay);
        
        System.out.println("Option #"+(indexOfLastSelectedOption+1)+": "+lastSelectedOption+" was selected. ");
        // E.g. Option #2: Unwell was selected. 
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
        clearTerminal();
        System.out.println(menu);
        /* E.g. 
         * "How are you?
         * 1) - Well
         * 2) - Unwell"
         */ 

        indexOfLastSelectedOption = intInRangeInput("Please enter a number picked from one of the options", 1, options.length) - 1;
        lastSelectedOption = options[indexOfLastSelectedOption];
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
                scanner.useDelimiter("\n");
            }
        }
        return input;
    }
    
    /**
     * Be returned the index of the option the player last chose in a input menu
     * 
     * @return the index
     */
    public int indexOflastSelectedOptionByUser() {
        return indexOfLastSelectedOption;
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
    private char[] printableBufferFromGrid(Cell[][] grid) {
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
        
        return symbolBuffer;
    }
    
    /**
     * Return a representation, to be used in the terminal, for a specified cell dependant on if it's alive or not
     *
     * @param  the cell
     * @return the representation
     */
    private char charRepresentationFromCell(Cell cell) {
        if(cell == Cell.ALIVE) {
            return ALIVE_CELL_SYMBOL;
        } else {
            return DEAD_CELL_SYMBOL;
        }
    }
}
