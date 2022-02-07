package cellsociety.view;

import cellsociety.cell.Cell;
import cellsociety.game.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.MissingResourceException;

public class GridManager {
	public static final String COLOR_MATCH_STRING = "%s_COLOR";
	private static GridPane grid;
	private static double cellWidth, cellHeight;
	private static double gridGap;

	public GridManager() {
		grid = new GridPane();
		gridGap = Double.parseDouble(Game.getDefaultProperties().getString("GRID_GAP"));
		grid.setHgap(gridGap);
		grid.setVgap(gridGap);
	}

	public static double[] getCellDimensions() {
		double[] ret = new double[2];
		ret[0] = cellWidth;
		ret[1] = cellHeight;
		return ret;
	}

	/**
	 * @return Centered grid
	 */
	public Node getGrid() {
		HBox ret = new HBox();
		ret.getChildren().add(grid);
		ret.setAlignment(Pos.CENTER);
		return ret;
	}

	/**
	 * Updates the GridPane using types and colors externally specified
	 *
	 * @param g Model representation of the displayed grid
	 */
	public static void update(Cell[][] g) {
		int controlsPadding = 175;
		cellWidth = ViewController.getWindowSize().width / (double) g.length - gridGap;
		cellHeight = (ViewController.getWindowSize().height - controlsPadding) / (double) g[0].length - gridGap;
		// reset grid
		grid.getChildren().clear();

		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g[0].length; j++) {
				CellNode c = new CellNode(g[i][j]);

				Color color;
				String type = g[i][j].getType().colorString();

				// First try to load a cell color from the current file
				try {
					color = Color.valueOf(Game.getCurrentFile().getGameData().get(String.format(COLOR_MATCH_STRING, type)));
				} catch (Exception e) {
					// If color is not defined, revert to default color defined in DEFAULT.properties
					System.out.printf("COLOR %s NOT FOUND IN CONFIGURATION, REVERTING TO DEFAULT%n", type );
					try {
						color = Color.valueOf(Game.getDefaultProperties().getString(String.format(COLOR_MATCH_STRING, type)));
					} catch (MissingResourceException colorNotDefinedException) {
						// If color hasn't been defined in DEFAULT.properties, revert to DEFAULT_COLOR
						String DEFAULT_COLOR = Game.getDefaultProperties().getString("DEFAULT_COLOR");
						System.out.printf((Game.getDefaultProperties().getString("MISSING_COLOR_EXCEPTION_MESSAGE")) + "%n", type, DEFAULT_COLOR);
						color = Color.valueOf(DEFAULT_COLOR);
					}
				}

				c.setColor(color);

				grid.add(c.getNode(), i, j);
			}
		}
	}
}
