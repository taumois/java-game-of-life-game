
/**
 * Can display everything necessary for a Game
 */
interface GameUserDisplay {
    
    /**
     * Updates the displayed grid with a new one
     *
     * @param grid the new grid
     */
    public void updateGrid(Cell[][] grid);
    
    /**
     * Displays a prompt for the user
     *
     * @param the prompt
     */
    public void promptUserForAction(String prompt);
}
