package cellsociety.view;

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
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class Controls {
	private Button playButton;
	private Button stepButton;

	public Node makeControls() {
		GridPane ret = new GridPane();
		HBox leftBox = new HBox();
		HBox centerBox = new HBox();
		HBox rightBox = new HBox();
		playButton = makeButton("Play", (e) -> {
			Game.toggleSimulation();
			if(e.getTarget() instanceof Button) {
				Button b = (Button) e.getTarget();
				if(Game.getPlaying()) {
					b.setText("Pause");
				} else {
					b.setText("Play");
				}
			}
		});
		stepButton = makeButton("Step", (e) -> Game.step());

		centerBox.getChildren().addAll(playButton, stepButton);
		centerBox.setAlignment(Pos.CENTER);
		HBox.setMargin(playButton,new Insets(0,10,20,0));
		HBox.setMargin(stepButton,new Insets(0,0,20,10));

		Node typeSelector = makeTypeSelector();
		HBox.setMargin(typeSelector, new Insets(0,0,0,20));
		leftBox.getChildren().add(typeSelector);
		centerBox.setAlignment(Pos.CENTER);

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

	private Button makeButton(String title, EventHandler<ActionEvent> handler) {
		// represent all supported image suffixes
		Button result = new Button();
		result.setText(title);
		result.setOnAction(handler);
		return result;
	}

	private Node makeTypeSelector() {
		// create a tile pane
		TilePane r = new TilePane();

		// create a label
		Label l = new Label("This is a choice box");
		Label l1 = new Label("nothing selected");

		// string array
		String st[] = { "Arnab", "Andrew", "Ankit", "None" };

		// create a choiceBox
		ChoiceBox c = new ChoiceBox(FXCollections.observableArrayList(st));

		// add a listener
		// if the item of the list is changed
		c.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
			// set the text for the label to the selected item
			l1.setText(st[new_value.intValue()] + " selected");
		});

		// add ChoiceBox
		r.getChildren().add(l);
		r.getChildren().add(c);
		r.getChildren().add(l1);



		HBox ret = new HBox();
		Text defaultText = new Text();
		defaultText.setText("Click to select cell type");
		ret.setOnMouseClicked(System.out::println);

		ret.getChildren().add(defaultText);
		return r;
	}


}
