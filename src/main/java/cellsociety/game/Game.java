package cellsociety.game;

import cellsociety.cell.CellGrid;
import cellsociety.cell.Type.GAMETYPE;
import cellsociety.io.FileReader;
import cellsociety.view.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {
	private static boolean playing = false;

    private static Timeline animation;
    private static CellGrid cellGrid;
    private static ViewController viewController;

    public Game(double SECOND_DELAY, Stage stage) {
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

	public static CellGrid getCellGrid() {
		return cellGrid;
	}

    private void init() {
        FileReader f = new FileReader();
        f.parseFile("data/SampleComfig1.xml");
        cellGrid = new CellGrid();
        cellGrid.initializeGrid(Integer.parseInt(f.getGameData().get("Width")), Integer.parseInt(f.getGameData().get("Height")), GAMETYPE.GAMEOFLIFE);
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
