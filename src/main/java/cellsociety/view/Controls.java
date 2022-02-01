package cellsociety.view;

import cellsociety.game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

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
		stepButton = makeButton("Step", (e) -> {
			Game.step();
		});

		centerBox.getChildren().addAll(playButton, stepButton);

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
}
