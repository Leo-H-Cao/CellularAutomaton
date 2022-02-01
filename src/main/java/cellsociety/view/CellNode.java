package cellsociety.view;

import cellsociety.cell.Cell;
import cellsociety.game.Game;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellNode extends Node {

	private final double width, height;
	private final Rectangle rect;
	private final Cell cell;

	public CellNode(Cell c) {
		int[] dimensions = GridManager.getCellDimensions();
		width = dimensions[0];
		height = dimensions[1];
		cell = c;
		rect = new Rectangle(c.getX(), c.getY(), width, height);
		rect.setOnMouseClicked(event -> {
			handleMouseEvent(event);
		});
	}

	public void setColor(Color c) {
		rect.setFill(c);
	}

	public Node getNode() {
		return rect;
	}

	private void handleMouseEvent(MouseEvent e) {
		cell.updateType(ViewController.getSelectedClickType());
		Game.renderGrid();
	}
}
