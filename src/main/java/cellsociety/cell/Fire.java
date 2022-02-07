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
    private static double treeCombustProbability;
    private static double treeGrowthProbability;

    public Fire(double combustP, double growthP){
        treeCombustProbability = combustP;
        treeGrowthProbability = growthP;
    }

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
        else if (type == TREE && hasBurningNeighbor(CellGrid.getNeighbors(x, y, getGrid()))) updatingGrid[x][y].updateType(BURNING);
        else if (type == TREE && (Math.random() < treeCombustProbability)) updatingGrid[x][y].updateType(BURNING);
        else if (type == EMPTY && (Math.random() < treeGrowthProbability)) updatingGrid[x][y].updateType(TREE);
        else updatingGrid[x][y].updateType(type);
    }

    private static boolean hasBurningNeighbor(CellType[][] neighborsType) {
	    return switch (getNeighborhoodType()) {
		    case SQUARE_MOORE, SQUARE_NEUMANN -> hasBurningSquareNeighbor(neighborsType, getNeighborhoodType() == SQUARE_NEUMANN);
		    case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN -> hasBurningTriangularNeighbor(neighborsType);
	    };
    }

    private static boolean hasBurningSquareNeighbor(CellType[][] neighborsType, boolean isNeumann) {
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length; j++) {
                if (isNeumann && (i+j)%2 == 0) continue;
                if (neighborsType[i][j] == BURNING) return true;
            }
        }
        return false;
    }

    private static boolean hasBurningTriangularNeighbor(CellType[][] neighborsType) {
        for (CellType[] cellTypes : neighborsType) {
            for (int j = 0; j < neighborsType[0].length; j++) {
                if (cellTypes[j] == BURNING) return true;
            }
        }
        return false;
    }

}
