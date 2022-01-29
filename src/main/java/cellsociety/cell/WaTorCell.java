package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.*;

/**
 * This is the Cell type for WaTor, its next generation method follows the rules that:
 * At each chronon, a fish moves randomly to one of the adjacent unoccupied squares. If there are no free squares, no movement takes place.
 * Once a fish has survived a certain number of chronons it may reproduce. This is done as it moves to a neighbouring square, leaving behind a new fish in its old position. Its reproduction time is also reset to zero.
 * At each chronon, a shark moves randomly to an adjacent square occupied by a fish. If there is none, the shark moves to a random adjacent unoccupied square. If there are no free squares, no movement takes place.
 * At each chronon, each shark is deprived of a unit of energy.
 * Upon reaching zero energy, a shark dies.
 * If a shark moves to a square occupied by a fish, it eats the fish and earns a certain amount of energy.
 * Once a shark has survived a certain number of chronons it may reproduce in exactly the same way as the fish.
 *
 * @author Zack Schrage
 */
public class WaTorCell extends Cell {

    private int reproductionTimer = 0;
    private int energy = 5;

    public WaTorCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public void nextGeneration(Cell[][] updatingGrid) {
        if (updatingGrid[getX()][getY()].getType() != NULL) return;
        if (getType() == EMPTY) {
            updatingGrid[getX()][getY()].updateType(EMPTY);
            return;
        }
        if (getType() == SHARK) {
            if (energy == 0) {
                updatingGrid[getX()][getY()].updateType(EMPTY, getProperties());
                return;
            }
        }
        if (moveToRandom(updatingGrid) && reproductionTimer < 4) {
            updatingGrid[getX()][getY()].updateType(EMPTY, getProperties());
        }
        else {
            updatingGrid[getX()][getY()].updateType(getType(), getProperties());
        }
    }

    @Override
    public void updateType(CELLTYPE cType, Object[] properties) {
        super.updateType(cType);
        reproductionTimer = (int) properties[0];
        energy = (int) properties[1];
        reproductionTimer++;
        energy--;
    }

    @Override
    public CELLTYPE getDefault() {
        return EMPTY;
    }

    private boolean moveToRandom(Cell[][] updatingGrid) {
        if (getType() == FISH) {
            int d = randomDirection(getValidDirections(updatingGrid, EMPTY));
            if (d < 0) return false;
            updateGrid(d, FISH, updatingGrid);
        }
        else if (getType() == SHARK) {
            int d = randomDirection(getValidDirections(updatingGrid, FISH));
            if (d < 0) d = randomDirection(getValidDirections(updatingGrid, EMPTY));
            if (d < 0) return false;
            updateGrid(d, SHARK, updatingGrid);
        }
        return true;
    }

    private boolean[] getValidDirections(Cell[][] updatingGrid, CELLTYPE destType) {
        boolean[] validDirections = new boolean[4];
        if (updatingGrid[getX()][getY()-1].getType() == destType) validDirections[0] = true;
        if (updatingGrid[getX()-1][getY()].getType() == destType) validDirections[1] = true;
        if (CellGrid.getGrid()[getX()+1][getY()].getType() == destType) validDirections[2] = true;
        if (CellGrid.getGrid()[getX()][getY()+1].getType() == destType) validDirections[3] = true;
        return validDirections;
    }

    private int randomDirection(boolean[] validDirections) {
        int count = 0;
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i]) count++;
        }
        int random = (int)(Math.random() * count);
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i] && random > 0) random--;
            else if (validDirections[i] && random == 0) return i;
        }
        return -1;
    }

    private void updateGrid(int d, CELLTYPE cType, Cell[][] updatingGrid) {
        if (d == 0) updatingGrid[getX()][getY()-1].updateType(cType, getProperties());
        if (d == 1) updatingGrid[getX()-1][getY()].updateType(cType, getProperties());
        if (d == 2) updatingGrid[getX()+1][getY()].updateType(cType, getProperties());
        if (d == 3) updatingGrid[getX()][getY()+1].updateType(cType, getProperties());
    }

    private Object[] getProperties() {
        Object[] properties = {reproductionTimer, energy};
        return properties;
    }

}
