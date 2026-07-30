
/**
 * 
 */
public class Main {
    public static void main(String[] args) {
        main();
    }
    
    public static void main() {
        Game game = new Game(new TerminalUserInterface(), GridFactory.standard());
        game.run();
    }
}