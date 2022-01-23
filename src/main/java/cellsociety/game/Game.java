package cellsociety.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Game {

    private static Timeline animation;

    public Game(double SECOND_DELAY) {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();
    }

    private void step(double second_delay) {
        //Updates the 2D Array in Cell
        System.out.println("Test");
    }

}
