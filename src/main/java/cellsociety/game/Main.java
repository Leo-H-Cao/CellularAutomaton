package cellsociety.game;

import cellsociety.io.UI;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

    public static final String TITLE = "Cell Society";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 1;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        new UI(stage);
        new Game(SECOND_DELAY);
    }
}
