package cellsociety.cell;

import cellsociety.game.GameType;
import cellsociety.game.NeighborhoodType;

import java.util.ArrayList;
import java.util.Map;

import static cellsociety.cell.CellProperties.*;
import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.*;

/**
 * This class manages the 2D array of Cells that abstractly represents the game's world
 * A grid of cells is initialized to a particular Cell subclass depending on the game picked from a configuration file
 * Each game tick or when manually called, the nextGeneration method will update the cell grid as a function of its current state
 *
 * @author Zack Schrage
 */
public abstract class CellGrid {

    private static Cell[][] grid;
    private static GameType gametype;
    private static NeighborhoodType neighborhoodType;

    /**
     * Initializes a grid of cells with the appropriate type given the game being played
     *
     * @param width of the cell grid
     * @param height of the cell grid
     * @param gType game being played
     */
    public static void initializeGrid(int width, int height, GameType gType) {
        gametype = gType;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = Cell.newGameCell(i, j, gType, NULL);
            }
        }
        neighborhoodType = SQUARE_MOORE;
    }

    /**
     * Initializes the cells on the grid to their default states given a game type and
     * takes in a list of cells to initialize onto the board
     */
    public static void initializeCells(ArrayList<Cell> cells) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].updateType(grid[i][j].getDefault());
//                FOR SCHELLING SEGREGATION TESTING
//                CellType randomType = EMPTY;
//                if (Math.random() > 0.35) randomType = Math.random() > 0.5? A : B;
//                grid[i][j].updateType(randomType);
            }
        }
        for (Cell c : cells) {
            grid[c.getX()][c.getY()].updateType(c.getType());
        }
    }

    public static boolean inBounds(int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) return false;
        return true;
    }

    public static void updateGrid(Cell[][] updatingGrid, int d, int x, int y, CellType cType, Map<CellProperties, Object> properties) {
        properties.put(MOVED, true);
        switch (neighborhoodType) {
            case SQUARE_MOORE, SQUARE_NEUMANN -> updateGridSquareNeighbors(updatingGrid, d, x, y, cType, properties);
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN -> updateGridTriangularNeighbors(updatingGrid, d, x, y, cType, properties);
        }
    }

    public static void updateGridSquareNeighbors(Cell[][] updatingGrid, int d, int x, int y, CellType cType, Map<CellProperties, Object> properties) {
        switch (d) {
            case -1 -> updatingGrid[x][y].updateType(cType, properties);
            case 0 -> updatingGrid[x - 1][y - 1].updateType(cType, properties);
            case 1 -> updatingGrid[x][y - 1].updateType(cType, properties);
            case 2 -> updatingGrid[x + 1][y - 1].updateType(cType, properties);
            case 3 -> updatingGrid[x - 1][y].updateType(cType, properties);
            case 4 -> updatingGrid[x + 1][y].updateType(cType, properties);
            case 5 -> updatingGrid[x - 1][y + 1].updateType(cType, properties);
            case 6 -> updatingGrid[x][y + 1].updateType(cType, properties);
            case 7 -> updatingGrid[x + 1][y + 1].updateType(cType, properties);
        }
    }

    public static void updateGridTriangularNeighbors(Cell[][] updatingGrid, int d, int x, int y, CellType cType, Map<CellProperties, Object> properties) {
        switch (d) {
            case -1 -> updatingGrid[x][y].updateType(cType, properties);
        }
    }

    /**
     * Returns a cells 8 neighboring cell types.
     * If a cell is on the edge than cells that would be out of bounds are set to NULL cells
     * Its central cell, itself, is also set to NULL
     * @param x coordinate of the cell
     * @param y coordinate of the cell
     * @return its neighboring cell types
     */
    public static CellType[][] getNeighbors(int x, int y) {
        CellType[][] neighbors = new CellType[3][3];
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
    public static GameType getGameType() { return gametype; }

    /**
     * Each next generation is a function of the current generation and since the rules surrounding
     * what a cells type will be in the next generation is game dependent, each subclass will implement
     * its own rules dictating the game behavior
     */
    public abstract void nextGeneration();

}
