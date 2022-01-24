package cellsociety.cell;

/**
 * This class outlines the primary attributes of a Cell: position (xy coordinate) and type (game specific)
 * Different games will have specialized cells that extend this class.
 * Each cell must have a nextGeneration method that takes in its neighbors as a parameter
 * The nextGeneration method determines what its type of Cell it will be in the next generation
 *
 * @author Zack Schrage
 */
public abstract class Cell {

    protected int x;
    protected int y;
    protected int type;

    public Cell(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public abstract int nextGeneration(int[][] neighborsType);

}
