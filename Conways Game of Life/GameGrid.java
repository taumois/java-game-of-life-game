
/**
 * GameGrid, a class to hold the state of the game, including every cell. Cells are booleans, where true = alive/false = dead
 *
 * @author Isaiah Taumoepeau
 */
class GameGrid
{
    private final int WIDTH;
    private final int HEIGHT;
    
    private boolean cells[];

    /**
     * Constructor for objects of class GameGrid
     * 
     * @param  width  width to make the game grid
     * @param  height  height to make the game grid
     */
    GameGrid(int width, int height)
    {
        this.WIDTH = width;
        this.HEIGHT = height;
        cells = new boolean[WIDTH * HEIGHT];
    }
    
    /**
     * Returns the cell at the specified coordinate
     *
     * @param  x  x-coordinate
     * @param  y  y-coordinate
     * @return    the cell
     */
    boolean cell(int x, int y) {
        int index = cellIndexFromCoordinate(x, y);
        return cell(index);
    }
    
    /**
     * Returns the cell of the specified index
     *
     * @param  index  self explanatory
     * @return    the cell
     */
    boolean cell(int index) {
        boolean cell = cells[index];
        return cell;
    }
    
    /**
     * Returns the index of where you'd find a cell(in the cells field) with a given coordinate
     *
     * @param  x  x-coordinate
     * @param  y  y-coordinate
     * @return    the index
     */
    private int cellIndexFromCoordinate(int x, int y) {
        int index = y * WIDTH + x;
        return index;
    }
}
