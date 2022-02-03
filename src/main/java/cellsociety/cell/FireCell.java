package cellsociety.cell;

import static cellsociety.cell.CellType.*;

/**
 * This is the Cell type for Fire
 *
 * @author Zack Schrage
 */
public class FireCell extends Cell {

    public FireCell(int x, int y, CellType cType) {
        super(x, y, cType);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
