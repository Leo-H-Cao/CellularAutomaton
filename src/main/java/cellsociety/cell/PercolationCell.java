package cellsociety.cell;

import static cellsociety.cell.CellType.*;

public class PercolationCell extends Cell {

    public PercolationCell(int x, int y, CellType cType) {
        super(x, y, cType);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
