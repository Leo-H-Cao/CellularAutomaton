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
	private static FileReader currentFile;

	public static final String CONFIG_PROPERTIES_FILE = "config.properties";

	private static Timeline animation;
	private static CellGrid cellGrid;
	private static ViewController viewController;
	private static ResourceBundle myDefaults;
	private static Dimension DEFAULT_SIZE;


	public Game(double SECOND_DELAY, Stage stage) {

		try {
			myDefaults = ResourceBundle.getBundle("DEFAULTS");
		} catch (Exception e) {
			e.printStackTrace();
		}

		setCurrentFile(myDefaults.getString("FILEPATH"));
		DEFAULT_SIZE = new Dimension(Integer.parseInt(Game.getDefaultProperties().getString("WIDTH")),
				Integer.parseInt(Game.getDefaultProperties().getString("HEIGHT")));
		viewController = new ViewController(stage);

		cellGrid.initializeGrid(Integer.parseInt(currentFile.getGameData().get("Width")), Integer.parseInt(currentFile.getGameData().get("Height")), currentGameType);
		cellGrid.initializeCells(currentFile.getInitialState());
		renderGrid();

		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step()));
	}

	public static FileReader getCurrentFile() {
		return currentFile;
	}

	public static ResourceBundle getDefaultProperties() {
		return myDefaults;
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

	public static void renderGrid() {
		viewController.updateGridPane(CellGrid.getGrid());
	}

	private static void setCurrentFile(String filePath) {
		currentFile = new FileReader();
		currentFile.parseFile(filePath);
		currentGameType = currentFile.getGameType();
		switch(currentGameType) {
			case GAMEOFLIFE -> cellGrid = new GameOfLife();
			case FIRE -> cellGrid = new Fire();
			case WATOR -> cellGrid = new WaTor();
			case PERCOLATION -> cellGrid = new Percolation();
			case SCHELLSEG -> cellGrid = new SchellingSegregation();
			case default -> cellGrid = null;
		}
	}

	public static void importNewFile(String filePath) {
		setCurrentFile(filePath);

		cellGrid.initializeGrid(Integer.parseInt(currentFile.getGameData().get("Width")), Integer.parseInt(currentFile.getGameData().get("Height")), currentGameType);
		cellGrid.initializeCells(currentFile.getInitialState());
		renderGrid();
	}

	public static void step() {
		//Updates the 2D Array in Cell
		cellGrid.nextGeneration();

		// Display current cellGrid
		renderGrid();
	}
}
