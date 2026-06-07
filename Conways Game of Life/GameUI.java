
/**
 * User Interface.
 *
 * @author Isaiah Taumoepeau
 */
class GameUI {
    private final UserDisplay DISPLAY;
    private final UserInput INPUT;
    
    /**
     * Constructor for objects of class UI
     */
    GameUI(UserDisplay display, UserInput input) {
        this.DISPLAY = display;
        this.INPUT = input;
    }
    
    void updateDisplay(Cell[][] grid) {
        DISPLAY.display(grid);
    }
    
    int userAction() {
        return INPUT.userAction();
    }
}
