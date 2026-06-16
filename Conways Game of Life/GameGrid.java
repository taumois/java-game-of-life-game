
import java.util.Random;

/**
 * Objects of GameGrid holds the state of every cell for a game of Conway's Game of Life.
 */
class GameGrid {
    private static final byte NEIGHBORS_PER_CELL = 8;
    
    private final int WIDTH;
    private final int HEIGHT;
    private Cell[][] CELLS;

    /**
     * Constructor for objects of class GameGrid
     * 
     * @param width  width to make the grid
     * @param height height to make the grid
     */
    GameGrid(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        
        CELLS = new Cell[HEIGHT][WIDTH];
        for(int row=0;row<CELLS.length;row++) {
            for(int column=0;column<CELLS[row].length;column++) {
                CELLS[row][column] = Math.random() > 0.5 ? Cell.DEAD : Cell.ALIVE;
            }
        }
    }
    
    int width() {
        return this.WIDTH;
    }
    
    int height() {
        return this.HEIGHT;
    }
    
    /**
     * Returns an array with the state of all cells
     *
     * @return all the cells
     */
    Cell[][] cells() {
        return CELLS;
    }
    
    /**
     * Returns the state of a specified cell
     *
     * @param cellX x-coordinate of the target cell
     * @param cellY y-coordinate of the target cell
     * @return      the cell
     */
    Cell cell(int column, int row) {
        Cell cell = CELLS[row][column];
        return cell;
    }
    
    /**
     * Sets the state of a specified cell. Either alive or dead(i.e. true or false)
     *
     * @param cellX x-coordinate of the target cell
     * @param cellY y-coordinate of the target cell
     * @param state state to change the cell to
     * 
     * @see 
     */
    void setCellsState(int cellsColumn, int cellsRow, Cell state) {
        CELLS[cellsRow][cellsColumn] = state;
    }
    
    void setCellStates(Cell[][] states) {
        for(int row=0;row<CELLS.length;row++) {
            for(int column=0;column<CELLS[row].length;column++) {
                CELLS[row][column] = states[column][row];
            }
        }
    }
    
    /**
     * Returns the neighbor of alive neighbors a specified cell has. A CELLS neighbors are the CELLS adjacent to it, including those diagonally adjacent
     *
     * @param  cellX x-coordinate of the target cell
     * @param  cellY y-coordinate of the target cell
     * @return the neighbors
     */
    int cellsNumberOfLivingNeighbors(int cellsColumn, int cellsRow) {
        int neighbors = 0;
        
        for(int rowOffset=-1;rowOffset<=1;rowOffset++) {
            for(int columnOffset=-1;columnOffset<=1;columnOffset++) {
                boolean isNeighbor;
                isNeighbor = OffsetCellIsLivingNeighbor(cellsColumn, cellsRow, columnOffset, rowOffset);
                neighbors += isNeighbor ? 1 : 0;
            }
        }
        return neighbors;
    }
    
    private boolean OffsetCellIsLivingNeighbor(int cellsColumn, int cellsRow, int columnOffset, int rowOffset) {
        Cell cell;
        boolean cellIsNotNeighbor;
        boolean cellIsDead;
        int targetColumn;
        int targetRow;
        boolean coordinateIsInvalid;
        
        targetColumn = cellsColumn + columnOffset;
        targetRow = cellsRow + rowOffset;
        coordinateIsInvalid = (!hasColumn(targetColumn) || !hasRow(targetRow));
        if(coordinateIsInvalid) {
            return false;
        }
        
        cell = cell(targetColumn, targetRow);
        cellIsNotNeighbor = ((columnOffset == 0) && (rowOffset == 0));
        if(cellIsNotNeighbor) {
            return false;
        }
        
        cellIsDead = (cell == Cell.DEAD);
        if(cellIsDead) {
            return false;
        }
        return true;
    }
    
    private boolean hasColumn(int column) {
        if((column < WIDTH) && (column >= 0)) {
            return true;
        }
        return false;
    }
    
    private boolean hasRow(int row) {
        if((row < HEIGHT) && (row >= 0)) {
            return true;
        }
        return false;
    }
}
