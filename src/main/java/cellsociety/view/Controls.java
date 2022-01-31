package cellsociety.view;

import cellsociety.game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Controls {
	private Button playButton;
	private Button stepButton;

	public Node makeControls() {
		HBox ret = new HBox();
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
		ret.getChildren().addAll(playButton, stepButton);

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
