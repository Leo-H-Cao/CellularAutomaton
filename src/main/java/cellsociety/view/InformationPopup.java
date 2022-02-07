package cellsociety.view;

import cellsociety.game.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;


public class InformationPopup {
	private final Popup popup;

	public InformationPopup() {
		popup = new Popup();
		popup.setAutoHide(true);
		popup.getContent().add(makePopup());
	}

	public Popup getPopup() {
		return popup;
	}

	private Node makePopup() {
		StackPane ret = new StackPane();
		Rectangle background = new Rectangle(250,250, Color.LIGHTGRAY);
		GridPane grid = new GridPane();
		Text author = new Text();
		author.setText(Game.getCurrentFile().getGameData().get("Author"));

		Rectangle close = new Rectangle(40,30, Color.RED);
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> popup.hide());

		StackPane.setAlignment(close, Pos.TOP_RIGHT);

		Text description = new Text();
		description.setText("DESCRIPTION");

		grid.add(author, 0, 0);
		grid.add(description, 0, 1);

		grid.setAlignment(Pos.CENTER);

		ret.getChildren().addAll(background, grid, close);


		return ret;
	}

	public Node getButton() {
		Button ret = new Button(Game.getInterfaceProperties().getString("INFORMATION"));
		ret.setOnAction((e) -> ViewController.openPopup());
		ret.setId("info-button");
		return ret;
	}
}
