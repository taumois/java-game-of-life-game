
/**
 * Starting place for the program
 */
public class Main {
    /**
     * Called to play Game of Life
     */
    public static void main(String[] args) {
        main();
    }
    
    /**
     * Called to play game of Life
     */
    public static void main() {
        Game game = new Game(new TerminalUserInterface(), GridFactory.standard());
        game.run();
    }
}