package cellsociety.io;

import cellsociety.game.Main;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class UI {

    public UI(Stage stage) {
        Circle shape = new Circle(190, 190, 20);
        shape.setFill(Color.LIGHTSTEELBLUE);

        Group root = new Group();
        root.getChildren().add(shape);

        Scene scene = new Scene(root, Main.SIZE, Main.SIZE, Color.DARKBLUE);
        scene.setOnKeyPressed(e -> Input.handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> Input.handleKeyRelease(e.getCode()));
        scene.setOnMouseClicked(e -> Input.handleMouseInput(e.getX(), e.getY()));

        stage.setScene(scene);
        stage.setTitle(Main.TITLE);
        stage.show();
    }

}
