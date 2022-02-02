package cellsociety.cell;

import cellsociety.utils.Type;

import static cellsociety.utils.Type.CELLTYPE.*;

/**
 * This is the Cell type for Schelling Segregation, its next generation method follows the rules that:
 * Agents desire a fraction F_ideal of their neighborhood (eight adjacent agents) to be from the same group as them
 * If the fraction of agents not including empty spaces F_real < F_ideal
 * Agents will attempt to relocate to a spot where F_real >= F_ideal
 *
 * @author Zack Schrage
 */
public class SchellingSegCell extends Cell {

    public SchellingSegCell(int x, int y, Type.CELLTYPE cType) {
        super(x, y, cType);
    }

//    @Override
//    public void nextGeneration(Cell[][] updatingGrid) {
//        if (updatingGrid[getX()][getY()].getType() == EMPTY) return;
//        if (getType() == EMPTY) {
//            updatingGrid[getX()][getY()].updateType(EMPTY);
//            return;
//        }
//        double fReal = fReal(CellGrid.getNeighbors(getX(), getY()));
//        if (fReal >= 0.6) return;
//        int d = bestDirection(getValidDirections(updatingGrid), fReal);
//        if (d < 0) {
//            updatingGrid[getX()][getY()].updateType(EMPTY);
//            return;
//        }
//        updateGrid(d, getType(), updatingGrid);
//    }

    @Override
    public Type.CELLTYPE getDefault() {
        return EMPTY;
    }

}
