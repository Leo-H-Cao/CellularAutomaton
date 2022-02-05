package cellsociety.view;

import cellsociety.cell.Cell;
import cellsociety.cell.CellType;
import cellsociety.game.Game;
import cellsociety.io.XMLExport;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ViewController {
	private Button importButton;
	private Button exportButton;
	private final GridManager gm;
	private Controls controls;
	private BorderPane root;
	private final Stage stage;
	private static CellType selectedClickType;

	public ViewController(Stage _stage) {
		gm = new GridManager();
		controls = new Controls();

		stage = _stage;
		stage.setScene(makeScene(Game.getDefaultSize().width, Game.getDefaultSize().height));
		stage.show();
	}

	public void updateGridPane(Cell[][] cells){
		gm.update(cells);
		root.setCenter(gm.getGrid());
	}

	public static CellType getSelectedClickType() {
		return selectedClickType;
	}

	public static void setSelectedClickType(CellType type) {
		selectedClickType = type;
	}

	private Scene makeScene (int width, int height) {
		root = new BorderPane();

		root.setCenter(gm.getGrid());
		root.setTop(makeTopDisplay());
		root.setBottom(controls.makeControls());

		Scene scene = new Scene(root, width, height);
		stage.titleProperty().bind(
				scene.widthProperty().asString().
						concat(" : ").
						concat(scene.heightProperty().asString()));
		return scene;
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
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(stage);
			if(file != null) {
				Game.importNewFile(file.toString());
				root.setBottom(controls.makeControls());
			}
		});
		exportButton = makeButton("Export", e -> {
			XMLExport exporter = new XMLExport();
			exporter.saveToXML();
		});

		buttonContainer.getChildren().addAll(importButton, exportButton);

		layout.add(buttonContainer, 0, 0);
		layout.add(appTitle, 1, 0);
		layout.add(spacerRight, 2, 0);

		HBox ret = new HBox();
		ret.getChildren().add(layout);

		return layout;
	}

	private Button makeButton (String label, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		result.setText(label);
		result.setOnAction(handler);
		return result;
	}
}
