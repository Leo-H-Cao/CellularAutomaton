package cellsociety;

import cellsociety.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        new Game(stage);
    }
}
