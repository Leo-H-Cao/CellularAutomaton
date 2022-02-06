package cellsociety.cell;

import cellsociety.game.Game;
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
    private static int neighborhoodCenter;

    /**
     * Initializes a grid of cells with the appropriate type given the game being played
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
        assignNeighborhoodCenter();
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

    /**
     * Updates an updatingGrid at a particular location with a specified type and properties
     * Depending on the neighborhood type, the updatingGrid will be updated accordingly
     * @param updatingGrid the grid to be updated
     * @param d a directional offset
     * @param x central x coordinate
     * @param y central y coordinate
     * @param cType new cell type
     * @param properties new cell properties
     */
    public static void updateGrid(Cell[][] updatingGrid, int d, int x, int y, CellType cType, Map<CellProperties, Object> properties) {
        properties.put(MOVED, true);
        switch (neighborhoodType) {
            case SQUARE_MOORE, SQUARE_NEUMANN -> updateGridSquareNeighbors(updatingGrid, d, x, y, cType, properties);
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN -> updateGridTriangularNeighbors(updatingGrid, d, x, y, cType, properties);
        }
    }

    private static void updateGridSquareNeighbors(Cell[][] updatingGrid, int d, int x, int y, CellType cType, Map<CellProperties, Object> properties) {
        switch (d) {
            case 0 -> updatingGrid[x - 1][y - 1].updateType(cType, properties);
            case 1 -> updatingGrid[x - 1][y].updateType(cType, properties);
            case 2 -> updatingGrid[x - 1][y + 1].updateType(cType, properties);
            case 3 -> updatingGrid[x][y - 1].updateType(cType, properties);
            case 4 -> updatingGrid[x][y].updateType(cType, properties);
            case 5 -> updatingGrid[x][y + 1].updateType(cType, properties);
            case 6 -> updatingGrid[x + 1][y - 1].updateType(cType, properties);
            case 7 -> updatingGrid[x + 1][y].updateType(cType, properties);
            case 8 -> updatingGrid[x + 1][y + 1].updateType(cType, properties);
        }
    }

    private static void updateGridTriangularNeighbors(Cell[][] updatingGrid, int d, int x, int y, CellType cType, Map<CellProperties, Object> properties) {
        switch (d) {
            case 0 -> updatingGrid[x - 1][y - 2].updateType(cType, properties);
            case 1 -> updatingGrid[x - 1][y - 1].updateType(cType, properties);
            case 2 -> updatingGrid[x - 1][y].updateType(cType, properties);
            case 3 -> updatingGrid[x - 1][y + 1].updateType(cType, properties);
            case 4 -> updatingGrid[x - 1][y + 2].updateType(cType, properties);
            case 5 -> updatingGrid[x][y - 2].updateType(cType, properties);
            case 6 -> updatingGrid[x][y - 1].updateType(cType, properties);
            case 7 -> updatingGrid[x][y].updateType(cType, properties);
            case 8 -> updatingGrid[x][y + 1].updateType(cType, properties);
            case 9 -> updatingGrid[x][y + 2].updateType(cType, properties);
            case 10 -> updatingGrid[x + 1][y - 2].updateType(cType, properties);
            case 11 -> updatingGrid[x + 1][y - 1].updateType(cType, properties);
            case 12 -> updatingGrid[x + 1][y].updateType(cType, properties);
            case 13 -> updatingGrid[x + 1][y + 1].updateType(cType, properties);
            case 14 -> updatingGrid[x + 1][y + 2].updateType(cType, properties);
        }
    }

    /**
     * Returns a CellType grid of a particular cell's neighborhood.
     * If a cell is on the edge then the cell's out of bounds neighbor would set to the NULL type
     * The cell itself is not considered a neighbor of itself, so it will also be set to NULL
     * @param x coordinate of the cell
     * @param y coordinate of the cell
     * @return its neighboring cell types
     */
    public static CellType[][] getNeighbors(int x, int y, Cell[][] grid) {
        switch (neighborhoodType) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                return getSquareNeighbors(x, y, grid);
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN:
                return getTriangularNeighbors(x, y, grid);
        }
    }

    private static CellType[][] getSquareNeighbors(int x, int y, Cell[][] grid) {
        int n = Integer.parseInt(Game.getDefaultProperties().getString("SQUARE_NEIGHBORS_WIDTH"));
        CellType[][] neighbors = new CellType[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    neighbors[i][j] = grid[x - (n/2) + i][y - (n/2) + j].getType();
                } catch (ArrayIndexOutOfBoundsException e) {
                    neighbors[i][j] = NULL;
                }
            }
        }
        neighbors[n/2][n/2] = NULL;
        return neighbors;
    }

    private static CellType[][] getTriangularNeighbors(int x, int y, Cell[][] grid) {
        int m = Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_HEIGHT"));
        int n = Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_WIDTH"));
        CellType[][] neighbors = new CellType[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    neighbors[i][j] = grid[x - (n/2) + i][y - (m/2) + j].getType();
                } catch (ArrayIndexOutOfBoundsException e) {
                    neighbors[i][j] = NULL;
                }
            }
        }
        neighbors[m/2][n/2] = NULL;
        if ((x+y)%2 == 0) {
            neighbors[m-1][0] = NULL;
            neighbors[m-1][n-1] = NULL;
        }
        else {
            neighbors[0][0] = NULL;
            neighbors[0][n-1] = NULL;
        }
        return neighbors;
    }

    private static void assignNeighborhoodCenter() {
        switch(neighborhoodType) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                neighborhoodCenter = Integer.parseInt(Game.getDefaultProperties().getString("SQUARE_NEIGHBORS_COUNT"))/2;
                break;
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN:
                neighborhoodCenter = Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_COUNT"))/2;
                break;
        }
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
     * Getter method for the neighborhood type
     * @return neighborhood type
     */
    public static NeighborhoodType getNeighborhoodType() { return neighborhoodType; }

    /**
     * Getter method for the central coordinate of the neighborhood
     * @return neighborhood center
     */
    public static int getNeighborhoodCenter() {
        return neighborhoodCenter;
    }

    /**
     * Each next generation is a function of the current generation and since the rules surrounding
     * what a cells type will be in the next generation is game dependent, each subclass will implement
     * its own rules dictating the game behavior
     */
    public abstract void nextGeneration();

}
