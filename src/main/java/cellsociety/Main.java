package cellsociety;

import cellsociety.View.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Society of Cells";
    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        ViewController UI = new ViewController();

        stage.setScene(UI.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));

        stage.setTitle(TITLE);
        stage.show();
    }
}
