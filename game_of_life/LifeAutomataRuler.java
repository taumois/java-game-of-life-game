
/**
 * Rules what the state of a cell should be.
 */
public class LifeAutomataRuler {
    private int[] neighborQuantitiesThatAllowReproduction;
    private int[] neighborQuantitiesThatAllowSurvival;
    
    /**
     * An object of LifeAutomataRuler
     */
    LifeAutomataRuler(int[] neighborQuantitiesThatAllowReproduction, int[] neighborQuantitiesThatAllowSurvival) {
        this.neighborQuantitiesThatAllowReproduction = neighborQuantitiesThatAllowReproduction;
        this.neighborQuantitiesThatAllowSurvival = neighborQuantitiesThatAllowSurvival;
    }
    
    /**
     * The ruling on what it thinks a cell should be from the info passed.
     * 
     * @param cell            the cell to rule on
     * @param neighborsOfCell the neighbors of the cell to rule on
     */
    static Cell cellRulingFrom(Cell cell, Cell neighborsOfCell) {
        for(int quantity : neighborsOfCell)
        return Cell.ALIVE;
    }
}