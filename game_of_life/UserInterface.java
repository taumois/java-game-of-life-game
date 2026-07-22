
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
     * Create and display a prompt to the user for boolean input.
     * 
     * @param prompt the message in the prompt
     */
    public void createInputPrompt(String prompt, InputType type);
    
    /**
     * Be returned an Input object made with the last user input recieved
     * 
     * @return the input object
     */
    public Input lastRecievedInput();
}
