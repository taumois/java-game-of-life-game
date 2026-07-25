
/**
 * 
 */
public interface UserInterface {
    /**
     * Update the grid displayed to the user.
     * 
     * @param grid the new grid to replace the displayed with
     */
    public void updateGrid(Cell[][] grid);
    
    /**
     * Create and display a menu to display to the user for them to choose from.
     * 
     * @param prompt a prompt for the menu
     * @param options the array of options for the user to select from after looking at the menu's prompt
     */
    public void createInputMenu(String prompt, String[] options);
    
    /**
     * 
     */
    public void createInputRangeMenu(String prompt, int lowerBound, int upperBound);
    
    /**
     * 
     */
    public String stringInput(String prompt);
    
    /**
     * Be returned the number of the option the player last chose in a input menu
     * 
     * @return the number
     */
    public int numberOfLastSelectedOptionByUser();
}
