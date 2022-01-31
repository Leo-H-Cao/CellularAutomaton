package cellsociety.view;

import cellsociety.Main;
import cellsociety.cell.Cell;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static cellsociety.cell.Type.CELLTYPE.ALIVE;

public class GridManager {
	private static GridPane grid;
	private static final int GRID_GAP = 3;
	private static int cellWidth, cellHeight;

	public GridManager() {
		grid = new GridPane();
		grid.setHgap(GRID_GAP);
		grid.setVgap(GRID_GAP);
	}

	public static int[] getCellDimensions() {
		int[] ret = new int[2];
		ret[0] = cellWidth;
		ret[1] = cellHeight;
		return ret;
	}

	public Node getGrid() {
		return grid;
	}

	public void update(Cell[][] g) {
		int verticalPadding = 120;
		cellWidth = Integer.valueOf(Main.DEFAULT_SIZE.width / g.length) - GRID_GAP - 1;
		cellHeight = Math.round((Main.DEFAULT_SIZE.height - verticalPadding) / g[0].length) - GRID_GAP - 1;
		// reset grid
		grid.getChildren().clear();


		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[0].length; j++) {
				CellNode c = new CellNode(g[i][j]);
				if (g[i][j].getType() == ALIVE) {
					c.setColor(Color.BLUE);
				} else {
					c.setColor(Color.GRAY);
				}
				grid.add(c.getNode(), i, j);
			}
		}
	}
}
