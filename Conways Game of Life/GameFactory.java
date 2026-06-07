
/**
 * GameFactory here.
 *
 * @author Isaiah Taumoepeau
 */
public class GameFactory {
    
    /**
     * Constructs an example object of ConwaysGameOfLife
     *
     * @return the object
     */
    public static ConwaysGameOfLife egGame() {
        var game = new ConwaysGameOfLife(new GameUI(new TextUserDisplay(), new TerminalUserInput()), new GameGrid(8, 5));
        game.display();
        return game;
    }
}
