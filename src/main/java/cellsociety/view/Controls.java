package cellsociety.view;

import cellsociety.cell.CellType;
import cellsociety.game.Game;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class Controls {
	private final Button playButton;
	private final Button stepButton;
	private static GameCellMapping myGameCellMapping;

	public Controls() {
		myGameCellMapping = new GameCellMapping();
		playButton = makeButton(Game.getInterfaceProperties().getString("PLAY"), (e) -> {
			Game.toggleSimulation();
			togglePlayButtonState((Button) e.getSource());
		});
		stepButton = makeButton(Game.getInterfaceProperties().getString("STEP"), (e) -> Game.step());
	}

	public Node makeControls() {
		GridPane ret = new GridPane();
		HBox leftBox = new HBox();
		HBox centerBox = new HBox();
		HBox rightBox = new HBox();

		centerBox.getChildren().addAll(playButton, stepButton);
		centerBox.setAlignment(Pos.CENTER);
		HBox.setMargin(playButton, new Insets(0,10,20,0));
		HBox.setMargin(stepButton, new Insets(0,0,20,10));

		leftBox.getChildren().add(makeTypeSelector());
		leftBox.setAlignment(Pos.CENTER);

		Slider gameSpeedSlider = new Slider();

		gameSpeedSlider.setMin(Double.parseDouble(Game.getDefaultProperties().getString("MIN_GAME_SPEED")));
		gameSpeedSlider.setMax(Double.parseDouble(Game.getDefaultProperties().getString("MAX_GAME_SPEED")));
		gameSpeedSlider.setValue(Game.getCurrentGameSpeed());


		gameSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> Game.setSpeed(newValue.doubleValue()));

		rightBox.getChildren().add(gameSpeedSlider);
		rightBox.setAlignment(Pos.CENTER);

		ret.add(leftBox, 0, 0);
		ret.add(centerBox, 1, 0);
		ret.add(rightBox, 2, 0);

		for (int i = 0 ; i < 3 ; i++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100.0/3.0);
			cc.setHgrow(Priority.ALWAYS);
			ret.getColumnConstraints().add(cc);
		}

		RowConstraints rc = new RowConstraints();
		rc.setVgrow(Priority.ALWAYS);
		ret.getRowConstraints().add(rc);

		return ret;
	}

	private static void togglePlayButtonState(Button b) {
		if(b.getText().equals(Game.getInterfaceProperties().getString("PLAY"))) {
			b.setText(Game.getInterfaceProperties().getString("PAUSE"));
		}
		else {
			b.setText(Game.getInterfaceProperties().getString("PLAY"));
		}
	}

	private Button makeButton(String title, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		result.setText(title);
		result.setOnAction(handler);
		return result;
	}

	private Node makeTypeSelector() {
		GridPane ret = new GridPane();
		Label selectorTitle = new Label(Game.getInterfaceProperties().getString("SELECT_CELL_TYPE"));
		selectorTitle.setId("selector-title");

		ArrayList<CellType> selectionTypes = myGameCellMapping.MAP.get(Game.getCurrentGameType());

		ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(selectionTypes));

		choiceBox.setValue(selectionTypes.get(0).toString());
		ViewController.setSelectedClickType(selectionTypes.get(0));

		choiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> ViewController.setSelectedClickType(selectionTypes.get(new_value.intValue())));

		ret.add(selectorTitle, 0, 0);
		ret.add(choiceBox, 0, 1);

		return ret;
	}
}