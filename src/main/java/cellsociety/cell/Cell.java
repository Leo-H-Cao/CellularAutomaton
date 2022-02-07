package cellsociety.cell;

import cellsociety.game.GameType;
import static cellsociety.cell.CellType.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class outlines the primary attributes of a Cell:
 * position (xy coordinate)
 * type (game specific status)
 * properties (cell attributes for more complex gameplay)
 *
 * @author Zack Schrage
 */
public class Cell {

    private final int x;
    private final int y;
    private CellType cType;
    private Map<CellProperties, Object> properties;

    public Cell(int x, int y, CellType cType) {
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
     * @return a new cell of the specified game and cell type
     */
    public static Cell newGameCell(int x, int y, GameType gType, CellType cType) {
	    return switch (gType) {
		    case GAMEOFLIFE -> new GameOfLifeCell(x, y, cType);
		    case FIRE -> new FireCell(x, y, cType);
		    case PERCOLATION -> new PercolationCell(x, y, cType);
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
    public CellType getType() {
        return cType;
    }

    /**
     * Setter Method to update a cells type
     * @param cType a cells new type
     */
    public void updateType(CellType cType) {
        this.cType = cType;
    }

    /**
     * Setter Method to update a cells type and modify internal properties by passing arguments
     * @param cType a cells new type
     * @param properties array of properties
     */
    public void updateType(CellType cType, Map<CellProperties, Object> properties) {
        this.cType = cType;
        this.properties = properties;
    }

    /**
     * Getter method for a cells private properties
     * @return properties array of a cells objects
     */
    public Map<CellProperties, Object> getProperties() {
        return properties;
    }

    /**
     * Setter method for a cells private properties
     * @param properties array of a cells objects
     */
    public void setProperties(Map<CellProperties, Object> properties) {
        this.properties = properties;
    }

    /**
     * Getter method for a cells default type upon initialization
     * @return a cells default type
     */
    public CellType getDefault() {
        return NULL;
    }

}
