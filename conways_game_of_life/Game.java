
/**
 * ConwaysGameOfLife here.
 */
public class Game {
    private static final int MINIMUM_LIVE_NEIGHBORS_FOR_SURVIVAL = 2;
    private static final int MAXIMUM_LIVE_NEIGHBORS_FOR_SURVIVAL = 3;
    private static final int LIVE_NEIGHBORS_FOR_REPRODUCTION = 3;
    
    private final GameUI UI;
    private final GameGrid GRID;
    
    Game(GameUI ui, GameGrid grid) {
        this.UI = ui;
        this.GRID = grid;
    }
    
    static Game terminalUIGame() {
        return new Game(GameUI.terminalGameUI(), new GameGrid(40,60));
    }
    
    void play() {
        UI.updateDisplay(GRID.cells());
        while(true) {
            step();
        }
    }
    
    private void step() {
        foo();
        UI.updateDisplay(GRID.cells());
        UI.askUserForAction("Hello World!");
    }
    
    private void foo() {
        int width = GRID.width();
        int height = GRID.height();
        Cell[][] cellStatesBuffer = new Cell[GRID.width()][GRID.height()];
        
        for(int row=0;row<height;row++) {
            for(int column=0;column<width;column++) {
                Cell cell = GRID.cell(column, row);
                int cellsNumberOfLivingNeighbors = GRID.cellsNumberOfLivingNeighbors(column, row);
                Cell cellsNextState = cellStateFromNumberOfLivingNeighbors(cell, cellsNumberOfLivingNeighbors);
                
                cellStatesBuffer[column][row] = cellsNextState;
            }
        }
        
        GRID.setCellStates(cellStatesBuffer);
    }
    
    private Cell cellStateFromNumberOfLivingNeighbors(Cell state, int numberOfLivingNeighbors) {
        if(state == Cell.DEAD) {
            if(numberOfLivingNeighbors == LIVE_NEIGHBORS_FOR_REPRODUCTION) {
                return Cell.ALIVE;
            }
            return Cell.DEAD;
        }
        
        boolean willUnderpopulate = numberOfLivingNeighbors < MINIMUM_LIVE_NEIGHBORS_FOR_SURVIVAL;
        if(willUnderpopulate) { 
            return Cell.DEAD; 
        }
        boolean willOverpopulate = numberOfLivingNeighbors > MAXIMUM_LIVE_NEIGHBORS_FOR_SURVIVAL;
        if(willOverpopulate) { 
            return Cell.DEAD; 
        }
        return Cell.ALIVE;
    }
}
