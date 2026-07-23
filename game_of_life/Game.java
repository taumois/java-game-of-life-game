
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
        boolean playing = true;
        while(playing) {
            GRID.stepForwardGenerations(1);
            USER_INTERFACE.updateGrid(GRID.cells());
            String menuPrompt = "How are you?";
            String[] menuOptions = {"Good", "Bad"};
            USER_INTERFACE.createInputMenu(menuPrompt, menuOptions);
        }
    }
}
