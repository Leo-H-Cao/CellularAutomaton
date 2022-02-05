package cellsociety.cell;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.SQUARE_NEUMANN;
import static cellsociety.game.NeighborhoodType.TRIANGULAR_NEUMANN;

/**
 * This is the Cell Grid Manager for Game of Life, its next generation method follows the rules that:
 * Any live cell with fewer than two live neighbours dies, as if by underpopulation.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by overpopulation.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 * @author Zack Schrage
 */
public class GameOfLife extends CellGridSE {

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
        int liveNeighbors = countLiveNeighbors(CellGrid.getNeighbors(x, y));
        if (type == ALIVE) {
            if (liveNeighbors < 2) updatingGrid[x][y].updateType(DEAD);
            else if (liveNeighbors == 2 || liveNeighbors == 3) updatingGrid[x][y].updateType(ALIVE);
            else if (liveNeighbors > 3) updatingGrid[x][y].updateType(DEAD);
        }
        else {
            if (liveNeighbors == 3) updatingGrid[x][y].updateType(ALIVE);
            else updatingGrid[x][y].updateType(DEAD);
        }
    }

    private static int countLiveNeighbors(CellType[][] neighborsType) {
        switch(getNeighborhoodType()) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                return countLiveSquareNeighbors(neighborsType, getNeighborhoodType()==SQUARE_NEUMANN);
            case TRIANGULAR_MOORE, TRIANGULAR_NEUMANN:
                return countLiveTriangularNeighbors(neighborsType, getNeighborhoodType()==TRIANGULAR_NEUMANN);
        }
    }

    private static int countLiveSquareNeighbors(CellType[][] neighborsType, boolean isNeumann) {
        int liveNeighbors = 0;
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length; j++) {
                if (isNeumann && (i+j)%2 == 0) continue;
                if (neighborsType[i][j] == ALIVE) liveNeighbors++;
            }
        }
        return liveNeighbors;
    }

    private static int countLiveTriangularNeighbors(CellType[][] neighborsType, boolean isNeumann) {
        int liveNeighbors = 0;
        return liveNeighbors;
    }

}
