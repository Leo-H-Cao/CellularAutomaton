package cellsociety.view;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellNode {
	private int x, y, width, height;
	private Color color;
	private Rectangle cell;

	public CellNode(double x, double y, double width, double height) {
		x = Math.round(x);
		y = Math.round(y);
		width = Math.round(width);
		height = Math.round(height);
		cell = new Rectangle(x, y, width, height);
	}

	public void setColor(Color c) {
		color = c;
	}

	public Node getNode() {
		return cell;
	}
}
