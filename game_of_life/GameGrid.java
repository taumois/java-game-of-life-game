
/**
 * A grid responsible for holding all the cells for a game of Life.
 */
public interface GameGrid {
    
    /**
     * A complete 2d array of this object's cell's states. The 1st dimension is row #; the 2nd dimension is column #
     * 
     * @return the 2d array of states
     */
    public Cell[][] cells();
    
    /**
     * The value of a specified cell
     * 
     * @param column the column of the target cell
     * @param row    the row of the target cell
     */
    public Cell cell(int column, int row);
    
    /**
     * Sets the state of a specified cell within the buffer
     * 
     * @param column the column of the target cell
     * @param row    the row of the target cell
     * @param state  the state to set the target cell in the buffer
     */
    public void setBufferCell(int column, int row, Cell state);
    
    /**
     * 'Pushes' the buffer onto the grid. I.e. Overrides the grid such that it is identicle to the buffer
     */
    public void pushBuffer();
    
    /**
     * The number of alive neighbors the cell at the target position has in the non-buffer grid
     * 
     * @param column the column of the target cell
     * @param row    the row of the target cell
     */
    public int neighborNumberOfCell(int column, int row);
}
