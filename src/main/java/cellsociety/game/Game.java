package cellsociety.game;

import cellsociety.cell.*;
import cellsociety.io.FileReader;
import cellsociety.view.ViewController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Locale;
import java.util.ResourceBundle;

public class Game {
	private static boolean playing = false;
	private static GameType currentGameType;
	private static FileReader currentFile;
	private static double currentGameSpeed;
	private static NeighborhoodType currentNeighborhoodType;

	private static Timeline timeline;
	private static CellGrid cellGrid;
	private static ViewController viewController;
	private static ResourceBundle myDefaultProperties;
	private static ResourceBundle myInterfaceProperties;



	public Game(Stage stage) {
		try {
			myDefaultProperties = ResourceBundle.getBundle("DEFAULTS");
			myInterfaceProperties = ResourceBundle.getBundle("config", new Locale(myDefaultProperties.getString("LANGUAGE_CODE")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		openFile(myDefaultProperties.getString("FILEPATH"));
		viewController = new ViewController(stage);

		CellGrid.initializeGrid(Integer.parseInt(currentFile.getGameData().get("Width")), Integer.parseInt(currentFile.getGameData().get("Height")), currentGameType, currentNeighborhoodType);
		CellGrid.initializeCells(currentFile.getInitialState());
		renderGrid();

		timeline = new Timeline(new KeyFrame(Duration.seconds(Double.parseDouble(Game.getDefaultProperties().getString("DEFAULT_DELAY"))), event -> step()));
		timeline.setCycleCount(Animation.INDEFINITE);
	}

	public static void setLocale(String code) {
		try {
			myInterfaceProperties = ResourceBundle.getBundle("config", new Locale(code));
		} catch (Exception e) {
			System.out.println("Locale not found");
		}
		ViewController.redrawUI();
	}

	public static FileReader getCurrentFile() {
		return currentFile;
	}

	public static ResourceBundle getDefaultProperties() {
		return myDefaultProperties;
	}

	public static ResourceBundle getInterfaceProperties() {
		return myInterfaceProperties;
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
//		currentFile.printMap();
		currentGameType = currentFile.getGameType();
		currentNeighborhoodType = currentFile.getNeighborhoodType();
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
		CellGrid.initializeGrid(Integer.parseInt(currentFile.getGameData().get("Width")), Integer.parseInt(currentFile.getGameData().get("Height")), currentGameType, currentNeighborhoodType);
		CellGrid.initializeCells(currentFile.getInitialState());
		renderGrid();
	}

	public static void step() {
		cellGrid.nextGeneration();
		renderGrid();
	}
}
