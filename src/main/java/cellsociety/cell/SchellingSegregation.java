package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import static cellsociety.cell.Type.CELLTYPE.*;

public class SchellingSegregation extends CellGrid {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        //Copy Grid to an updating grid
        Cell[][] grid = getGrid();
        updatingGrid = new Cell[grid.length][grid[0].length];
//        for (int i = 0; i < updatingGrid.length; i++) {
//            for (int j = 0; j < updatingGrid[0].length; j++) {
//                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), grid[i][j].getType());
//                Object[] props = grid[i][j].getProperties();
//                props[0] = false;
//                updatingGrid[i][j].setProperties(props);
//            }
//        }
        //Update Stuff Here
        //!!!
        setGrid(updatingGrid);
    }

    private static double fReal(CELLTYPE[][] neighbors, CELLTYPE type) {
        int countA = type == A ? 1 : 0;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                if (neighbors[0][0] == A) countA++;
            }
        }
        return type == A ? (double)countA/8.0 : 1.0-((double)countA/8.0);
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

    private static void updateGrid(int d, int x, int y, Type.CELLTYPE cType) {
        if (d == 0) updatingGrid[x-1][y-1].updateType(cType);
        if (d == 1) updatingGrid[x][y-1].updateType(cType);
        if (d == 2) updatingGrid[x+1][y-1].updateType(cType);
        if (d == 3) updatingGrid[x-1][y].updateType(cType);
        if (d == 4) updatingGrid[x+1][y].updateType(cType);
        if (d == 5) updatingGrid[x-1][y+1].updateType(cType);
        if (d == 6) updatingGrid[x][y+1].updateType(cType);
        if (d == 7) updatingGrid[x+1][y+1].updateType(cType);
    }

    private static boolean inBounds(int x, int y) {
        if (x < 0 || x >= updatingGrid.length || y < 0 || y >= updatingGrid[0].length) return false;
        return true;
    }
}
