package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.ALIVE;
import static cellsociety.cell.Type.CELLTYPE.DEAD;

/**
 * This is the Cell type for Game of Life, its next generation method follows the rules that:
 * Any live cell with fewer than two live neighbours dies, as if by underpopulation.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by overpopulation.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 * @author Zack Schrage
 */
public class GameOfLifeCell extends Cell {

    public GameOfLifeCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public CELLTYPE nextGeneration(CELLTYPE[][] neighborsType) {
        int liveNeighbors = countLiveNeighbors(neighborsType);
        if (cType == ALIVE) {
            if (liveNeighbors < 3) return DEAD;
            if (liveNeighbors == 3) return ALIVE;
            if (liveNeighbors > 3) return DEAD;
        }
        else {
            if (liveNeighbors == 3) return ALIVE;
        }
        return DEAD;
    }

    private int countLiveNeighbors(CELLTYPE[][] neighborsType) {
        int liveNeighbors = 0;
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length; j++) {
                if (neighborsType[i][j] == ALIVE) liveNeighbors++;
            }
        }
        return liveNeighbors;
    }

}
