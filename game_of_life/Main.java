
import game_of_life_engine.*;

/**
 * 
 */
public class Main {
    public static void main(String[] args) {
        main();
    }
    
    public static void main() {
        var game = Game.standardGame();
        game.play();
    }
}