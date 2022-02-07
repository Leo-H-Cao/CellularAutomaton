package cellsociety.cell;

import cellsociety.game.Game;

/**
 * Enums for all the cell types within each game
 *
 * @author Zack Schrage
 */
public enum CellType {
	NULL,
	DEAD,
	ALIVE,
	EMPTY,
	TREE,
	BURNING,
	BLOCK,
	WATER,
	FISH,
	SHARK,
	A,
	B;

	@Override
	public String toString() {
		return Game.getInterfaceProperties().getString(super.toString());
	}

	public String colorString() {
		return super.toString();
	}
}
