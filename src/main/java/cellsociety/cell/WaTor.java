package cellsociety.cell;

import cellsociety.game.Game;

import java.util.HashMap;
import java.util.Map;

import static cellsociety.cell.CellProperties.*;
import static cellsociety.cell.CellType.*;
import static cellsociety.game.NeighborhoodType.*;

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
    private static int reproductionCounter;
    private static int energyCounter;

    public WaTor(int reproduction, int energy){
        reproductionCounter = reproduction;
        energyCounter = energy;
    }

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
                        updateGrid(updatingGrid, getNeighborhoodCenter(), i, j, EMPTY, properties);
                        continue;
                    }
                    int d = randomDirection(getValidDirections(i, j, FISH));
                    if (d != getNeighborhoodCenter()) {
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
        return switch (getNeighborhoodType()) {
            case SQUARE_MOORE, SQUARE_NEUMANN -> getValidDirectionsSquare(CellGrid.getNeighbors(x, y, updatingGrid), destType, getNeighborhoodType() == SQUARE_NEUMANN);
            case TRIANGULAR_MOORE -> getValidDirectionsTriangular(CellGrid.getNeighbors(x, y, updatingGrid), destType);
            case TRIANGULAR_NEUMANN -> getValidDirectionsTriangularNeumann(CellGrid.getNeighbors(x, y, updatingGrid), destType, (x + y) % 2 == 0);
        };
    }

    private static boolean[] getValidDirectionsSquare(CellType[][] neighborsType, CellType destType, boolean isNeumann) {
        boolean[] validDirections = new boolean[Integer.parseInt(Game.getDefaultProperties().getString("SQUARE_NEIGHBORS_COUNT"))];
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j = 0; j < neighborsType[0].length; j++) {
                if (isNeumann && (i+j)%2 == 0) continue;
                if (neighborsType[i][j] == destType) validDirections[(i*neighborsType.length)+j] = true;
            }
        }
        return validDirections;
    }

    private static boolean[] getValidDirectionsTriangular(CellType[][] neighborsType, CellType destType) {
        boolean[] validDirections = new boolean[Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_COUNT"))];
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j = 0; j < neighborsType[0].length; j++) {
                if (neighborsType[i][j] == destType) validDirections[(i*neighborsType[0].length)+j] = true;
            }
        }
        return validDirections;
    }

    private static boolean[] getValidDirectionsTriangularNeumann(CellType[][] neighborsType, CellType destType, boolean orientDown) {
        boolean[] validDirections = new boolean[Integer.parseInt(Game.getDefaultProperties().getString("TRIANGLE_NEIGHBORS_COUNT"))];
        if (neighborsType[1][1] == destType) validDirections[(neighborsType.length)+1] = true;
        if (neighborsType[1][3] == destType) validDirections[(neighborsType.length)+3] = true;
        if (orientDown && neighborsType[0][2] == destType) validDirections[(0)+2] = true;
        if (!orientDown && neighborsType[2][2] == destType) validDirections[(2*neighborsType.length)+2] = true;
        return validDirections;
    }

    private static int randomDirection(boolean[] validDirections) {
        int count = 0;
        for (boolean validDirection : validDirections) {
            if (validDirection) {
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
        return getNeighborhoodCenter();
    }

    private void move(int d, int i, int j, Map<CellProperties, Object> properties, CellType cType) {
        boolean reproduce = checkReproduction(properties);
        updateGrid(updatingGrid, d, i, j, cType, properties);
        if (reproduce) {
            properties = resetProperties();
            updateGrid(updatingGrid, getNeighborhoodCenter(), i, j, cType, properties);
        }
        else if (d != getNeighborhoodCenter()) {
            updateGrid(updatingGrid, getNeighborhoodCenter(), i, j, EMPTY, properties);
        }
    }

    private static boolean checkReproduction(Map<CellProperties, Object> properties) {
        if ((int) properties.get(REPRODUCE) > reproductionCounter) {
            properties.put(REPRODUCE, 0);
            return true;
        }
        properties.put(REPRODUCE, (int) properties.get(REPRODUCE) + 1);
        return false;
    }

    private static Map<CellProperties, Object> resetProperties() {
        Map<CellProperties, Object> props = new HashMap<>();
        props.put(MOVED, false);
        props.put(REPRODUCE, 0);
        props.put(ENERGY, energyCounter);
        return props;
    }

}
