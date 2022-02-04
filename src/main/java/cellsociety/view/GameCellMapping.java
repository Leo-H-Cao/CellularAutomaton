package cellsociety.view;

import cellsociety.cell.CellType;
import cellsociety.game.GameType;

import java.util.*;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.GameType.*;

public class GameCellMapping {
	public final Map<GameType, ArrayList<CellType>> MAP;;

	public GameCellMapping() {
		MAP = new HashMap<>();
		MAP.put(GAMEOFLIFE, new ArrayList<>(Arrays.asList(DEAD, ALIVE)));
		MAP.put(FIRE, new ArrayList<>(Arrays.asList(EMPTY, BURNING, TREE)));
		MAP.put(PERCOLATION, new ArrayList<>(Arrays.asList(BLOCK, WATER)));
		MAP.put(WATOR, new ArrayList<>(Arrays.asList(EMPTY, FISH, SHARK)));
		MAP.put(SCHELLSEG, new ArrayList<>(Arrays.asList(EMPTY, A, B)));
	}
}
