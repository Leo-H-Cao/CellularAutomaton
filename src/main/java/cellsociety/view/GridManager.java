package cellsociety.view;

import cellsociety.cell.Cell;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridManager {
	private static GridPane grid;

	public GridManager(int x, int y) {
		grid = new GridPane();
		grid.setHgap(2);
		grid.setVgap(2);
	}


	public Node getGrid() {
		return grid;
	}

	public void update(Cell[][] cells) {
		// reset grid
		grid.getChildren().clear();

	}
}
