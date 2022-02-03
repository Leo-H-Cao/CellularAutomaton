package cellsociety.cell;

import java.util.HashMap;

import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.*;

public class SchellingSegregation extends CellGrid {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        Cell[][] grid = getGrid();
        updatingGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), grid[i][j].getType());
                HashMap<String, Object> properties = grid[i][j].getProperties();
                properties.put("Moved", false);
                updatingGrid[i][j].setProperties(properties);
            }
        }
        updatePositons();
        setGrid(updatingGrid);
    }

    private void updatePositons() {
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                HashMap<String, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() != EMPTY && !(boolean) properties.get("Moved")) {
                    CELLTYPE type = updatingGrid[i][j].getType();
                    double fReal = fReal(CellGrid.getNeighbors(i, j), type);
                    if (fReal > 0.5) continue;
                    int d = bestDirection(getValidDirections(i, j, EMPTY), fReal);
                    updateGrid(d, i, j, type, properties);
                    if (d >= 0) updateGrid(-1, i, j, EMPTY, properties);

                }
            }
        }
    }

    private static double fReal(CELLTYPE[][] neighbors, CELLTYPE type) {
        double countA = type == A ? 1 : 0;
        double den = 0;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                if (neighbors[i][j] == A) countA++;
                if (neighbors[i][j] != NULL) den++;
            }
        }
        return type == A ? countA/8.0 : 1.0-(countA/den);
    }

    private static double[] getValidDirections(int x, int y, CELLTYPE type) {
        double[] validDirections = new double[8];
        if (inBounds(x-1, y-1) && updatingGrid[x-1][y-1].getType() == EMPTY) validDirections[0] = fReal(CellGrid.getNeighbors(x-1, y-1), type);
        if (inBounds(x, y-1) && updatingGrid[x][y-1].getType() == EMPTY) validDirections[1] = fReal(CellGrid.getNeighbors(x, y-1), type);
        if (inBounds(x+1, y-1) && updatingGrid[x+1][y-1].getType() == EMPTY) validDirections[2] = fReal(CellGrid.getNeighbors(x+1, y-1), type);
        if (inBounds(x-1, y) && updatingGrid[x-1][y].getType() == EMPTY) validDirections[3] = fReal(CellGrid.getNeighbors(x-1, y), type);
        if (inBounds(x+1, y) && updatingGrid[x+1][y].getType() == EMPTY) validDirections[4] = fReal(CellGrid.getNeighbors(x+1, y), type);
        if (inBounds(x-1, y+1) && updatingGrid[x-1][y+1].getType() == EMPTY) validDirections[5] = fReal(CellGrid.getNeighbors(x-1, y+1), type);
        if (inBounds(x, y+1) && updatingGrid[x][y+1].getType() == EMPTY) validDirections[6] = fReal(CellGrid.getNeighbors(x, y+1), type);
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

    private static void updateGrid(int d, int x, int y, CELLTYPE cType, HashMap<String, Object> properties) {
        properties.put("Moved", true);
        if (d == -1) updatingGrid[x][y].updateType(cType, properties);
        if (d == 0) updatingGrid[x-1][y-1].updateType(cType, properties);
        if (d == 1) updatingGrid[x][y-1].updateType(cType, properties);
        if (d == 2) updatingGrid[x+1][y-1].updateType(cType, properties);
        if (d == 3) updatingGrid[x-1][y].updateType(cType, properties);
        if (d == 4) updatingGrid[x+1][y].updateType(cType, properties);
        if (d == 5) updatingGrid[x-1][y+1].updateType(cType, properties);
        if (d == 6) updatingGrid[x][y+1].updateType(cType, properties);
        if (d == 7) updatingGrid[x+1][y+1].updateType(cType, properties);
    }

    private static boolean inBounds(int x, int y) {
        if (x < 0 || x >= updatingGrid.length || y < 0 || y >= updatingGrid[0].length) return false;
        return true;
    }
}
