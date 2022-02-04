package cellsociety.cell;

import cellsociety.game.Game;

import java.util.HashMap;
import java.util.Map;

import static cellsociety.cell.CellProperties.*;
import static cellsociety.cell.CellType.EMPTY;

/**
 * This class manages the 2D array of Cells that abstractly represents the game's world
 * This deals specifically with games that have MOBILE ENTITIES (ME)
 *
 * @author Zack Schrage
 */
public class CellGridME extends CellGrid {

    @Override
    public void nextGeneration() {

    }

    /**
     * Initializes a new grid of cells to be used by the next generation function
     * This forcibly sets the moved property to be false for games with moving entities
     * The current grid gets overwritten by this grid
     * @return a new update grid with the moved property as false
     */
    protected static Cell[][] initializeUpdateGrid() {
        Cell[][]  updatingGrid = new Cell[getGrid().length][getGrid()[0].length];
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), getGrid()[i][j].getType());
                Map<CellProperties, Object> properties = getGrid()[i][j].getProperties();
                properties.put(MOVED, false);
                updatingGrid[i][j].setProperties(properties);
            }
        }
        return updatingGrid;
    }

}
