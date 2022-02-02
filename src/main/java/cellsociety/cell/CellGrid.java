package cellsociety.cell;

import cellsociety.utils.Type.GAMETYPE;
import cellsociety.utils.Type.CELLTYPE;
import java.util.ArrayList;

import static cellsociety.utils.Type.CELLTYPE.*;

/**
 * This class manages the 2D array of Cells that abstractly represents the game's world
 * A grid of cells is initialized to a particular Cell subclass depending on the game picked from a configuration file
 * Each game tick or when manually called, the nextGeneration method will update the cell grid as a function of its current state
 *
 * @author Zack Schrage
 */
public abstract class CellGrid {

    private static Cell[][] grid;
    private static GAMETYPE gametype;

    /**
     * Initializes a grid of cells with the appropriate type given the game being played
     *
     * @param width of the cell grid
     * @param height of the cell grid
     * @param gType game being played
     */
    public static void initializeGrid(int width, int height, GAMETYPE gType) {
        gametype = gType;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = Cell.newGameCell(i, j, gType, NULL);
            }
        }
    }

    /**
     * Initializes the cells on the grid to their default states given a game type and
     * takes in a list of cells to initialize onto the board
     */
    public static void initializeCells(ArrayList<Cell> cells) {
        for (Cell[] value : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                value[j].updateType(value[j].getDefault());
            }
        }
        for (Cell c : cells) {
            grid[c.getX()][c.getY()].updateType(c.getType());
        }
    }

    /**
     * Returns a cells 8 neighboring cell types.
     * If a cell is on the edge than cells that would be out of bounds are set to NULL cells
     * Its central cell, itself, is also set to NULL
     *
     * @param x coordinate of the cell
     * @param y coordinate of the cell
     * @return its neighboring cell types
     */
    public static CELLTYPE[][] getNeighbors(int x, int y) {
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
     * @return Grid of Cells
     */
    public static Cell[][] getGrid() {
        return grid;
    }

    /**
     * Setter method for the grid
     * @param gridIn grid of Cells
     */
    public static void setGrid(Cell[][] gridIn) {
        grid = gridIn;
    }

    /**
     * Getter method for the game type
     * @return game type
     */
    public static GAMETYPE getGameType() { return gametype; }

    /**
     * Each next generation is a function of the current generation and since the rules surrounding
     * what a cells type will be in the next generation is game dependent, each subclass will implement
     * its own rules dictating the game behavior
     */
    public abstract void nextGeneration();

}
