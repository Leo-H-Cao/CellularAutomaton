package cellsociety.cell;

import cellsociety.cell.Type.GAMETYPE;
import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.NULL;

/**
 * This class manages the 2D array of Cells that abstractly represents the game's world
 * A grid of cells is initialized to a particular Cell subclass depending on the game picked from a configuration file
 * Each game tick or when manually called, the nextGeneration method will update the cell grid as a function of its current state
 *
 * @author Zack Schrage
 */
public class CellGrid {

    private static Cell[][] grid;

    /**
     * Initializes a grid of cells with the appropriate type given the game being played
     *
     * @param width of the cell grid
     * @param height of the cell grid
     * @param gType game being played
     */
    public static void initializeGrid(int width, int height, GAMETYPE gType) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = newGameCell(i, j, gType);
            }
        }
    }

    public static void initializeCells() {
        //Something here about reading in cells from the parser and putting them on the grid
    }

    /**
     * Iterates through the entire grid and calls the nextGeneration method on each cell and compiles
     * the results into a new cell grid, then overwrite the old cell grid with the next generation of cells
     */
    public static void nextGeneration() {
        Cell[][] nextGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                nextGrid[i][j].updateType(grid[i][j].nextGeneration(getNeighbors(i, j)));
            }
        }
        grid = nextGrid;
    }

    private static Cell newGameCell(int i, int j, GAMETYPE gType) {
        switch(gType) {
            case GAMEOFLIFE:
                return new GameOfLifeCell(i, j, NULL);
            case FIRE:
                return new FireCell(i, j, NULL);
            case WATOR:
                return new WaTorCell(i, j, NULL);
        }
        return null;
    }

    private static CELLTYPE[][] getNeighbors(int x, int y) {
        CELLTYPE[][] neighbors = new CELLTYPE[3][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    neighbors[i][j] = grid[x - 1 + i][y - 1 + j].cType;
                } catch (ArrayIndexOutOfBoundsException e) {
                    neighbors[i][j] = NULL;
                }
            }
        }
        neighbors[1][1] = NULL;
        return neighbors;
    }

    /**
     * Getter method for the grid
     *
     * @return Grid of Cells
     */
    public static Cell[][] getGrid() {
        return grid;
    }

}
