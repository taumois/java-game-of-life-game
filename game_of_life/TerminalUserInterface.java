
import java.util.Scanner;

/**
 * User Interface.
 */
class TerminalUserInterface implements UserInterface {
    private static final char UNICODE_CLEAR_SCREEN_COMMAND = '\u000C';
    private static final char ALIVE_CELL_SYMBOL = 'W';
    private static final char DEAD_CELL_SYMBOL = '`';
    private final Scanner SCANNER = new Scanner(System.in);
    private char[] gridToDisplay;
    private boolean isDisplayingPrompt;
    private String promptToDisplay;
    private int lastRecievedInput;
    
    /**
     * Constructor for objects of class UI
     */
    TerminalUserInterface() {
        
    }
    
    /**
     * Update the grid displayed to the user.
     * 
     * @param grid the new grid to replace the displayed with
     */
    public void updateGrid(Cell[][] grid) {
        gridToDisplay = printableBufferFromGrid(grid);
        clearTerminal();
        System.out.print(gridToDisplay);
    }
    
    /**
     * Create and display a menu to display to the user for them to choose from.
     * 
     * @param prompt a prompt for the menu
     * @param options the array of options for the user to select from after looking at the menu's prompt
     */
    public void createInputMenu(String prompt, String[] options) {
        System.out.println(prompt);
        lastRecievedInput = intInRangeInput(1, 3);
        System.out.println("Option #"+lastRecievedInput+" selected. ");
    }
    
    private int intInRangeInput(int lowerBound, int upperBound) {
        assert(upperBound >= lowerBound);
        int intInRange = intInput();
        while(intInRange < lowerBound || intInRange > upperBound) {
            System.out.println("Number must fall within the range of "+lowerBound+" to "+upperBound+" (inclusive). ");
            intInRange = intInput();
        }
        return intInRange;
    }
    
    private int intInput() {
        while(!SCANNER.hasNextInt()) {
            System.out.println("Input must be an integer/whole number.  ");
            SCANNER.next();
        }
        return SCANNER.nextInt();
    }
    
    /**
     * Be returned the index of the option the player last chose in a input menu
     * 
     * @return the index
     */
    public int lastRecievedInput() {
        return lastRecievedInput;
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
