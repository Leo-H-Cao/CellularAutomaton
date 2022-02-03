package cellsociety.cell;

import cellsociety.utils.Type.CELLTYPE;
import static cellsociety.utils.Type.CELLTYPE.*;

/**
 * This is the Cell Grid Manager for Fire, its next generation method follows the rules that:
 * A burning cell turns into an empty cell
 * A tree will burn if at least one neighbor is burning
 * A tree ignites with probability f even if no neighbor is burning
 * An empty space fills with a tree with probability p
 *
 * @author Zack Schrage
 */
public class CellGridFire extends CellGrid {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        Cell[][] grid = getGrid();
        updatingGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), grid[i][j].getType());
            }
        }
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                updateState(i, j, updatingGrid[i][j].getType());
            }
        }
        setGrid(updatingGrid);
    }

    private static void updateState(int x, int y, CELLTYPE type) {
        if (type == BURNING) updatingGrid[x][y].updateType(EMPTY);
        else if (type == TREE && hasBurningNeighbor(CellGrid.getNeighbors(x, y))) updatingGrid[x][y].updateType(BURNING);
        else if (type == TREE && (Math.random() < 0.05)) updatingGrid[x][y].updateType(BURNING);
        else if (type == EMPTY && (Math.random() < 0.30)) updatingGrid[x][y].updateType(TREE);
        else updatingGrid[x][y].updateType(type);
    }

    private static boolean hasBurningNeighbor(CELLTYPE[][] neighborsType) {
        if (neighborsType[0][1] == BURNING) return true;
        if (neighborsType[1][0] == BURNING) return true;
        if (neighborsType[1][2] == BURNING) return true;
        return neighborsType[2][1] == BURNING;
    }
}
