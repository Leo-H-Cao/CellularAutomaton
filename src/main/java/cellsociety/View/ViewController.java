package cellsociety.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ViewController {
	private Button importButton;
	private Button exportButton;


	// TEMPORARY TESTING METHOD
	private GridManager initializeGridManager() {
		return new GridManager(10, 10);
	}


	public Scene makeScene (int width, int height) {
		BorderPane root = new BorderPane();


		// must be first since other panels may refer to page
		GridManager gm = initializeGridManager();
		root.setCenter(gm.getGrid());
		root.setTop(makeTopDisplay());
//		root.setBottom(makeInformationPanel());
		return new Scene(root, width, height);
	}

	private Node makeTopDisplay() {

		GridPane layout = new GridPane();

		for (int i = 0 ; i < 3 ; i++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100.0/3.0);
			cc.setHgrow(Priority.ALWAYS);
			layout.getColumnConstraints().add(cc);
		}

		Text appTitle = new Text("Society of Cells");
		appTitle.setFont(new Font("san-serif", 28));
		appTitle.setTextAlignment(TextAlignment.CENTER);
		HBox.setHgrow(appTitle, Priority.ALWAYS);

		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);

		Region spacerLeft = new Region();
		HBox.setHgrow(spacerLeft, Priority.ALWAYS);

		Region spacerRight = new Region();
		HBox.setHgrow(spacerRight, Priority.ALWAYS);


		importButton = makeButton("Import", e -> {
			System.out.println(e);
		});
		exportButton = makeButton("Export", e -> {
			System.out.println(e);
		});

		buttonContainer.getChildren().addAll(importButton, exportButton);

		layout.add(buttonContainer, 0, 0);
		layout.add(appTitle, 1, 0);
		layout.add(spacerRight, 2, 0);

		HBox ret = new HBox();
		ret.getChildren().add(layout);

		return layout;
	}


	// makes a button using either an image or a label
	private Button makeButton (String label, EventHandler<ActionEvent> handler) {
		// represent all supported image suffixes
		Button result = new Button();
		result.setText(label);
		result.setOnAction(handler);
		return result;
	}
}
