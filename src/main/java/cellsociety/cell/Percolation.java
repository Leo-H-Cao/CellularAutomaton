package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.*;

public class Percolation extends CellGrid {

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

    private static void updateState(int x, int y, CELLTYPE type) {
        if (inBounds(x-1, y, updatingGrid) && CellGrid.getGrid()[x-1][y].getType() == type) updatingGrid[x][y].updateType(type);
        if (inBounds(x+1, y, updatingGrid) && CellGrid.getGrid()[x+1][y].getType() == type) updatingGrid[x][y].updateType(type);
        if (inBounds(x, y-1, updatingGrid) && CellGrid.getGrid()[x][y-1].getType() == type) updatingGrid[x][y].updateType(type);
    }

    private static boolean inBounds(int x, int y, Cell[][] updatingGrid) {
        if (x < 0 || x >= updatingGrid.length || y < 0 || y >= updatingGrid[0].length) return false;
        return true;
    }

}
