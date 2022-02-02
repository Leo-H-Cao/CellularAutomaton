package cellsociety.view;

import cellsociety.cell.Cell;
import cellsociety.game.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GridManager {
	private static GridPane grid;
	private static int cellWidth, cellHeight;
	private final double gridGap;

	public GridManager() {
		grid = new GridPane();
		gridGap = Double.parseDouble(Game.getProperties().getString("GRID_GAP"));
		grid.setHgap(gridGap);
		grid.setVgap(gridGap);
	}

	public static int[] getCellDimensions() {
		int[] ret = new int[2];
		ret[0] = cellWidth;
		ret[1] = cellHeight;
		return ret;
	}

	public Node getGrid() {
		HBox ret = new HBox();
		ret.getChildren().add(grid);
		ret.setAlignment(Pos.CENTER);
		return ret;
	}

	public void update(Cell[][] g) {
		int verticalPadding = 100;
		cellWidth = (int) (Game.DEFAULT_SIZE.width / g.length - gridGap - 1);
		cellHeight = (int) (Math.round((Game.DEFAULT_SIZE.height - verticalPadding) / g[0].length) - gridGap - 1);
		// reset grid
		grid.getChildren().clear();

		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[0].length; j++) {
				CellNode c = new CellNode(g[i][j]);
				switch (g[i][j].getType()) {
					case EMPTY:
					case DEAD:
						c.setColor(Color.BLACK);
						break;
					case ALIVE:
					case FISH:
					case A:
						c.setColor(Color.BLUE);
						break;
					case TREE:
						c.setColor(Color.GREEN);
						break;
					case BURNING:
						c.setColor(Color.YELLOW);
						break;
					case SHARK:
					case B:
						c.setColor(Color.RED);
						break;
					case NULL:
						c.setColor(Color.BROWN);
					default:
						c.setColor(Color.BLACK);
				}
				grid.add(c.getNode(), i, j);
			}
		}
	}
}
