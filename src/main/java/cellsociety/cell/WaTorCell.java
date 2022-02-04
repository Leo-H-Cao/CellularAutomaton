package cellsociety.cell;

import java.util.HashMap;
import java.util.Map;

import static cellsociety.cell.CellType.*;
import static cellsociety.cell.CellProperties.*;

/**
 * This is the Cell type for WaTor
 *
 * @author Zack Schrage
 */
public class WaTorCell extends Cell {

    public WaTorCell(int x, int y, CellType cType) {
        super(x, y, cType);
        Map<CellProperties, Object> map = new HashMap<>();
        map.put(MOVED, false);
        map.put(REPRODUCE, 0);
        map.put(ENERGY, 5);
        setProperties(map);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
