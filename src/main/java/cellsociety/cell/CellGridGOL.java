package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.ALIVE;
import static cellsociety.cell.Type.CELLTYPE.DEAD;

/**
 * This is the Cell Grid Manager for Game of Life, its next generation method follows the rules that:
 * Any live cell with fewer than two live neighbours dies, as if by underpopulation.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by overpopulation.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 * @author Zack Schrage
 */
public class CellGridGOL extends CellGrid {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        Cell[][] grid = getGrid();
        updatingGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), grid[i][j].getType());
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
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
            else if (liveNeighbors > 3) updatingGrid[x][y].updateType(DEAD);
        }
        else {
            if (liveNeighbors == 3) updatingGrid[x][y].updateType(ALIVE);
            else updatingGrid[x][y].updateType(DEAD);
        }
    }

    private static int countLiveNeighbors(CELLTYPE[][] neighborsType) {
        int liveNeighbors = 0;
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length; j++) {
                if (neighborsType[i][j] == ALIVE) liveNeighbors++;
            }
        }
        return liveNeighbors;
    }

}

class GameOfLifeCell extends Cell {

    public GameOfLifeCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public CELLTYPE getDefault() {
        return DEAD;
    }

}
