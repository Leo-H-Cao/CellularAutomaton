package cellsociety.cell;

import cellsociety.utils.Type.CELLTYPE;
import cellsociety.utils.Type.GAMETYPE;

import java.util.HashMap;

/**
 * This class outlines the primary attributes of a Cell: position (xy coordinate) and type (game specific)
 * Different games will have specialized cells that extend this class.
 *
 * @author Zack Schrage
 */
public abstract class Cell {

    private final int x;
    private final int y;
    private CELLTYPE cType;
    private HashMap<String, Object> properties;

    public Cell(int x, int y, CELLTYPE cType) {
        this.x = x;
        this.y = y;
        this.cType = cType;
        properties = new HashMap<>();
    }

    /**
     * Constructs a cell subclass given a game type
     * @param x coordinate of cell
     * @param y coordinate of cell
     * @param gType game type of cell
     * @return
     */
    public static Cell newGameCell(int x, int y, GAMETYPE gType, CELLTYPE cType) {
        return switch (gType) {
            case GAMEOFLIFE -> new GameOfLifeCell(x, y, cType);
            case FIRE -> new FireCell(x, y, cType);
            case WATOR -> new WaTorCell(x, y, cType);
            case SCHELLSEG -> new SchellingSegCell(x, y, cType);
        };
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
