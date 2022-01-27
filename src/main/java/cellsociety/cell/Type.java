package cellsociety.cell;

/**
 * Enums for the different playable game types and all the cell types within each game
 *
 * @author Zack Schrage
 */
public enum Type {;

    enum GAMETYPE {
        GAMEOFLIFE,
        FIRE,
        WATOR
    }

    enum CELLTYPE {
        NULL,
        DEAD,
        ALIVE,
        TREE,
        EMPTY,
        BURNING,
        FISH,
        SHARK
    }

}
