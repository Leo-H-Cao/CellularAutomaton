package cellsociety.cell;

/**
 * This class manages the 2D array of Cells that abstractly represents the game's world
 * This deals specifically with games that have STATIONARY ENTITIES (SE)
 *
 * @author Zack Schrage
 */
public class CellGridSE extends CellGrid {

    @Override
    public void nextGeneration() {

    }

    /**
     * Initializes a new grid of cells to be used by the next generation function that it can update as it loops over it
     * The current grid gets overwritten by this grid
     * @return a new update grid
     */
    protected static Cell[][] initializeUpdateGrid() {
        Cell[][] updatingGrid = new Cell[getGrid().length][getGrid()[0].length];
        for (int i = 0; i < getGrid().length; i++) {
            for (int j = 0; j < getGrid()[0].length; j++) {
                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), getGrid()[i][j].getType());
            }
        }
        return updatingGrid;
    }

}
