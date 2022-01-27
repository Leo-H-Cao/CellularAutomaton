package cellsociety.View;

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
}
