
/**
 * 
 */
public class GameFactory {
    public static Game gameA() {
        return new Game(new TerminalUserInterface(), new UnborderedGrid());
    }
    
    public static Game gameB() {
        int[] a = {1, 2, 5};
        int[] b = {3, 6};
        return new Game(new TerminalUserInterface(), new BorderedGrid(a,b));
    }
}