package cellsociety.cell;

import java.util.HashMap;

import static cellsociety.cell.CellType.*;

/**
 * This is the Cell type for WaTor
 *
 * @author Zack Schrage
 */
public class WaTorCell extends Cell {

    public WaTorCell(int x, int y, CellType cType) {
        super(x, y, cType);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Moved", false);
        map.put("Reproduce", 0);
        map.put("Energy", 5);
        setProperties(map);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
