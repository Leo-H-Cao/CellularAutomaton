package cellsociety.cell;

import java.util.HashMap;

import static cellsociety.cell.CellType.*;

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
public class WaTor extends CellGrid {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        updatingGrid = initializeUpdateGridME();
        updateSharks();
        updateFish();
        setGrid(updatingGrid);
    }

    private void updateSharks() {
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                HashMap<String, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() == SHARK && !(boolean)properties.get("Moved")) {
                    if ((int)properties.get("Energy") == 0) {
                        updateGrid(-1, i, j, EMPTY, properties);
                        continue;
                    }
                    int d = randomDirection(getValidDirections(i, j, FISH));
                    if (d >= 0) {
                        properties.put("Energy", (int)properties.get("Energy") + 3);
                    }
                    else d = randomDirection(getValidDirections(i, j, EMPTY));
                    //Update Properties
                    boolean reproduce = false;
                    properties.put("Reproduce", (int)properties.get("Reproduce") + 1);
                    properties.put("Energy", (int)properties.get("Energy") - 1);
                    if ((int) properties.get("Reproduce") > 5) {
                        properties.put("Reproduce", 0);
                        reproduce = true;
                    }
                    //Update position
                    updateGrid(d, i, j, SHARK, properties);
                    if (reproduce) {
                        HashMap<String, Object> props = new HashMap<String, Object>();
                        props.put("Moved", false);
                        props.put("Reproduce", 0);
                        props.put("Energy", 5);
                        updateGrid(-1, i, j, FISH, props);
                    }
                    else if (d >= 0) updateGrid(-1, i, j, EMPTY, properties);
                }
            }
        }
    }

    private void updateFish() {
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                HashMap<String, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() == FISH && !(boolean) properties.get("Moved")) {
                    int d = randomDirection(getValidDirections(i, j, EMPTY));
                    boolean reproduce = false;
                    properties.put("Reproduce", (int) properties.get("Reproduce") + 1);
                    if ((int) properties.get("Reproduce") > 5) {
                        properties.put("Reproduce", 0);
                        reproduce = true;
                    }
                    //Update position
                    updateGrid(d, i, j, FISH, properties);
                    if (reproduce) {
                        HashMap<String, Object> props = new HashMap<String, Object>();
                        props.put("Moved", false);
                        props.put("Reproduce", 0);
                        props.put("Energy", 5);
                        updateGrid(-1, i, j, FISH, props);
                    }
                    else if (d >= 0) updateGrid(-1, i, j, EMPTY, properties);

                }
            }
        }
    }

    private static boolean[] getValidDirections(int x, int y, CellType destType) {
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

    private static void updateGrid(int d, int x, int y, CellType cType, HashMap<String, Object> properties) {
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