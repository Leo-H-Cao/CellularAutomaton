# Cell Society Design Plan
### Team 7
### Nolan Gelinas, Leo Cao, Zach Schrage


## Design Overview
We will need to design an application that can read in data from a file that represents a game type and the games initial state. We will then need to display a GUI that allows the user to see the progress of the game, control the game speed, pause/play the game, and modify the current game state.

Some of our core classes include a "cell" class, which keeps track of the state of the basic individual blocks displayed,
a "cell grid" class that stores the 2d array of cells, and a "game controller" class that keeps track of the current game state.
We also have a "draw game" class that renders the display based on which cells are supposed to be "on." This way, the display view is 
separated from the logic regarding the cells. We are planning to have a super class "Cell" and it’s subclasses like GameOfLifeCell or FireCell have the correct methods that
dictate its behavior.


## Design Details
![](images/Initial_UML_Diagral.png)


## Use Cases

* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
    Cell middleCell = grid[x][y];
    int neighborCount = 0;
    for(neighbor: middleCell.getNeighbors()){
    if(neighbor.isAlive()){
          neighborCount++;
        }
    }
    if(neighborCount <= 1){
      middleCell.dies();
    }
```


* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
Cell middleCell = grid[x][y];
int neighborCount = 0;
for(neighbor: middleCell.getNeighbors()){
  if(neighbor.isAlive()){
    neighborCount++;
  }
}
if(neighborCount <= 1){
  middleCell.dies();
}
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
 for(int i = 0; i < grid.length; i++){
  for(int j = 0; j < grid[0].length; j++){
      grid[i][j].nextGeneration();
    }
}
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in a data file
```java
probCatch = x //read in from file
controller.setSimulationConfig(probCatch);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
 DataReader XMLReader = new DataReader();
curSimulationFile = XMLReader.read(filepath);
```

## Design Considerations

#### How do we make updating the display state more efficient, not just going through the entire 2d array and updating every single cell (because for some variations many of the cells will be "off")

 * Alernative #1: One solution is to have the cell grid keep track of each cell's position, and update the neighbors of any active cells

 * Alernative #2: An alternative is to have each cell keep store a list of its neighbors, and then go through each active cell and update its neighbors.

 * Tradeoffs: For the first approach, the grid would be the one keeping track of the coordinates of each cell, while for the second approach the cell itself would be keeping track
of its location in the grid. Both solutions are viable and it comes down to how much complexity we want in the cell class and which would lead to a better overall design


#### How do we handle the different rules for the different games

 * Alernative #1: Create an abstract rules class, and every variation of the game's rules would be a subclass of this rules class. Each one would implement/override any methods that it needs.

 * Alernative #2: Cell can be a super class and it’s subclasses like GameOfLifeCell or FireCell have the right methods that dictate its behavior

 * Trade-offs: The first approach would allow us to take advantage of any rules that are common to multiple games, by simply defining the method in the abstract rules class and any games that do not use that rule can override it.
However, we are unsure if this is a good use of inheritance because it may result in some unnecessary subclasses if certain games do not have many unique rules. The second approach may be 
more feasible because the controller would not have to process all the logic for all the different games, instead the different cells handle their own behavior depending on the game.



## User Interface

Here is our amazing UI:

![This is cool, too bad you can't see it](images/wireframev2.png "Basic wireframe layout")
* Click a cell to toggle its state
* Drag playback speed slider to changer the simulation speed
* Click play button to toggle the simulation
* Click the step button (right of play button) to step one cycle of the simulation
* Import/export the current simulation state with import and export buttons
* Select cell type dropdown will give you a list of options based on the current game type. This will decide which cell type is created when a user clicks a cell on the grid
* Click the i on the top right to view the simulation parameters and any errors

## Team Responsibilities

 * Leo Cao - controller, simulation rules in cells

 * Nolan Gelinas - view classes and UI for simulations, XML parsing

 * Zack Schrage - design of cells, inheritance hierarchy


