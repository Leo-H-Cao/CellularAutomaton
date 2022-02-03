package cellsociety.cell;

import cellsociety.util.Type.CELLTYPE;
import static cellsociety.util.Type.CELLTYPE.ALIVE;
import static cellsociety.util.Type.CELLTYPE.DEAD;

/**
 * This is the Cell Grid Manager for Game of Life, its next generation method follows the rules that:
 * Any live cell with fewer than two live neighbours dies, as if by underpopulation.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by overpopulation.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 * @author Zack Schrage
 */
public class GameOfLife extends CellGrid {

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

    public static void updateState(int x, int y, CELLTYPE type) {
        int liveNeighbors = countLiveNeighbors(CellGrid.getNeighbors(x, y));
        if (type == ALIVE) {
            if (liveNeighbors < 2) updatingGrid[x][y].updateType(DEAD);
            else if (liveNeighbors == 2 || liveNeighbors == 3) updatingGrid[x][y].updateType(ALIVE);
            else updatingGrid[x][y].updateType(DEAD);
        }
        else {
            if (liveNeighbors == 3) updatingGrid[x][y].updateType(ALIVE);
            else updatingGrid[x][y].updateType(DEAD);
        }
    }

    private static int countLiveNeighbors(CELLTYPE[][] neighborsType) {
        int liveNeighbors = 0;
	    for (CELLTYPE[] celltypes : neighborsType) {
		    for (int j = 0; j < neighborsType[0].length; j++) {
			    if (celltypes[j] == ALIVE) liveNeighbors++;
		    }
	    }
        return liveNeighbors;
    }

}
