package cellsociety.view;

import cellsociety.Main;
import cellsociety.cell.Cell;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import static cellsociety.cell.Type.CELLTYPE.ALIVE;

public class GridManager {
	private static GridPane grid;
	public static final int GRIDGAP = 1;

	public GridManager() {
		grid = new GridPane();
		grid.setHgap(GRIDGAP);
		grid.setVgap(GRIDGAP);
	}


	public Node getGrid() {
		return grid;
	}

	public void update(Cell[][] g) {
		int verticalPadding = 100;
		int cellWidth = Integer.valueOf(Main.DEFAULT_SIZE.width / g.length) - GRIDGAP;
		int cellHeight = Math.round((Main.DEFAULT_SIZE.height - verticalPadding) / g[0].length) - GRIDGAP;
		// reset grid
		grid.getChildren().clear();


		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[0].length; j++) {
				CellNode c = new CellNode(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
				if (g[i][j].getType() == ALIVE) {
					c.setColor(Color.BLUE);
				} else {
					c.setColor(Color.GRAY);
				}
				grid.add(c.getNode(), j, i);
			}
		}
	}
}
