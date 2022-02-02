package cellsociety.cell;

import cellsociety.utils.Type.CELLTYPE;

import static cellsociety.utils.Type.CELLTYPE.*;

/**
 * This is the Cell type for Fire
 *
 * @author Zack Schrage
 */
public class FireCell extends Cell {

    public FireCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public CELLTYPE getDefault() {
        return EMPTY;
    }

}
