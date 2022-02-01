package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import java.util.HashMap;

import static cellsociety.cell.Type.CELLTYPE.*;
import static cellsociety.cell.Type.CELLTYPE.SHARK;

/**
 * This is the Cell Grid Manager for WaTor, its next generation method follows the rules that:
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
public class CellGridWaTor extends CellGrid {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        //Copy Grid to an updating grid
        Cell[][] grid = getGrid();
        updatingGrid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                updatingGrid[i][j] = Cell.newGameCell(i, j, getGameType(), grid[i][j].getType());
                HashMap<String, Object> props = grid[i][j].getProperties();
                props.put("Moved", false);
                updatingGrid[i][j].setProperties(props);
            }
        }
        //Update Sharks Positions
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                HashMap<String, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() == SHARK && !(boolean)properties.get("Moved")) {
                    if ((int)properties.get("Energy") == 0) {
                        updateGrid(-1, i, j, EMPTY);
                        continue;
                    }
                    int d = randomDirection(getValidDirections(i, j, FISH));
                    if (d >= 0) {
                        properties.put("Energy", (int)properties.get("Energy") + 1);
                    }
                    else d = randomDirection(getValidDirections(i, j, EMPTY));
                    //Update Properties
                    properties.put("Reproduce", (int)properties.get("Reproduce") + 1);
                    properties.put("Energy", (int)properties.get("Energy") - 1);
                    updatingGrid[i][j].setProperties(properties);
                    System.out.println("STAGE B " + (boolean)updatingGrid[i][j].getProperties().get("Moved") + " " + (int)updatingGrid[i][j].getProperties().get("Reproduce") + " " + (int)updatingGrid[i][j].getProperties().get("Energy"));
                    System.out.println();
                    //Update position
                    updateGrid(d, i, j, SHARK);
                    if (d >=0 && (int)properties.get("Reproduce") < 5) updateGrid(-1, i, j, EMPTY);
                }
            }
        }
        //Update Fish Positions
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                HashMap<String, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() == FISH && !(boolean)properties.get("Moved")) {
                    int d = randomDirection(getValidDirections(i, j, EMPTY));
                    //Update Properties
                    properties.put("Reproduce", (int)properties.get("Reproduce") + 1);
                    updatingGrid[i][j].setProperties(properties);
                    //Update position
                    updateGrid(d, i, j, FISH);
                    if (d >=0 && (int)properties.get("Reproduce") < 5) updateGrid(-1, i, j, EMPTY);
                }
            }
        }
        setGrid(updatingGrid);
    }

    private static boolean[] getValidDirections(int x, int y, Type.CELLTYPE destType) {
        boolean[] validDirections = new boolean[4];
        if (inBounds(x, y-1, updatingGrid) && updatingGrid[x][y-1].getType() == destType) validDirections[0] = true;
        if (inBounds(x-1, y, updatingGrid) && updatingGrid[x-1][y].getType() == destType) validDirections[1] = true;
        if (inBounds(x+1, y, updatingGrid) && updatingGrid[x+1][y].getType() == destType) validDirections[2] = true;
        if (inBounds(x, y+1, updatingGrid) && updatingGrid[x][y+1].getType() == destType) validDirections[3] = true;
        return validDirections;
    }

    private static int randomDirection(boolean[] validDirections) {
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

    private static void updateGrid(int d, int x, int y, Type.CELLTYPE cType) {
        HashMap<String, Object> properties = updatingGrid[x][y].getProperties();
        properties.put("Moved", true);
        if (d == -1) updatingGrid[x][y].updateType(cType, properties);
        else if (d == 0) updatingGrid[x][y-1].updateType(cType, properties);
        else if (d == 1) updatingGrid[x-1][y].updateType(cType, properties);
        else if (d == 2) updatingGrid[x+1][y].updateType(cType, properties);
        else if (d == 3) updatingGrid[x][y+1].updateType(cType, properties);
    }

    private static boolean inBounds(int x, int y, Cell[][] updatingGrid) {
        if (x < 0 || x >= updatingGrid.length || y < 0 || y >= updatingGrid[0].length) return false;
        return true;
    }

}

class WaTorCell extends Cell {

    public WaTorCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Moved", false);
        map.put("Reproduce", 0);
        map.put("Energy", 5);
        setProperties(map);
    }

    @Override
    public CELLTYPE getDefault() {
        return EMPTY;
    }

}
