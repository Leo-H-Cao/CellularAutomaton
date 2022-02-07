package cellsociety.view;

import cellsociety.cell.Cell;
import cellsociety.game.Game;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellNode extends Node {

	private final Rectangle rect;
	private final Cell cell;

	/**
	 * @param c Pointer to the cell that this cellNode represents
	 */
	public CellNode(Cell c) {
		int[] dimensions = GridManager.getCellDimensions();
		double width = dimensions[0];
		double height = dimensions[1];
		cell = c;
		rect = new Rectangle(c.getX(), c.getY(), width, height);
		rect.setOnMouseClicked(e -> handleMouseEvent());
	}

	public void setColor(Color c) {
		rect.setFill(c);
	}

	public Node getNode() {
		return rect;
	}

	private void handleMouseEvent() {
		cell.updateType(ViewController.getSelectedClickType());
		Game.renderGrid();
	}
}
