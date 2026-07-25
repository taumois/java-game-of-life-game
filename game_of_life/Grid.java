package game_of_life_engine;


/**
 * A grid for a 'Game of Life' that can step forward a through generations, changing the state of its cells which can be either dead or alive.
 */
public interface Grid {
    
    /**
     * The 2d array containing all of the cells. The 1st dimension is row #; the 2nd dimension is column #
     * 
     * @return the cells
     */
    public Cell[][] cells();
    
    /**
     * Steps every cell forwards one generation
     * 
     * @param NumberOfGenerations
     */
    public void stepForwardGenerations(int NumberOfgenerations);
}
