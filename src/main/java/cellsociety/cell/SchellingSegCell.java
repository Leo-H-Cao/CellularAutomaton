package cellsociety.cell;

import java.util.HashMap;

import static cellsociety.cell.CellType.*;

/**
 * This is the Cell type for Schelling Segregation, its next generation method follows the rules that:
 * Agents desire a fraction F_ideal of their neighborhood (eight adjacent agents) to be from the same group as them
 * If the fraction of agents not including empty spaces F_real < F_ideal
 * Agents will attempt to relocate to a spot where F_real >= F_ideal
 *
 * @author Zack Schrage
 */
public class SchellingSegCell extends Cell {

    public SchellingSegCell(int x, int y, CellType cType) {
        super(x, y, cType);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Moved", false);
        setProperties(map);
    }

    @Override
    public CellType getDefault() {
        return EMPTY;
    }

}
