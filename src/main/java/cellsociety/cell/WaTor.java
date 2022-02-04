package cellsociety.cell;

import cellsociety.game.Game;

import java.util.HashMap;
import java.util.Map;

import static cellsociety.cell.CellProperties.*;
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
public class WaTor extends CellGridME {

    private static Cell[][] updatingGrid;

    @Override
    public void nextGeneration() {
        updatingGrid = initializeUpdateGrid();
        updateSharks();
        updateFish();
        setGrid(updatingGrid);
    }

    private void updateSharks() {
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                Map<CellProperties, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() == SHARK && !(boolean)properties.get(MOVED)) {
                    if ((int)properties.get(ENERGY) == 0) {
                        updateGrid(updatingGrid, -1, i, j, EMPTY, properties);
                        continue;
                    }
                    int d = randomDirection(getValidDirections(i, j, FISH));
                    if (d >= 0) {
                        properties.put(ENERGY, (int)properties.get(ENERGY) + 1);
                    }
                    else {
                        d = randomDirection(getValidDirections(i, j, EMPTY));
                        properties.put(ENERGY, (int)properties.get(ENERGY) - 1);
                    }
                    move(d, i, j, properties, SHARK);
                }
            }
        }
    }

    private void updateFish() {
        for (int i = 0; i < updatingGrid.length; i++) {
            for (int j = 0; j < updatingGrid[0].length; j++) {
                Map<CellProperties, Object> properties = updatingGrid[i][j].getProperties();
                if (updatingGrid[i][j].getType() == FISH && !(boolean) properties.get(MOVED)) {
                    int d = randomDirection(getValidDirections(i, j, EMPTY));
                    move(d, i, j, properties, FISH);
                }
            }
        }
    }

    private static boolean[] getValidDirections(int x, int y, CellType destType) {
        boolean[] validDirections = new boolean[Integer.parseInt(Game.getProperties().getString("SQUARE_NEIGHBORS_COUNT"))];
        if (inBounds(x, y-1) && updatingGrid[x][y-1].getType() == destType) validDirections[1] = true;
        if (inBounds(x-1, y) && updatingGrid[x-1][y].getType() == destType) validDirections[3] = true;
        if (inBounds(x+1, y) && updatingGrid[x+1][y].getType() == destType) validDirections[4] = true;
        if (inBounds(x, y+1) && updatingGrid[x][y+1].getType() == destType) validDirections[6] = true;
        return validDirections;
    }

    private static int randomDirection(boolean[] validDirections) {
        int count = 0;
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i]) {
                count++;
            }
        }
        int random = (int)(Math.random() * count);
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i] && random > 0) {
                random--;
            }
            else if (validDirections[i] && random == 0) {
                return i;
            }
        }
        return -1;
    }

    private void move(int d, int i, int j, Map<CellProperties, Object> properties, CellType cType) {
        boolean reproduce = checkReproduction(properties);
        updateGrid(updatingGrid, d, i, j, cType, properties);
        if (reproduce) {
            properties = resetProperties();
            updateGrid(updatingGrid, -1, i, j, cType, properties);
        }
        else if (d >= 0) {
            updateGrid(updatingGrid, -1, i, j, EMPTY, properties);
        }
    }

    private static boolean checkReproduction(Map<CellProperties, Object> properties) {
        if ((int) properties.get(REPRODUCE) > 5) {
            properties.put(REPRODUCE, 0);
            return true;
        }
        properties.put(REPRODUCE, (int) properties.get(REPRODUCE) + 1);
        return false;
    }

    private static Map<CellProperties, Object> resetProperties() {
        Map<CellProperties, Object> props = new HashMap<CellProperties, Object>();
        props.put(MOVED, false);
        props.put(REPRODUCE, 0);
        props.put(ENERGY, 5);
        return props;
    }

}
