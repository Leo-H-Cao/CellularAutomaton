package cellsociety.cell;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.SQUARE_NEUMANN;

/**
 * This is the Cell Grid Manager for Percolation, its next generation method follows the rules that:
 * Any block adjacent to or under a water source will become a water source
 * Blocks can block a water source from spreading and water cannot flow into or through a block
 *
 * @author Zack Schrage
 */
public class Percolation extends CellGridSE {

    @Override
    public void nextGeneration() {
        Cell[][] updatingGrid = initializeUpdateGrid();
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                if (hasFloodedNeighbor(CellGrid.getNeighbors(i, j, getGrid())) && updatingGrid[i][j].getType() != BLOCK) updatingGrid[i][j].updateType(WATER);
            }
        }
        setGrid(updatingGrid);
    }

    private static boolean hasFloodedNeighbor(CellType[][] neighborsType) {
	    return switch (getNeighborhoodType()) {
		    case SQUARE_MOORE, SQUARE_NEUMANN -> hasFloodedSquareNeighbor(neighborsType, getNeighborhoodType() == SQUARE_NEUMANN);
		    case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN -> hasFloodedTriangularNeighbor(neighborsType);
	    };
    }

    private static boolean hasFloodedSquareNeighbor(CellType[][] neighborsType, boolean isNeumann) {
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length - 1; j++) {
                if (isNeumann && (i+j)%2 == 0) continue;
                if (neighborsType[i][j] == WATER) return true;
            }
        }
        return false;
    }

    private static boolean hasFloodedTriangularNeighbor(CellType[][] neighborsType) {
        for (CellType[] cellTypes : neighborsType) {
            for (int j = 0; j < neighborsType[0].length - 1; j++) {
                if (cellTypes[j] == WATER) return true;
            }
        }
        return false;
    }

}
