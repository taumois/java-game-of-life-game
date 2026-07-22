
/**
 * 
 */
public class Game {
    private final UserInterface USER_INTERFACE;
    private final Grid GRID;
    
    Game(UserInterface userInterface, Grid grid) {
        this.USER_INTERFACE = userInterface;
        this.GRID = grid;
    }
    
    static Game standardGame() {
        return new Game(new TerminalUserInterface(), new StandardBorderedVariableSizeGameGrid(25,25));
    }
    
    void play() {
        GRID.stepForwardGenerations(1);
        USER_INTERFACE.updateGrid(GRID.cells());
        USER_INTERFACE.createInputPrompt("Hello world", InputType.STRING);
    }
}
