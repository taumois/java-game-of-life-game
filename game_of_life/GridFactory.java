
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
        return new UnborderedGrid(new CellRuler(B,S), 110, 30);
    }

    /**
     * Returns the bordered grid which is currently 11x30 & bordered
     */
    public static Grid bordered() {
        int[] B = {3};
        int[] S = {2, 3};
        return new BorderedGrid(new CellRuler(B,S), 110, 30);
    }
    
    /**
     * Returns the mini grid which is currently 70x15 & unbordered
     */
    public static Grid mini() {
        int[] B = {3};
        int[] S = {2, 3};
        return new UnborderedGrid(new CellRuler(B,S), 70, 15);
    }
    
    /**
     * Returns the diamoeba grid which is currently 45x35 & unbordered & following the a the special 'diamoeba' rulestring
     */
    public static Grid diamoeba() {
        int[] B = {3,5,6,7,8};
        int[] S = {5,6,7,8};
        return new UnborderedGrid(new CellRuler(B,S), 45, 35);
    }
    
    /**
     * Returns the maze grid which is currently 110x30 & unbordered & following the a the special 'maze' rulestring
     */
    public static Grid maze() {
        int[] B = {3};
        int[] S = {1,2, 3, 4, 5};
        return new UnborderedGrid(new CellRuler(B,S), 110, 30);
    }
    
    /**
     * Returns the coral grid which is currently 110x30 & unbordered & following the a the special 'coral' rulestring
     */
    public static Grid coral() {
        int[] B = {3};
        int[] S = {3,4 ,5, 6, 7, 8};
        return new UnborderedGrid(new CellRuler(B,S), 110, 30);
    }
}
