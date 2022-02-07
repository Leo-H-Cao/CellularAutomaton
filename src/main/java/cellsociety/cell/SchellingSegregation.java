package cellsociety.cell;

import cellsociety.game.Game;

import java.util.Map;

import static cellsociety.cell.CellProperties.MOVED;
import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.*;

/**
 * This is the Cell type for Schelling Segregation, its next generation method follows the rules that:
 * Agents desire a fraction F_ideal of their neighborhood (eight adjacent agents) to be from the same group as them
 * If the fraction of agents not including empty spaces F_real < F_ideal
 * Agents will attempt to relocate to a spot where F_real >= F_ideal
 *
 * @author Zack Schrage
 */
public class SchellingSegregation extends CellGridME {

    private static Cell[][] updatingGrid;
    private static double fIdeal = 0.6;

    public SchellingSegregation(double fIdealParameter){
        fIdeal = fIdealParameter;
    }

    @Override
    public void nextGeneration() {
        updatingGrid = initializeUpdateGrid();
        updatePositions();
        setGrid(updatingGrid);
    }

    private void updatePositions() {
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                Map<CellProperties, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() != EMPTY && !(boolean) properties.get(MOVED)) {
                    CellType type = updatingGrid[i][j].getType();
                    double fReal = fReal(CellGrid.getNeighbors(i, j, updatingGrid), type);
                    int d = getNeighborhoodCenter();
                    if (fReal < fIdeal) {
                        d = bestDirection(getValidDirections(i, j, EMPTY), fReal);
                    }
                    updateGrid(updatingGrid, d, i, j, type, properties);
                    if (d != getNeighborhoodCenter()) {
                        updateGrid(updatingGrid,getNeighborhoodCenter(), i, j, EMPTY, properties);
                    }
                }
            }
        }
    }

    private static double fReal(CellType[][] neighbors, CellType type) {
        double countA = type == A ? 1 : 0;
        double den = 0;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                if (neighbors[i][j] == A) countA++;
                if (neighbors[i][j] != EMPTY) den++;
            }
        }
        return type == A ? countA/den : (den - countA/den);
    }

    private static double[] getValidDirections(int x, int y, CellType destType) {
        switch(getNeighborhoodType()) {
            case SQUARE_MOORE, SQUARE_NEUMANN, default:
                return getValidDirectionsSquare(x, y, destType, getNeighborhoodType()==SQUARE_NEUMANN);
            case TRIANGULAR_MOORE:
                return getValidDirectionsTriangular(x, y, destType);
            case TRIANGULAR_NEUMANN:
                return getValidDirectionsTriangularNeumann(x, y, destType, (x+y)%2==0);
        }
    }

    private static double[] getValidDirectionsSquare(int x, int y, CellType type, boolean isNeumann) {
        double[] validDirections = new double[Integer.parseInt(Game.getDefaultProperties().getString("SQUARE_NEIGHBORS_COUNT"))];
        CellType[][] neighborsType = CellGrid.getNeighbors(x, y, updatingGrid);
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j = 0; j < neighborsType[0].length; j++) {
                System.out.println(neighborsType[i][j]);
                if (isNeumann && (i+j)%2 == 0) continue;
                if (neighborsType[i][j] == EMPTY) validDirections[(i*neighborsType.length)+j] = fReal(CellGrid.getNeighbors(x-1+i, y-1+j, updatingGrid), updatingGrid[x][y].getType());
            }
        }
        return validDirections;
    }

    private static double[] getValidDirectionsTriangular(int x, int y, CellType type) {
        double[] validDirections = new double[Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_COUNT"))];
        CellType[][] neighborsType = CellGrid.getNeighbors(x, y, updatingGrid);
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j = 0; j < neighborsType[0].length; j++) {
                if (neighborsType[i][j] == type) validDirections[(i*neighborsType.length)+j] = fReal(CellGrid.getNeighbors(x-2+i, y-1+j, updatingGrid), updatingGrid[x][y].getType());
            }
        }
        return validDirections;
    }

    private static double[] getValidDirectionsTriangularNeumann(int x, int y, CellType type, boolean orientDown) {
        double[] validDirections = new double[Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_COUNT"))];
        CellType[][] neighborsType = CellGrid.getNeighbors(x, y, updatingGrid);
        if (neighborsType[1][1] == type) validDirections[(1*neighborsType.length)+1] = fReal(CellGrid.getNeighbors(x-2+1, y-1+1, updatingGrid), updatingGrid[x][y].getType());
        if (neighborsType[1][3] == type) validDirections[(1*neighborsType.length)+3] = fReal(CellGrid.getNeighbors(x-2+1, y-1+3, updatingGrid), updatingGrid[x][y].getType());
        if (orientDown && neighborsType[0][2] == type) validDirections[(0*neighborsType.length)+2] = fReal(CellGrid.getNeighbors(x-2+0, y-1+2, updatingGrid), updatingGrid[x][y].getType());
        if (!orientDown && neighborsType[2][2] == type) validDirections[(2*neighborsType.length)+2] = fReal(CellGrid.getNeighbors(x-2+2, y-1+2, updatingGrid), updatingGrid[x][y].getType());
        return validDirections;
    }


    private static int bestDirection(double[] validDirections, double fReal) {
        int count = 0;
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i] > fReal) count++;
        }
        int random = (int)(Math.random() * count);
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i] > fReal && random > 0) random--;
            else if (validDirections[i] > fReal && random == 0) return i;
        }
        return getNeighborhoodCenter();
    }

}
