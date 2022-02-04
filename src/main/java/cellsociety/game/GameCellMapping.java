package cellsociety.game;

import cellsociety.cell.CellType;

import java.util.*;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.GameType.*;

public class GameCellMapping {
	private static Map<GameType, List<CellType>> map;

	public GameCellMapping() {
		map = new HashMap<>();
		map.put(GAMEOFLIFE, new ArrayList<>(Arrays.asList(DEAD, ALIVE)));
		map.put(FIRE, new ArrayList<>(Arrays.asList(EMPTY, BURNING, TREE)));
		map.put(PERCOLATION, new ArrayList<>(Arrays.asList(BLOCK, WATER)));
		map.put(WATOR, new ArrayList<>(Arrays.asList(EMPTY, FISH, SHARK)));
		map.put(SCHELLSEG, new ArrayList<>(Arrays.asList(EMPTY, A, B)));
	}
}
