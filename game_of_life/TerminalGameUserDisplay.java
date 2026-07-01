
/**
 * Can display everything necessary for a game via the terminal
 */
class TerminalGameUserDisplay implements GameUserDisplay {
    private static final char UNICODE_CLEAR_SCREEN_COMMAND = '\u000C';
    private static final char ALIVE_CELL_SYMBOL = 'W';
    private static final char DEAD_CELL_SYMBOL = '`';
    
    private char[] printedContent;
    
    /**
     * Constructor for objects of class TextUserDisplay
     */
    TerminalGameUserDisplay(){
        //
    }
    
    /**
     * Updates the displayed grid with a new one
     *
     * @param grid the new grid
     */
    public void updateGrid(Cell[][] grid) {
        char[] displayBuffer = printableBufferFromGrid(grid);
        clearTerminal();
        recordAndPrint(displayBuffer);
    }
    
    public void recordAndPrint(char[] content) {
        System.out.print(content);
        printedContent = content;
    }
    
    /**
     * Displays a prompt for the user
     *
     * @param the prompt
     */
    public void promptUserForAction(String prompt) {
        System.out.println("=========================");
        System.out.println(prompt);
    }
    
    /**
     * Clears the display, i.e. terminal
     */
    private void clearTerminal() {
        System.out.print(UNICODE_CLEAR_SCREEN_COMMAND);
    }
    
    /**
     * Returns a ready to print buffer of a given grid's cell representations
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
     * Returns a representation, to be used in the terminal, for a given cell based on its state
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
