package cellsociety.cell;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.SQUARE_NEUMANN;
import static cellsociety.game.NeighborhoodType.TRIANGULAR_NEUMANN;

public class Percolation extends CellGridSE {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        updatingGrid = initializeUpdateGrid();
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                if (hasFloodedNeighbor(CellGrid.getNeighbors(i, j)) && updatingGrid[i][j].getType() != BLOCK) updatingGrid[i][j].updateType(WATER);
            }
        }
        setGrid(updatingGrid);
    }

    private static boolean hasFloodedNeighbor(CellType[][] neighborsType) {
        switch(getNeighborhoodType()) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                return hasFloodedSquareNeighbor(neighborsType, getNeighborhoodType()==SQUARE_NEUMANN);
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN:
                return hasFloodedTriangularNeighbor(neighborsType, getNeighborhoodType()==TRIANGULAR_NEUMANN);
        }
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

    private static boolean hasFloodedTriangularNeighbor(CellType[][] neighborsType, boolean isNeumann) {
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length - 1; j++) {
                if (neighborsType[i][j] == WATER) return true;
            }
        }
        return false;
    }

}
