package cellsociety.cell;

import cellsociety.game.Game;

import java.util.Map;

import static cellsociety.cell.CellProperties.MOVED;
import static cellsociety.cell.CellType.*;

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
    private static double fIdeal = 0.5;

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
                    double fReal = fReal(CellGrid.getNeighbors(i, j), type);
                    if (fReal > fIdeal) {
                        continue;
                    }
                    int d = bestDirection(getValidDirections(i, j, EMPTY), fReal);
                    updateGrid(updatingGrid, d, i, j, type, properties);
                    if (d >= 0) {
                        updateGrid(updatingGrid,-1, i, j, EMPTY, properties);
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
                if (neighbors[i][j] != NULL) den++;
            }
        }
        return type == A ? countA/den : 1.0-(countA/den);
    }

    private static double[] getValidDirections(int x, int y, CellType destType) {
        switch(getNeighborhoodType()) {
            case SQUARE_MOORE:
                return getValidDirectionsSquare(x, y, destType, false);
            case SQUARE_NEUMANN, default:
                return getValidDirectionsSquare(x, y, destType, true);
        }
    }

    private static double[] getValidDirectionsSquare(int x, int y, CellType type, boolean neumannSquare) {
        double[] validDirections = new double[Integer.parseInt(Game.getDefaultProperties().getString("SQUARE_NEIGHBORS_COUNT"))];
        if (inBounds(x, y-1) && updatingGrid[x][y-1].getType() == EMPTY) validDirections[1] = fReal(CellGrid.getNeighbors(x, y-1), type);
        if (inBounds(x-1, y) && updatingGrid[x-1][y].getType() == EMPTY) validDirections[3] = fReal(CellGrid.getNeighbors(x-1, y), type);
        if (inBounds(x+1, y) && updatingGrid[x+1][y].getType() == EMPTY) validDirections[4] = fReal(CellGrid.getNeighbors(x+1, y), type);
        if (inBounds(x, y+1) && updatingGrid[x][y+1].getType() == EMPTY) validDirections[6] = fReal(CellGrid.getNeighbors(x, y+1), type);
        if (neumannSquare) return validDirections;
        if (inBounds(x-1, y-1) && updatingGrid[x-1][y-1].getType() == EMPTY) validDirections[0] = fReal(CellGrid.getNeighbors(x-1, y-1), type);
        if (inBounds(x+1, y-1) && updatingGrid[x+1][y-1].getType() == EMPTY) validDirections[2] = fReal(CellGrid.getNeighbors(x+1, y-1), type);
        if (inBounds(x-1, y+1) && updatingGrid[x-1][y+1].getType() == EMPTY) validDirections[5] = fReal(CellGrid.getNeighbors(x-1, y+1), type);
        if (inBounds(x+1, y+1) && updatingGrid[x+1][y+1].getType() == EMPTY) validDirections[7] = fReal(CellGrid.getNeighbors(x+1, y+1), type);
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
        return -1;
    }

}
