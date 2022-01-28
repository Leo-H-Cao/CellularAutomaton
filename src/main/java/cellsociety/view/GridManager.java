package cellsociety.view;

import cellsociety.Main;
import cellsociety.cell.Cell;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import static cellsociety.cell.Type.CELLTYPE.ALIVE;

public class GridManager {
	private static GridPane grid;

	public GridManager() {
		grid = new GridPane();
		grid.setHgap(2);
		grid.setVgap(2);
	}


	public Node getGrid() {
		return grid;
	}

	public void update(Cell[][] g) {
		int cellWidth = Math.round(Main.DEFAULT_SIZE.width / g.length);
		int cellHeight = Math.round(Main.DEFAULT_SIZE.height / g[0].length);
		// reset grid
		grid.getChildren().clear();


		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[0].length; j++) {
				CellNode c = new CellNode(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
				if (g[i][j].getType() == ALIVE) {
					c.setColor(Color.BLUE);
				} else {
					c.setColor(Color.BLACK);
				}
				grid.add(c.getNode(), j, i);
			}
		}
	}
}
