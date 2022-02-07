package cellsociety.cell;

import static cellsociety.cell.CellType.*;

/**
 * This is the Cell type for Percolation
 *
 * @author Zack Schrage
 */
public class PercolationCell extends Cell {

    public PercolationCell(int x, int y, CellType cType) {
        super(x, y, cType);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
