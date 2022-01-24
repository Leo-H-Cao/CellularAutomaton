package cellsociety.cell;

/**
 * This class manages the 2D array of Cells that abstractly represents the game's world
 * A grid of cells is initialized to a particular Cell subclass depending on the game picked from a configuration file
 * Each game tick or when manually called, the nextGeneration method will update the cell grid as a function of its current state
 *
 * @author Zack Schrage
 */
public class CellGrid {

    private static Cell[][] grid;

    public static void initializeGrid(int width, int height, char gameType) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = newGameCell(i, j, gameType);
            }
        }
    }

    public static void nextGeneration() {
        Cell[][] nextGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                nextGrid[i][j].type = grid[i][j].nextGeneration(getNeighbors(i, j));
            }
        }
        grid = nextGrid;
    }

    private static Cell newGameCell(int i, int j, char gameType) {
        switch(gameType) {
            case 'l':
                return new GameOfLifeCell(i, j, 0);
            case 'f':
                return new FireCell(i, j, 0);
            case 'w':
                return new WaTorCell(i, j, 0);
        }
        return null;
    }

    private static int[][] getNeighbors(int x, int y) {
        int[][] neighbors = new int[3][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    neighbors[i][j] = grid[x - 1 + i][y - 1 + j].type;
                } catch (ArrayIndexOutOfBoundsException e) {
                    neighbors[i][j] = -1;
                }
            }
        }
        return neighbors;
    }

    public static Cell[][] getGrid() {
        return grid;
    }

}
