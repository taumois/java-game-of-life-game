
/**
 * TextUserDisplay here.
 *
 * @author Isaiah Taumoepeau
 */
class TextUserDisplay implements UserDisplay {
    private static final char ALIVE_CELL_SYMBOL = 'W';
    private static final char DEAD_CELL_SYMBOL = ' ';
    
    /**
     * Constructor for objects of class TextUserDisplay
     */
    TextUserDisplay(){
        //
    }
    
    /**
     * Displays a grid of cells using text
     *
     * @param grid the grid to display
     */
    public void display(Cell[][] grid) {
        for(int row=0;row<grid.length;row++) {
            for(int column=0;column<grid[row].length;column++) {
                char cellSymbol;
                
                Cell cell = grid[row][column];
                cellSymbol = symbolFromCell(cell);
                
                System.out.print(cellSymbol);
            }
            System.out.println();
        }
    }
    
    /**
     * Returns the corrosponding symbol of a cell, which depends on if it's alive
     *
     * @param  the cell
     * @return the symbol
     */
    private char symbolFromCell(Cell cell) {
        if(cell == Cell.ALIVE) {
            return ALIVE_CELL_SYMBOL;
        } else {
            return DEAD_CELL_SYMBOL;
        }
    }
}
