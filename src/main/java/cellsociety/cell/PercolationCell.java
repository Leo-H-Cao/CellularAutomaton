package cellsociety.cell;

import static cellsociety.cell.Type.CELLTYPE.EMPTY;

public class PercolationCell extends Cell {

    public PercolationCell(int x, int y, Type.CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public Type.CELLTYPE getDefault() {
        return EMPTY;
    }

}
