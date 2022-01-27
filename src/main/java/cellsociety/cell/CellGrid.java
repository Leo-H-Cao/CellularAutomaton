package cellsociety.cell;

import cellsociety.cell.Type.GAMETYPE;
import cellsociety.cell.Type.CELLTYPE;

import static cellsociety.cell.Type.CELLTYPE.ALIVE;
import static cellsociety.cell.Type.CELLTYPE.DEAD;
import static cellsociety.cell.Type.CELLTYPE.NULL;
import static cellsociety.cell.Type.GAMETYPE.GAMEOFLIFE;

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
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = Cell.newGameCell(i, j, gType, NULL);
            }
        }
    }

    /**
     * Initializes the cells to start at certain cell type states
     */
    public static void initializeCells() {
        //Something here about reading in cells from the parser and putting them on the grid
        //HARDCODED FOR TESTING PURPOSES
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].updateType(grid[i][j].getDefault());
            }
        }
        grid[5][5].updateType(ALIVE);
        grid[6][5].updateType(ALIVE);
        grid[7][5].updateType(ALIVE);

        grid[10][10].updateType(ALIVE);
        grid[11][10].updateType(ALIVE);
        grid[12][10].updateType(ALIVE);
        grid[12][9].updateType(ALIVE);
        grid[11][8].updateType(ALIVE);
    }

    /**
     * Iterates through the entire grid and calls the nextGeneration method on each cell and compiles
     * the results into a new cell grid, then overwrite the old cell grid with the next generation of cells
     */
    public static void nextGeneration() {
        Cell[][] nextGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //HARDCODED FOR TESTING PURPOSES
                nextGrid[i][j] = Cell.newGameCell(i, j, GAMEOFLIFE, DEAD);
                nextGrid[i][j].updateType(grid[i][j].nextGeneration(getNeighbors(i, j)));
            }
        }
        grid = nextGrid;
    }

    private static CELLTYPE[][] getNeighbors(int x, int y) {
        CELLTYPE[][] neighbors = new CELLTYPE[3][3];
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                try {
                    neighbors[i][j] = grid[x - 1 + i][y - 1 + j].getType();
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
