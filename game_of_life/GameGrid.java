
/**
 * A grid responsible for holding all the cells for a game of Life.
 */
public interface GameGrid {
    
    /**
     * A complete 2d array representive of this object's cell's states. The 1st dimension is row #; the 2nd dimension is column #
     * 
     * @return the 2d array of states
     */
    public Cell[][] cells();
    
    /**
     * Change the value of a specified cell
     * 
     * @param column the column of the target cell
     * @param row    the row of the target cell
     * @param value  the value to change to cell to
     */
    public Cell setCell(int column, int row, Cell value);
    
    /**
     * Steps every cell forwards one generation using the rules of Life
     */
    public void step();
}
