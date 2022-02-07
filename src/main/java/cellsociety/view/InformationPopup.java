package cellsociety.view;

import cellsociety.game.Game;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.Arrays;


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

	/**
	 * @return Node that contains the extra information popup
	 */
	private Node makePopup() {
		StackPane ret = new StackPane();
		Rectangle background = new Rectangle(400,500, Color.LIGHTGRAY);
		GridPane grid = new GridPane();
		grid.setVgap(25);
		Label author = new Label();

		StringBuilder authorText = new StringBuilder();
		for(String s : Game.getCurrentFile().getAuthors()) {
			authorText.append(s).append("\n");
		}

		author.setText(authorText.toString());

		Rectangle close = new Rectangle(40,30, Color.RED);
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> popup.hide());

		StackPane.setAlignment(close, Pos.TOP_RIGHT);

		Label description = new Label();
		description.setMaxWidth(300);
		description.setWrapText(true);
		description.setText(Game.getCurrentFile().getGameData().get("Description"));

		grid.add(author, 0, 0);
		grid.add(description, 0, 1);
		grid.setAlignment(Pos.CENTER);

		ArrayList<String> selectionTypes = new ArrayList<>(Arrays.asList("en", "es", "fr"));

		ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(selectionTypes));

		choiceBox.setValue(selectionTypes.get(0));

		choiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) ->
				Game.setLocale(selectionTypes.get(new_value.intValue())));

		StackPane.setAlignment(choiceBox, Pos.BOTTOM_LEFT);

		ret.getChildren().addAll(background, grid, close, choiceBox);


		return ret;
	}

	/**
	 * @return Node that will show the information popup when clicked
	 */
	public Node getButton() {
		Button ret = new Button(Game.getInterfaceProperties().getString("INFORMATION"));
		ret.setOnAction((e) -> ViewController.openPopup());
		ret.setId("info-button");
		return ret;
	}
}
