package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import cellsociety.io.PropertiesLoader;

import static cellsociety.cell.Type.CELLTYPE.BURNING;
import static cellsociety.cell.Type.CELLTYPE.EMPTY;
import static cellsociety.cell.Type.CELLTYPE.TREE;

/**
 * This is the Cell type for Fire, its next generation method follows the rules that:
 * A burning cell turns into an empty cell
 * A tree will burn if at least one neighbor is burning
 * A tree ignites with probability f even if no neighbor is burning
 * An empty space fills with a tree with probability p
 *
 * @author Zack Schrage
 */
public class FireCell extends Cell {

    public FireCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public CELLTYPE nextGeneration(CELLTYPE[][] neighborsType) {
        if (getType() == BURNING) return EMPTY;
        if (hasBurningNeighbor(neighborsType)) return BURNING;
        if (getType() == TREE && (Math.random() < PropertiesLoader.fireF)) return BURNING;
        if (getType() == EMPTY && (Math.random() < PropertiesLoader.fireP)) return TREE;
        return TREE;
    }

    @Override
    public CELLTYPE getDefault() {
        return EMPTY;
    }

    private boolean hasBurningNeighbor(CELLTYPE[][] neighborsType) {
        if (neighborsType[0][1] == BURNING) return true;
        if (neighborsType[1][0] == BURNING) return true;
        if (neighborsType[1][2] == BURNING) return true;
        if (neighborsType[2][1] == BURNING) return true;
        return false;
    }

}
