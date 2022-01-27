package cellsociety.game;

import cellsociety.cell.Cell;
import cellsociety.cell.Type.GAMETYPE;
import cellsociety.cell.CellGrid;
import cellsociety.io.FileReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import static cellsociety.cell.Type.CELLTYPE.*;

public class Game {

    private static Timeline animation;
    private static CellGrid cellGrid;

    public Game(double SECOND_DELAY) {
        init();
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();
    }

    private void init() {
        FileReader f = new FileReader();
        f.parseFile("data/SampleComfig1.xml");
        cellGrid = new CellGrid();
        cellGrid.initializeGrid(Integer.parseInt(f.getGameData().get("Width")), Integer.parseInt(f.getGameData().get("Height")), GAMETYPE.GAMEOFLIFE);
        cellGrid.initializeCells(f.getInitialState());
    }

    private void step(double second_delay) {

        //HARDCODED FOR TESTING PURPOSES
        Cell[][] g = cellGrid.getGrid();
        for (int i = 0 ; i < g.length; i++) {
            for (int j = 0 ; j < g[0].length; j++) {
                if (g[i][j].getType() == ALIVE) System.out.print("* ");
                else System.out.print(". ");
            }
            System.out.println();
        }
        System.out.println();

        //Updates the 2D Array in Cell
        cellGrid.nextGeneration();
    }

}
