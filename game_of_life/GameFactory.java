
/**
 * 
 */
public class GameFactory {
    public static Game gameA() {
        return new Game(new TerminalUserInterface(), new UnborderedGrid());
    }
    
    public static Game gameB() {
        // int[] a = {1, 2, 5};
        // int[] b = {3, 6};
        int[] b = {3};
        int[] a = {4,5,6,7,8};
        // return new Game(new TerminalUserInterface(), new UnborderedGrid(a,b));
        return new Game(new TerminalUserInterface(), new BorderedGrid());
    }
}