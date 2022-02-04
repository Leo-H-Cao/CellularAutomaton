package cellsociety.cell;

import static cellsociety.cell.CellType.*;

/**
 * Cell type for Game of Life
 *
 * @author Zack Schrage
 */
public class GameOfLifeCell extends Cell {

    public GameOfLifeCell(int x, int y, CellType cType) {
        super(x, y, cType);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
