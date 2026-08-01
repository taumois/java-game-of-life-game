 
/**
 * Rules what the state of a cell should be
 * I.e. enforces a given ruleset
 * 
 * Code here uses Birth/Survival notation.
 * look at it here:
 *          https://conwaylife.com/wiki/Rulestring#:~:text=S/B%20notation.-,Birth/survival%20notation,-The%20most%20common
 */
public class CellRuler {
    private Cell[] cellShouldSurviveByQuantityOfNeighborsAsIndex = new Cell[9];
    private Cell[] cellShouldBeReproducedByQuantityOfNeighborsAsIndex = new Cell[9];
    
    /**
     * An object of LifeAutomataRuler
     * 
     * @param quantitiesOfNeighborsToAllowReproduction an array of 'Birth' values from the Birth/Survival notated ruleset
     * @param quantitiesOfNeighborsToAllowSurvival     an array of the 'Survival' values from the Birth/Survival notated ruleset
     */
    CellRuler(int[] quantitiesOfNeighborsToAllowReproduction, int[] quantitiesOfNeighborsToAllowSurvival) {
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
     * Returns an object of this class that uses the standard Conway's Game of Life ruleset
     * 
     * @return the object
     */
    static CellRuler defaultLifeRulesetRuler() {
        int[] R = {3};
        int[] S = {2, 3};
        return new CellRuler(R, S);
    }
    
    /**
     * The ruling on what it thinks a cell should be from the info passed and the ruleset this object was created with
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
