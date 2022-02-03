package cellsociety.game;

import cellsociety.cell.*;
import cellsociety.io.FileReader;
import cellsociety.view.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ResourceBundle;

public class Game {
	private static boolean playing = false;
	private static GameType currentGameType;

		public static final String CONFIG_PROPERTIES_FILE = "config.properties";

    private static Timeline animation;
    private static CellGrid cellGrid;
    private static ViewController viewController;
	private static ResourceBundle myResources;
	private static Dimension DEFAULT_SIZE;

    public Game(double SECOND_DELAY, Stage stage) {

	    try {
		    myResources = ResourceBundle.getBundle("config");
	    } catch (Exception e) {
			e.printStackTrace();
        }

		DEFAULT_SIZE = new Dimension(Integer.parseInt(Game.getProperties().getString("DEFAULT_WIDTH")),
				Integer.parseInt(Game.getProperties().getString("DEFAULT_HEIGHT")));

        viewController = new ViewController(stage);
	    makeNewGrid("data/defaultGameState.xml");
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step()));
    }

	public static ResourceBundle getProperties() {
		return myResources;
	}

	public static boolean getPlaying() {
		return playing;
	}

	public static Dimension getDefaultSize() {
		return DEFAULT_SIZE;
	}

	public static GameType getCurrentGameType() {
		return currentGameType;
	}

	public static void toggleSimulation() {
		if(playing) {
			animation.pause();
		} else {
			animation.play();
		}
		playing = !playing;
	}

	public static void makeNewGrid(String filePath) {
		FileReader f = new FileReader();
		f.parseFile(filePath);
		currentGameType = f.getGameType();
		switch(currentGameType) {
			case GAMEOFLIFE -> cellGrid = new GameOfLife();
			case FIRE -> cellGrid = new Fire();
			case WATOR -> cellGrid = new WaTor();
			case PERCOLATION -> cellGrid = new Percolation();
			case SCHELLSEG -> cellGrid = new SchellingSegregation();
			case default -> cellGrid = null;
		}
		cellGrid.initializeGrid(Integer.parseInt(f.getGameData().get("Width")), Integer.parseInt(f.getGameData().get("Height")), currentGameType);
		cellGrid.initializeCells(f.getInitialState());
		renderGrid();
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
