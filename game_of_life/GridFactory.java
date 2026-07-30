
/**
 * 
 */
public class GridFactory {
    public static Grid standard() {
        int[] B = {3};
        int[] S = {2, 3};
        return new UnborderedGrid(new CellRuler(B,S), 20, 20);
    }
    
    public static Grid bordered() {
        int[] B = {3};
        int[] S = {2, 3};
        return new BorderedGrid(new CellRuler(B,S), 20, 20);
    }
    
    public static Grid huge() {
        int[] B = {3};
        int[] S = {2, 3};
        return new UnborderedGrid(new CellRuler(B,S), 50, 30);
    }
    
    public static Grid diamoeba() {
        int[] B = {3,5,6,7,8};
        int[] S = {5,6,7,8};
        return new UnborderedGrid(new CellRuler(B,S), 20, 20);
    }
}
