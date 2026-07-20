
import java.util.Random;

/**
 * A bordered grid responsible for holding all the cells for a game of Life.
 */
class BorderedVariableSizeGameGrid implements GameGrid {
    private static final byte NEIGHBORS_PER_CELL = 8;
    
    private final int COLUMN_QUANTITY;
    private final int ROW_QUANTITY;
    private Cell[][] cells;
    private Cell[][] bufferCells;
    
    /**
     * Constructor for objects of class GameGrid
     * 
     * @param width  width to make the grid
     * @param height height to make the grid
     */
    BorderedVariableSizeGameGrid(int width, int height) {
        this.COLUMN_QUANTITY = width;
        this.ROW_QUANTITY = height;
        
        cells = new Cell[ROW_QUANTITY][COLUMN_QUANTITY];
        for(int row=0;row<cells.length;row++) {
            for(int column=0;column<cells[row].length;column++) {
                cells[row][column] = Math.random() > 0.5 ? Cell.DEAD : Cell.ALIVE;
            }
        }
    }
    
    /**
     * The 2d array containing all of the cells. The 1st dimension is row #; the 2nd dimension is column #
     * 
     * @return the cells
     */
    public Cell[][] cells() {
        return cells;
    }
    
    /**
     * Step forward a generation the specified amount of times
     * 
     * @param NumberOfGenerations
     */
    public void stepForwardGenerations(int NumberOfgenerations) {
        
    }
    
    /**
     * 'Pushes' the buffer onto the grid. I.e. Overrides the grid such that it is identicle to the buffer
     */
    private void pushBuffer() {
        for(int rowIndex = 0;rowIndex<cells.length;rowIndex++) {
            for(int columnIndex = 0;columnIndex<cells.length;columnIndex++) {
                cells[rowIndex][columnIndex] = bufferCells[rowIndex][columnIndex];
            }
        }
    }
    
    /**
     * The number of alive neighbors the cell at the target position has in the non-buffer grid
     * 
     * @param column the column of the target cell
     * @param row    the row of the target cell
     */
    private int neighborNumberOfCell(int column, int row) {
        int neighbors = 0;
        
        for(int rowOffset=-1;rowOffset<=1;rowOffset++) {
            for(int columnOffset=-1;columnOffset<=1;columnOffset++) {
                boolean isNeighbor;
                isNeighbor = OffsetCellIsLivingNeighbor(column, row, columnOffset, rowOffset);
                neighbors += isNeighbor ? 1 : 0;
            }
        }
        return neighbors;
    }
    
    /**
     * True only if a specified cell meets the following conditions:
     * A) located in a cell that exists
     * B) The offset used in the process of finding the target cell was >= 1
     * C) alive
     * 
     * Created to be used by passing in a cell and offset pair to see if a cell at the offset of the passed in cell is a live neighbor,
     * but for this use it assumes the offset's total length is no more that 1(specifically sqrt(1), 1.414, to include diagonally adjacent neighbors).
     * 
     * @param column       a cell
     * @param cell         a cell
     * @param columnOffset the offset to be applied to the passed in cell's column to find the target cells column
     * @param rowOffset    what columnOffset said but with rows
     */
    private boolean OffsetCellIsLivingNeighbor(int column, int row, int columnOffset, int rowOffset) {
        Cell cell;
        boolean cellIsNotNeighbor;
        boolean cellIsDead;
        int targetColumn;
        int targetRow;
        boolean coordinateIsInvalid;
        
        targetColumn = column + columnOffset;
        targetRow = row + rowOffset;
        coordinateIsInvalid = (!hasColumn(targetColumn) || !hasRow(targetRow));
        if(coordinateIsInvalid) {
            return false;
        }
        
        cell = cells[targetRow][targetColumn];
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
    
    /**
     * The fact of a specified column existing
     * 
     * @param column the column number
     */
    private boolean hasColumn(int column) {
        if((column < COLUMN_QUANTITY) && (column >= 0)) {
            return true;
        }
        return false;
    }
    
    /**
     * The fact of a specified row existing
     * 
     * @param row the row number
     */
    private boolean hasRow(int row) {
        if((row < ROW_QUANTITY) && (row >= 0)) {
            return true;
        }
        return false;
    }
}
