package cellsociety.cell;

import java.util.HashMap;
import java.util.Map;

import static cellsociety.cell.CellType.*;
import static cellsociety.cell.CellProperties.MOVED;

/**
 * This is the Cell type for Schelling Segregation
 *
 * @author Zack Schrage
 */
public class SchellingSegCell extends Cell {

    public SchellingSegCell(int x, int y, CellType cType) {
        super(x, y, cType);
        Map<CellProperties, Object> map = new HashMap<>();
        map.put(MOVED, false);
        setProperties(map);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
