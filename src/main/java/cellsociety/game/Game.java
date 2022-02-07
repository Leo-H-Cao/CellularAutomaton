package cellsociety.game;

import cellsociety.cell.*;
import cellsociety.io.FileReader;
import cellsociety.view.ViewController;
import javafx.animation.Animation;
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
	private static double currentGameSpeed;

	public static final String CONFIG_PROPERTIES_FILE = "config.properties";

	private static Timeline timeline;
	private static CellGrid cellGrid;
	private static ViewController viewController;
	private static ResourceBundle myDefaults;
	private static Dimension DEFAULT_SIZE;


	public Game(Stage stage) {
		try {
			myDefaults = ResourceBundle.getBundle("DEFAULTS");
		} catch (Exception e) {
			e.printStackTrace();
		}

		openFile(myDefaults.getString("FILEPATH"));
		DEFAULT_SIZE = new Dimension(Integer.parseInt(Game.getDefaultProperties().getString("WIDTH")),
				Integer.parseInt(Game.getDefaultProperties().getString("HEIGHT")));
		viewController = new ViewController(stage);

		CellGrid.initializeGrid(Integer.parseInt(currentFile.getGameData().get("Width")), Integer.parseInt(currentFile.getGameData().get("Height")), currentGameType);
		CellGrid.initializeCells(currentFile.getInitialState());
		renderGrid();

		timeline = new Timeline(new KeyFrame(Duration.seconds(Double.parseDouble(Game.getDefaultProperties().getString("DEFAULT_DELAY"))), event -> step()));
		timeline.setCycleCount(Animation.INDEFINITE);
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

	public static double getCurrentGameSpeed() {return currentGameSpeed;}

	public static void toggleSimulation() {
		if(playing) {
			timeline.pause();
		} else {
			timeline.play();
		}
		playing = !playing;
	}

	public static void renderGrid() {
		viewController.updateGridPane(CellGrid.getGrid());
	}

	public static void setSpeed(double s) {
		currentGameSpeed = s;
		timeline.setRate(s);
	}

	private static void openFile(String filePath) {
		currentFile = new FileReader();
		currentFile.parseFile(filePath);
		currentGameType = currentFile.getGameType();
		currentGameSpeed = Double.parseDouble(currentFile.getGameData().get("Speed"));
		switch(currentGameType) {
			case GAMEOFLIFE -> cellGrid = new GameOfLife();
			case FIRE -> {
				double treeCombustProbability = Double.parseDouble(currentFile.getGameData().get("TreeCombustProbability"));
				double treeGrowthProbability = Double.parseDouble(currentFile.getGameData().get("TreeGrowthProbability"));
				cellGrid = new Fire(treeCombustProbability, treeGrowthProbability);
			}
			case WATOR -> {
				int reproduction = Integer.parseInt(currentFile.getGameData().get("ReproductionCounter"));
				int energy = Integer.parseInt(currentFile.getGameData().get("EnergyCounter"));
				cellGrid = new WaTor(reproduction, energy);
			}

			case PERCOLATION -> cellGrid = new Percolation();
			case SCHELLSEG -> {
				double fIdeal =  Double.parseDouble(currentFile.getGameData().get("fIdeal"));
				cellGrid = new SchellingSegregation(fIdeal);
			}
			case default -> cellGrid = null;
		}
	}

	public static void importNewFile(String filePath) {
		openFile(filePath);

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
