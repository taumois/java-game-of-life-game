
/**
 * Constructs various predetermined objects of the Grid interface for others
 * 
 * Code here uses survival/birth notation. Read about it here: https://conwaylife.com/wiki/Rulestring#:~:text=S/B%20notation.-,Birth/survival%20notation,-The%20most%20common
 * TLDR: B = the set of cell that allow a cell to be born
 *       S =  the set of cells that allow a cell to survive
 */
public class GridFactory {
    /**
     * Returns the standard grid which is currently 25x20 & unbordered
     */
    public static Grid standard() {
        int[] B = {3};
        int[] S = {2, 3};
        return new UnborderedGrid(new CellRuler(B,S), 25, 20);
    }

    /**
     * Returns the bordered grid which is currently 25x20 & bordered
     */
    public static Grid bordered() {
        int[] B = {3};
        int[] S = {2, 3};
        return new BorderedGrid(new CellRuler(B,S), 25, 20);
    }
    
    /**
     * Returns the huge grid which is currently 50x50 & unbordered
     */
    public static Grid huge() {
        int[] B = {3};
        int[] S = {2, 3};
        return new UnborderedGrid(new CellRuler(B,S), 50, 30);
    }
    
    /**
     * Returns the diamoeba grid which is currently 20x20 & unbordered & following the a the special 'diamoeba' rulestring
     */
    public static Grid diamoeba() {
        int[] B = {3,5,6,7,8};
        int[] S = {5,6,7,8};
        return new UnborderedGrid(new CellRuler(B,S), 20, 20);
    }
}
