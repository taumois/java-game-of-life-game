
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
    private Input lastRecievedInput;
    
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
    
    public void createInputPrompt(String prompt, InputType type) {
        switch(type) {
            case BOOLEAN:
                System.out.println(prompt + "(TRUE/FALSE)");
                //lastRecievedInput =  SCANNER.nextBoolean().getClass();
                break;
            case INT:
                break;
            case FLOAT:
                break;
            case STRING:
                break;
            default:
                throw new IllegalArgumentException("Unexpected input prompt type.");
        }
    }
     
    public Input lastRecievedInput() {
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
