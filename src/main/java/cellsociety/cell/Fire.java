package cellsociety.cell;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.*;

/**
 * This is the Cell Grid Manager for Fire, its next generation method follows the rules that:
 * A burning cell turns into an empty cell
 * A tree will burn if at least one neighbor is burning
 * A tree ignites with probability f even if no neighbor is burning
 * An empty space fills with a tree with probability p
 *
 * @author Zack Schrage
 */
public class Fire extends CellGridSE {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        updatingGrid = initializeUpdateGrid();
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                updateState(i, j, updatingGrid[i][j].getType());
            }
        }
        setGrid(updatingGrid);
    }

    private static void updateState(int x, int y, CellType type) {
        if (type == BURNING) updatingGrid[x][y].updateType(EMPTY);
        else if (type == TREE && hasBurningNeighbor(CellGrid.getNeighbors(x, y))) updatingGrid[x][y].updateType(BURNING);
        else if (type == TREE && (Math.random() < 0.05)) updatingGrid[x][y].updateType(BURNING);
        else if (type == EMPTY && (Math.random() < 0.3)) updatingGrid[x][y].updateType(TREE);
        else updatingGrid[x][y].updateType(type);
    }

    private static boolean hasBurningNeighbor(CellType[][] neighborsType) {
        switch(getNeighborhoodType()) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                return hasBurningSquareNeighbor(neighborsType, getNeighborhoodType()==SQUARE_NEUMANN);
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN:
                return hasBurningTriangularNeighbor(neighborsType, getNeighborhoodType()==TRIANGULAR_NEUMANN);
        }
    }

    private static boolean hasBurningSquareNeighbor(CellType[][] neighborsType, boolean isNeumann) {
        if (neighborsType[0][1] == BURNING) return true;
        if (neighborsType[1][0] == BURNING) return true;
        if (neighborsType[1][2] == BURNING) return true;
        if (neighborsType[2][1] == BURNING) return true;
        if (isNeumann) return false;
        if (neighborsType[0][0] == BURNING) return true;
        if (neighborsType[0][2] == BURNING) return true;
        if (neighborsType[2][0] == BURNING) return true;
        if (neighborsType[2][2] == BURNING) return true;
        return false;
    }

    private static boolean hasBurningTriangularNeighbor(CellType[][] neighborsType, boolean isNeumann) {
        return false;
    }

}
