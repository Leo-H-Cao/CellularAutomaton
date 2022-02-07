Cell Society
====

This project implements a cellular automata simulator.

Names: Leo Cao, Nolan Gelinas, Zack Schrage


### Timeline

Start Date: 1/23/22

Finish Date: 2/7/22

Hours Spent: 42

### Primary Roles

Leo Cao: File Parsing (io package)
Nolan Gelinas: View (view package)
Zack Schrage: Model (cell package)


### Resources Used
For XML parsing: https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/

For exporting to XML file: https://crunchify.com/java-simple-way-to-write-xml-dom-file-in-java/

### Running the Program

Main class: Main.java

Data files needed: XML Game Configuration File (Placed in the data resource folder)

Features implemented:

5 Games:

* GameOfLife
* Fire
* Percolation
* WaTor
* Schelling Segregation

Neighborhood Types:

* Square Moore (8 neighbors)
* Square Neumann (4 edge neighbors)
* Triangular Moore (15 neighbors)
* Triangular Neumann (3 edge neighbors)

General Features:

* Games (and associated properties) loaded via XML
* Current state of game can be packaged into and exported as an XML
* XML files with missing information have information loaded from a default .properties file
* Foreign languages supported via different config_**\<Language Code\>**.properties files
* Games can be played on a loop whose speed changes with a speed slider or can be stepped through generation by generation
* Clicking on the grid can alter the state of the cell, the particular state can be set using a picker


### Notes/Assumptions

Assumptions or Simplifications:

Assumes the playing space to be finite since the core data structure is a 2D Array

### Interesting data files:

exampleGameOfLife1.xml
exampleFire5.xml
exampleWaTor1.xml
exampleSegregation1.xml
examplePercolation1.xml

### Known Bugs:

Triangular neighborhoods are not fully working for games with mobile entities such as WaTor or Schelling Segregation.
Triangular neighborhoods are currently not displayed in triangular shaped cells
FileReader cannot parse XML files where nodes have more than one child node

### Noteworthy Features:
* Time scaling
* Dynamic coloring
* Importing and Exporting files
* Re-skin app with css files
* Use custom neighbor detection
* Easily add new languages


### Impressions:
* The app is very stable up to 50 x 50 grids
  * At very low sim speeds the game can be played at grids of up to 150 x 150
  * Game will automatically change default simulation scale depending on grid size
* Clicking is easy but would have been nice if click and dragging worked
* Model for simulations is very robust and seems accurate in almost all cases
