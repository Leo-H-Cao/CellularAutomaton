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
    private int energy = 0;

    public WaTorCell(int x, int y, CELLTYPE cType) {
        super(x, y, cType);
    }

    @Override
    public CELLTYPE nextGeneration(CELLTYPE[][] neighborsType) {
        if (getType() == FISH) {

        }
        else if (getType() == SHARK) {

        }
        return EMPTY;
    }

    @Override
    public CELLTYPE getDefault() {
        return EMPTY;
    }

    @Override
    public void updateType(CELLTYPE cType) {
        updateType(cType);
        if (cType == FISH) reproductionTimer = 0;
        else if (cType == SHARK) energy = 0;
    }

    private int returnFish(CELLTYPE[][] neighborsType) {
        int fish = 0;
        for (int i = 0; i < neighborsType.length; i++) {
            for (int j= 0; j < neighborsType[0].length; j++) {
                if (neighborsType[i][j] == FISH) fish++;
            }
        }
        return fish;
    }

}
