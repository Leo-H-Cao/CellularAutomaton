package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;

import static cellsociety.cell.Type.CELLTYPE.*;

/**
 * Cell type for Game of Life
 *
 * @author Zack Schrage
 */
public class GameOfLifeCell extends Cell {

    public GameOfLifeCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public CELLTYPE getDefault() {
        return DEAD;
    }

}
