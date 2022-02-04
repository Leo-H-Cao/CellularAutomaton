package cellsociety.view;

import cellsociety.cell.CellType;
import cellsociety.game.GameType;

import java.util.*;

import static cellsociety.cell.CellType.*;
import static cellsociety.game.GameType.*;

public class GameCellMapping {
	public final Map<GameType, ArrayList<CellType>> MAP;


	public GameCellMapping() {
		MAP = new HashMap<>();
		MAP.put(GAMEOFLIFE, new ArrayList<>(Arrays.asList(ALIVE, DEAD)));
		MAP.put(FIRE, new ArrayList<>(Arrays.asList(BURNING, TREE, EMPTY)));
		MAP.put(PERCOLATION, new ArrayList<>(Arrays.asList(WATER, BLOCK)));
		MAP.put(WATOR, new ArrayList<>(Arrays.asList(FISH, SHARK, EMPTY)));
		MAP.put(SCHELLSEG, new ArrayList<>(Arrays.asList(A, B, EMPTY)));
	}
}
