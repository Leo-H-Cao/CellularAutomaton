package cellsociety.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameCellMapping {
	private static HashMap<Type.GAMETYPE, ArrayList<Type.CELLTYPE>> mapping;

	public GameCellMapping() {
		mapping.put(Type.GAMETYPE.GAMEOFLIFE, new ArrayList<>(Arrays.asList(Type.CELLTYPE.DEAD, Type.CELLTYPE.ALIVE)));
		mapping.put(Type.GAMETYPE.GAMEOFLIFE, new ArrayList<>(Arrays.asList(Type.CELLTYPE.DEAD, Type.CELLTYPE.ALIVE)));
	}
}
