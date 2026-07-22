
/**
 * Rules what the state of a cell should be.
 */
public class LifeAutomataRuler {
    private Cell[] cellShouldSurviveByQuantityOfNeighborsAsIndex = new Cell[9];
    private Cell[] cellShouldBeReproducedByQuantityOfNeighborsAsIndex = new Cell[9];
    
    /**
     * An object of LifeAutomataRuler
     */
    LifeAutomataRuler(int[] quantitiesOfNeighborsToAllowSurvival, int[] quantitiesOfNeighborsToAllowReproduction) {
        for(int cellIndex=0;cellIndex<cellShouldSurviveByQuantityOfNeighborsAsIndex.length;cellIndex++) {
            cellShouldSurviveByQuantityOfNeighborsAsIndex[cellIndex] = Cell.DEAD;
            for(int neighborQuantity: quantitiesOfNeighborsToAllowSurvival) {
                if(cellIndex == neighborQuantity) {
                    cellShouldSurviveByQuantityOfNeighborsAsIndex[cellIndex] = Cell.ALIVE;
                }
            }
        }
        
        for(int cellIndex=0;cellIndex<cellShouldBeReproducedByQuantityOfNeighborsAsIndex.length;cellIndex++) {
            cellShouldBeReproducedByQuantityOfNeighborsAsIndex[cellIndex] = Cell.DEAD;
            for(int neighborQuantity: quantitiesOfNeighborsToAllowReproduction) {
                if(cellIndex == neighborQuantity) {
                    cellShouldBeReproducedByQuantityOfNeighborsAsIndex[cellIndex] = Cell.ALIVE;
                }
            }
        }
    }
    
    /**
     * The ruling on what it thinks a cell should be from the info passed.
     * 
     * @param cell            the cell to rule on
     * @param neighborsOfCell the neighbors of the cell to rule on
     */
    Cell rulingFromNeighborsForCell(Cell cell, int quantityOfNeighbors) {
        assert(quantityOfNeighbors <= 8 && quantityOfNeighbors >= 0);
        
        if(cell == Cell.ALIVE) {
            return cellShouldSurviveByQuantityOfNeighborsAsIndex[quantityOfNeighbors];
        }
        return cellShouldBeReproducedByQuantityOfNeighborsAsIndex[quantityOfNeighbors];
    }
}