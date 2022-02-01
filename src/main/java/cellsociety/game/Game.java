package cellsociety.game;

import cellsociety.cell.CellGrid;
import cellsociety.cell.CellGridFire;
import cellsociety.cell.CellGridGOL;
import cellsociety.cell.CellGridSchellingSeg;
import cellsociety.cell.CellGridWaTor;
import cellsociety.cell.Type.GAMETYPE;
import cellsociety.io.FileReader;
import cellsociety.io.PropertiesLoader;
import cellsociety.view.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Game {
	private static boolean playing = false;

    private static Timeline animation;
    private static CellGrid cellGrid;
    private static ViewController viewController;

    public Game(double SECOND_DELAY, Stage stage) {
        PropertiesLoader pl = new PropertiesLoader();
        try {
            pl.readPropValues();
        } catch (IOException e) {

        }
        viewController = new ViewController(stage);
        init();
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step()));
    }

	public static boolean getPlaying() {
		return playing;
	}

	public static void toggleSimulation() {
		if(playing) {
			animation.pause();
		} else {
			animation.play();
		}
		playing = !playing;
	}

    private void init() {
        FileReader f = new FileReader();
        f.parseFile("data/SampleComfig1.xml");
        cellGrid  = new CellGridFire();
//        cellGrid = new CellGridWaTor();
        cellGrid.initializeGrid(Integer.parseInt(f.getGameData().get("Width")), Integer.parseInt(f.getGameData().get("Height")), GAMETYPE.FIRE);
        cellGrid.initializeCells(f.getInitialState());
        viewController.updateGridPane(cellGrid.getGrid());
    }

	public static void renderGrid() {
		viewController.updateGridPane(cellGrid.getGrid());
	}

    public static void step() {
	    //Updates the 2D Array in Cell
	    cellGrid.nextGeneration();

	    // Display current cellGrid
	    renderGrid();
    }
}
