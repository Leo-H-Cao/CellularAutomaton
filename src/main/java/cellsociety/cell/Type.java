package cellsociety.cell;

/**
 * Enums for the different playable game types and all the cell types within each game
 *
 * @author Zack Schrage
 */
public enum Type {;

    public enum GAMETYPE {
        GAMEOFLIFE,
        FIRE,
        PERCOLATION,
        WATOR,
        SCHELLSEG
    }

    public enum CELLTYPE {
        NULL,
        DEAD,
        ALIVE,
        EMPTY,
        TREE,
        BURNING,
        BLOCK,
        WATER,
        FISH,
        SHARK,
        A,
        B
    }
}
