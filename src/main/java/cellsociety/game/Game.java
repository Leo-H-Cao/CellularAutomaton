package cellsociety.game;

import cellsociety.cell.CellGrid;
import cellsociety.cell.Fire;
import cellsociety.cell.GameOfLife;
import cellsociety.cell.WaTor;
import cellsociety.io.FileReader;
import cellsociety.util.Type;
import cellsociety.view.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ResourceBundle;

public class Game {
	private static boolean playing = false;
	private static Type.GAMETYPE currentGameType;

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

	public static Type.GAMETYPE getCurrentGameType() {
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

    private void init() {
		 makeNewGrid("data/SampleComfig1.xml");
    }

	public static void makeNewGrid(String filePath) {
		FileReader f = new FileReader();
		f.parseFile(filePath);
		currentGameType = f.getGameType();
		switch(currentGameType) {
			case default -> cellGrid = null;
			case GAMEOFLIFE -> cellGrid = new GameOfLife();
			case FIRE -> cellGrid = new Fire();
			case WATOR -> cellGrid = new WaTor();
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
