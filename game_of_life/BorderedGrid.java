
import java.util.Random;

/**
 * A bordered grid responsible for holding all the cells for a game of Life.
 */
class BorderedGrid implements Grid {
    private static final byte NEIGHBORS_PER_CELL = 8;
    
    private CellRuler cellRuler;
    private final int QUANTITY_OF_COLUMNS;
    private final int QUANTITY_OF_ROWS;
    private Cell[][] cells;
    private Cell[][] bufferCells;
    
    /**
     * Contructor B for objects of class BorderedGrid 
     * 
     * @param cells     the cells to fill the created grid width. Note that the created grid's dimensions will be determined by that of this array
     * @param cellRuler @see CellRuler TLDR: it determines this's ruleset
     */
    BorderedGrid(Cell[][] cells, CellRuler cellRuler) {
        this.QUANTITY_OF_COLUMNS = cells[0].length;
        this.QUANTITY_OF_ROWS = cells.length;
        
        this.cellRuler = cellRuler;
        
        this.cells = new Cell[QUANTITY_OF_ROWS][QUANTITY_OF_COLUMNS];
        this.bufferCells = new Cell[QUANTITY_OF_ROWS][QUANTITY_OF_COLUMNS];
        for(int row=0;row<bufferCells.length;row++) {
            for(int column=0;column<bufferCells[row].length;column++) {
                bufferCells[row][column] = cells[row][column];
            }
        }
        pushBuffer();
    }
    
    /**
     * Constructor A for objects of class BorderedGrid.
     * 
     * Note that the cells in grids generated with this constructor will be filled with a random assortment of alive and dead by default
     * 
     * @param cellRuler @see CellRuler TLDR: it determines this's ruleset
     * @param width     the # of rows to give the grid
     * @param height    the # of columns to give the grid
     */
    BorderedGrid(CellRuler cellRuler, int width, int height) {
        this.QUANTITY_OF_COLUMNS = width;
        this.QUANTITY_OF_ROWS = height;
        
        this.cellRuler = cellRuler;
        
        randomiseCells();
    }
    
    /**
     * Randomizes the state of every cell in both grids with a coinflip essentially
     */
    private void randomiseCells() {
        cells = new Cell[QUANTITY_OF_ROWS][QUANTITY_OF_COLUMNS];
        bufferCells = new Cell[QUANTITY_OF_ROWS][QUANTITY_OF_COLUMNS];
        for(int row=0;row<bufferCells.length;row++) {
            for(int column=0;column<bufferCells[row].length;column++) {
                bufferCells[row][column] = Math.random() > 0.5 ? Cell.DEAD : Cell.ALIVE;
            }
        }
        pushBuffer();
    }
    
    /**
     * Replaces the cell cellruler/ruleset which this class uses
     * 
     * @see CellRuler
     */
    public void useCellRuler(CellRuler cellRuler) {
        this.cellRuler = cellRuler;
    }
    
    /**
     * Returns a reference to the 2d array used to store this grids cells
     * The 1st dimension is row #; the 2nd dimension is column #
     * 
     * @return the cells
     */
    public Cell[][] cells() {
        return cells;
    }
    
    /**
     * Repeatedly step forwards a generation the specified amount of times
     * 
     * @param NumberOfGenerations the specified amount of times number
     */
    public void stepForwardGenerations(int numberOfGenerations) {
        for(int i=0;i<numberOfGenerations;i++) {
            stepForwardAGeneration();
        }
    }
    
    /**
     * Step forward one generation
     */
    private void stepForwardAGeneration() {
        
        for(int row=0;row<QUANTITY_OF_ROWS;row++) {
            for(int column=0;column<QUANTITY_OF_COLUMNS;column++) {
                Cell cell = cells[row][column];
                int cellsNumberOfLivingNeighbors = neighborNumberOfCell(column, row);
                Cell nextStateOfCell = cellRuler.rulingFromNeighborsForCell(cell, cellsNumberOfLivingNeighbors);
                
                bufferCells[row][column] = nextStateOfCell;
            }
        }
        
        pushBuffer();
    }
    
    /**
     * 'Pushes' the buffer onto the grid. I.e. Overrides the grid such that it is identicle to the buffer
     */
    private void pushBuffer() {
        for(int rowIndex = 0;rowIndex<cells.length;rowIndex++) {
            for(int columnIndex = 0;columnIndex<cells[rowIndex].length;columnIndex++) {
                cells[rowIndex][columnIndex] = bufferCells[rowIndex][columnIndex];
            }
        }
    }
    
    /**
     * The number of alive neighbors the cell at the target position has in the non-buffer grid
     * 
     * @param  column the column of the target cell
     * @param  row    the row of the target cell
     * @return        how many ALIVE neighbors the cell at the specified coordinate has
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
     * @param  column       a cells column
     * @param  cell         that same cells row
     * @param  columnOffset the offset to be applied to the passed in cell's column to find the target cells column
     * @param  rowOffset    what columnOffset said but with rows
     * @return              true if the cell meets those condition mentioned at the top of this comment 
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
            return true;
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
     * Be returned a boolean representing if a specified column exists here
     * 
     * @param  column the column number
     * @return        a bool that's only true if the column exists
     */
    private boolean hasColumn(int column) {
        if((column < QUANTITY_OF_COLUMNS) && (column >= 0)) {
            return true;
        }
        return false;
    }
    
    /**
     * Be returned a boolean representing if a specified row exists here
     * 
     * @param  row the row number
     * @return     a bool that's only true if the row exists
     */
    private boolean hasRow(int row) {
        if((row < QUANTITY_OF_ROWS) && (row >= 0)) {
            return true;
        }
        return false;
    }
}
