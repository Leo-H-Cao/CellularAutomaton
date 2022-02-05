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
                if (updatingGrid[i][j].getType() != BLOCK) updateState(i, j, WATER);
            }
        }
        setGrid(updatingGrid);
    }

    private static void updateState(int x, int y, CellType type) {
        switch(getNeighborhoodType()) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                updateStateSquare(x, y, type, getNeighborhoodType()==SQUARE_NEUMANN);
                break;
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN:
                updateStateTriangular(x, y, type, getNeighborhoodType()==TRIANGULAR_NEUMANN);
                break;
        }
    }

    private static void updateStateSquare(int x, int y, CellType type, boolean isNeumann) {
        if (inBounds(x-1, y) && CellGrid.getGrid()[x-1][y].getType() == type) updatingGrid[x][y].updateType(type);
        if (inBounds(x+1, y) && CellGrid.getGrid()[x+1][y].getType() == type) updatingGrid[x][y].updateType(type);
        if (inBounds(x, y-1) && CellGrid.getGrid()[x][y-1].getType() == type) updatingGrid[x][y].updateType(type);
        if (isNeumann) return;
        if (inBounds(x-1, y-1) && CellGrid.getGrid()[x-1][y-1].getType() == type) updatingGrid[x][y].updateType(type);
        if (inBounds(x+1, y-1) && CellGrid.getGrid()[x+1][y-1].getType() == type) updatingGrid[x][y].updateType(type);
    }

    private static void updateStateTriangular(int x, int y, CellType type, boolean isNeumann) {
        if (inBounds(x-1, y) && CellGrid.getGrid()[x-1][y].getType() == type) updatingGrid[x][y].updateType(type);
    }

}
