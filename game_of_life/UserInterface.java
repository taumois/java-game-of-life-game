
/**
 * The UserInterface deals with the UI:
 * Input handling
 * Displaying info
 */
public interface UserInterface {
    /**
     * Update the grid displayed to the user
     * 
     * @param grid the new grid to replace the displayed with
     */
    public void updateGrid(Cell[][] grid);
    
    /**
     * Create and display a menu to display to the user for them to choose from
     * 
     * @param prompt a prompt for the menu
     * @param options the array of options for the user to select from after looking at the menu's prompt
     */
    public void createInputMenu(String prompt, String[] options);
    
    /**
     * Create and display a prompt for the user to get an input in range of two numbers
     * Range is inclusive for bounding numbers.
     * 
     * @param prompt a prompt for the menu
     * @param upperBound a lower bound
     * @param lowerBound a upper bound
     */
    public void createInputRangeMenu(String prompt, int lowerBound, int upperBound);
    
    /**
     * Create and display a menu to display to the user for them to choose from
     * 
     * @param prompt a prompt for the menu
     */
    public String stringInput(String prompt);
    
    /**
     * Be returned the number of the option the player last chose in a input menu
     * 
     * @return the number
     */
    public int numberOfLastSelectedOptionByUser();
    
    /**
     * Switch to high contrast grid display mode
     */
    public void useHighContrastCellSymbols();
    
    /**
     * Switch to high contrast grid display mode
     */
    public void usePunchCardCellSymbols();
    
    /**
     * Switch to default grid display mode
     */
    public void useDefaultCellSymbols();
}
