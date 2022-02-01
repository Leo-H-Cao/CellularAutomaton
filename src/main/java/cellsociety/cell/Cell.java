package cellsociety.cell;

import cellsociety.cell.Type.CELLTYPE;
import cellsociety.cell.Type.GAMETYPE;

import java.util.HashMap;

/**
 * This class outlines the primary attributes of a Cell: position (xy coordinate) and type (game specific)
 * Different games will have specialized cells that extend this class.
 *
 * @author Zack Schrage
 */
public abstract class Cell {

    private int x;
    private int y;
    private CELLTYPE cType;
    private HashMap<String, Object> properties;

    public Cell(int x, int y, CELLTYPE cType) {
        this.x = x;
        this.y = y;
        this.cType = cType;
        properties = new HashMap<String, Object>();
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
     * @param cType a cells new type
     */
    public void updateType(CELLTYPE cType) {
        this.cType = cType;
    }

    /**
     * Setter Method to update a cells type and modify internal properties by passing arguments
     * @param cType a cells new type
     * @param properties array of properties
     */
    public void updateType(CELLTYPE cType, HashMap<String, Object> properties) {
        this.cType = cType;
        this.properties = properties;
    }

    /**
     * Getter method for a cells private properties
     * @return properties array of a cells objects
     */
    public HashMap<String, Object> getProperties() {
        return properties;
    }

    /**
     * Setter method for a cells private properties
     * @param properties array of a cells objects
     */
    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Getter method for a cells default type upon initialization
     * @return a cells default type
     */
    public abstract CELLTYPE getDefault();

}
