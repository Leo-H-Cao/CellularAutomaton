package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import cellsociety.io.PropertiesLoader;

import static cellsociety.cell.Type.CELLTYPE.*;

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

    @Override
    public void nextGeneration(Cell[][] updatingGrid) {
        if (getType() == EMPTY) return;
        double fReal = fReal(CellGrid.getNeighbors(getX(), getY()));
        if (fReal >= PropertiesLoader.fIdeal) return;
        int d = bestDirection(getValidDirections(updatingGrid), fReal);
        if (d < 0) return;
        updateGrid(d, getType(), updatingGrid);
    }

    @Override
    public Type.CELLTYPE getDefault() {
        return EMPTY;
    }

    private double fReal(CELLTYPE[][] neighbors) {
        int countA = getType() == A ? 1 : 0;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                if (neighbors[0][0] == A) countA++;
            }
        }
        return getType() == A ? (double)countA/8.0 : 1.0-((double)countA/8.0);
    }

    private double[] getValidDirections(Cell[][] updatingGrid) {
        double[] validDirections = new double[8];
        if (updatingGrid[getX()-1][getY()-1].getType() == EMPTY) validDirections[0] = fReal(CellGrid.getNeighbors(getX()-1, getY()-1));
        if (updatingGrid[getX()][getY()-1].getType() == EMPTY) validDirections[1] = fReal(CellGrid.getNeighbors(getX(), getY()-1));
        if (updatingGrid[getX()+1][getY()-1].getType() == EMPTY) validDirections[2] = fReal(CellGrid.getNeighbors(getX()+1, getY()-1));
        if (updatingGrid[getX()-1][getY()].getType() == EMPTY) validDirections[3] = fReal(CellGrid.getNeighbors(getX()-1, getY()));
        if (CellGrid.getGrid()[getX()+1][getY()].getType() == EMPTY) validDirections[4] = fReal(CellGrid.getNeighbors(getX()+1, getY()));
        if (CellGrid.getGrid()[getX()-1][getY()+1].getType() == EMPTY) validDirections[5] = fReal(CellGrid.getNeighbors(getX()-1, getY()+1));
        if (CellGrid.getGrid()[getX()][getY()+1].getType() == EMPTY) validDirections[6] = fReal(CellGrid.getNeighbors(getX(), getY()+1));
        if (CellGrid.getGrid()[getX()+1][getY()+1].getType() == EMPTY) validDirections[7] = fReal(CellGrid.getNeighbors(getX()+1, getY()+1));
        return validDirections;
    }

    private int bestDirection(double[] validDirections, double fReal) {
        int count = 0;
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i] > fReal) count++;
        }
        int random = (int)(Math.random() * count);
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i] > fReal && random > 0) random--;
            else if (validDirections[i] > fReal && random == 0) return i;
        }
        return -1;
    }

    private void updateGrid(int d, CELLTYPE cType, Cell[][] updatingGrid) {
        if (d == 0) updatingGrid[getX()-1][getY()-1].updateType(cType);
        if (d == 1) updatingGrid[getX()][getY()-1].updateType(cType);
        if (d == 2) updatingGrid[getX()+1][getY()-1].updateType(cType);
        if (d == 3) updatingGrid[getX()-1][getY()].updateType(cType);
        if (d == 4) updatingGrid[getX()+1][getY()].updateType(cType);
        if (d == 5) updatingGrid[getX()-1][getY()+1].updateType(cType);
        if (d == 6) updatingGrid[getX()][getY()+1].updateType(cType);
        if (d == 7) updatingGrid[getX()+1][getY()+1].updateType(cType);
    }

}
