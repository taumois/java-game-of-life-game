
/**
 * ConwaysGameOfLife here.
 *
 * @author Isaiah Taumoepeau
 */
public class ConwaysGameOfLife {
    private final GameUI UI;
    private final GameGrid GRID;
    
    ConwaysGameOfLife(GameUI ui, GameGrid grid) {
        this.UI = ui;
        this.GRID = grid;
    }
    
    void display() {
        foo();
        UI.updateDisplay(GRID.cells());
    }
    
    void foo() {
        int width = GRID.width();
        int height = GRID.height();
        
        
        for(int row=0;row<height;row++) {
            for(int column=0;column<width;column++) {
               int no = GRID.cellsNumberOfLivingNeighbors(column, row);
               GRID.setCellsState(column, row, GRID.cell(column, row) == Cell.ALIVE ? Cell.DEAD : Cell.ALIVE);
            }
        }
    }
}
