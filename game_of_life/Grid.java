 
/**
 * Represents the grid of cells that Game of Life is played on
 */
public interface Grid {
    
    /**
     * Returns a reference to the 2d array used to store this grids cells
     * The 1st dimension is row #; the 2nd dimension is column #
     * 
     * @return the cells
     */
    public Cell[][] cells();
    
    /**
     * Repeatedly step forwards a generation the specified amount of times
     * 
     * @param NumberOfGenerations the specified amount of times number
     */
    public void stepForwardGenerations(int NumberOfgenerations);
    
    /**
     * Replaces the cell cellruler/ruleset which this class uses
     * 
     * @see CellRuler
     */
    public void useCellRuler(CellRuler cellRuler);
}
