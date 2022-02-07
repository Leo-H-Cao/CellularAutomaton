package cellsociety.view;

import cellsociety.cell.Cell;
import cellsociety.cell.CellType;
import cellsociety.game.Game;
import cellsociety.io.XMLExport;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ViewController {
	private final GridManager gm;
	private static InformationPopup myInformationPopup;
	private final Controls controls;
	private BorderPane root;
	private static Stage stage;
	private static CellType selectedClickType;

	public ViewController(Stage _stage) {
		myInformationPopup = new InformationPopup();
		gm = new GridManager();
		controls = new Controls();

		stage = _stage;
		stage.setScene(makeScene(Game.getDefaultSize().width, Game.getDefaultSize().height));
		stage.show();
	}

	public void updateGridPane(Cell[][] cells) {
		GridManager.update(cells);
		root.setCenter(gm.getGrid());
	}

	public static void openPopup() {
		if (!myInformationPopup.getPopup().isShowing()) {
			myInformationPopup.getPopup().show(stage);
		}
	}

	public static CellType getSelectedClickType() {
		return selectedClickType;
	}

	public static void setSelectedClickType(CellType type) {
		selectedClickType = type;
	}

	private Scene makeScene(int width, int height) {
		root = new BorderPane();

		root.setCenter(gm.getGrid());
		root.setTop(makeTopDisplay());
		root.setBottom(controls.makeControls());

		Scene scene = new Scene(root, width, height);
		root.getStylesheets().add("stylesheet.css");
		return scene;
	}

	private Node makeTopDisplay() {
		GridPane ret = new GridPane();
		HBox leftBox = new HBox();
		HBox centerBox = new HBox();
		HBox rightBox = new HBox();
		FileChooser fileChooser = new FileChooser();

		Button importButton = makeButton(Game.getInterfaceProperties().getString("IMPORT"), e -> {
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				Game.importNewFile(file.toString());
				root.setBottom(controls.makeControls());
			}
		});
		Button exportButton = makeButton(Game.getInterfaceProperties().getString("EXPORT"), e -> {
			fileChooser.setTitle("Save Resource File");
			File file = fileChooser.showSaveDialog(stage);
			if (file != null) {
				XMLExport exporter = new XMLExport(file);
				exporter.saveToXML();
			}
		});

		Text appTitle = new Text(Game.getInterfaceProperties().getString("TITLE"));
		appTitle.setId("title");

		leftBox.getChildren().addAll(importButton, exportButton);
		leftBox.setAlignment(Pos.CENTER);
		HBox.setMargin(importButton, new Insets(20, 10, 20, 0));
		HBox.setMargin(exportButton, new Insets(20, 0, 20, 10));

		centerBox.getChildren().add(appTitle);
		centerBox.setAlignment(Pos.CENTER);

		rightBox.getChildren().add(myInformationPopup.getButton());
		rightBox.setAlignment(Pos.CENTER);

		ret.add(leftBox, 0, 0);
		ret.add(centerBox, 1, 0);
		ret.add(rightBox, 2, 0);

		for (int i = 0; i < 3; i++) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100.0 / 3.0);
			cc.setHgrow(Priority.ALWAYS);
			ret.getColumnConstraints().add(cc);
		}

		RowConstraints rc = new RowConstraints();
		rc.setVgrow(Priority.ALWAYS);
		ret.getRowConstraints().add(rc);

		return ret;
	}

	private Button makeButton(String label, EventHandler<ActionEvent> handler) {
		Button result = new Button();
		result.setText(label);
		result.setOnAction(handler);
		return result;
	}
}
