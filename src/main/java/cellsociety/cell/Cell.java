package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import cellsociety.cell.Type.GAMETYPE;

/**
 * This class outlines the primary attributes of a Cell: position (xy coordinate) and type (game specific)
 * Different games will have specialized cells that extend this class.
 * Each cell must have a nextGeneration method that takes in its neighbors as a parameter
 * The nextGeneration method determines what its type of Cell it will be in the next generation
 *
 * @author Zack Schrage
 */
public abstract class Cell {

    private int x;
    private int y;
    private CELLTYPE cType;

    public Cell(int x, int y, CELLTYPE cType) {
        this.x = x;
        this.y = y;
        this.cType = cType;
    }

    /**
     * Constructs a cell subclass given a game type
     * @param x coordinate of cell
     * @param y coordinate of cell
     * @param gType game type of cell
     * @return
     */
    public static Cell newGameCell(int x, int y, GAMETYPE gType, CELLTYPE cType) {
        switch(gType) {
            case GAMEOFLIFE:
                return new GameOfLifeCell(x, y, cType);
            case FIRE:
                return new FireCell(x, y, cType);
            case WATOR:
                return new WaTorCell(x, y, cType);
            case SCHELLSEG:
                return new SchellingSegCell(x, y, cType);
        }
        return null;
    }

    /**
     * Getter method for a cells x position
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * Getter method for a cells y position
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * Getter method for a cells type
     * @return a cells type
     */
    public CELLTYPE getType() {
        return cType;
    }

    /**
     * Setter Method to update a cells type
     *
     * @param cType a cells new type
     */
    public void updateType(CELLTYPE cType) {
        this.cType = cType;
    }

    /**
     * Setter Method to update a cells type and modify internal properties by passing arguments
     *
     * @param cType a cells new type
     * @param properties array of properties
     */
    public void updateType(CELLTYPE cType, Object[] properties) {
        this.cType = cType;
    }

    /**
     * Each next generation is a function of the current generation and since the rules surrounding
     * what a cells type will be in the next generation is game dependent, each subclass will implement
     * its own rules dictating the game behavior
     *
     * @param updatingGrid a grid to update the next generation to
     */
    public abstract void nextGeneration(Cell[][] updatingGrid);

    /**
     * Getter method for a cells default type upon initialization
     * @return a cells default type
     */
    public abstract CELLTYPE getDefault();

}
