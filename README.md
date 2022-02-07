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
Zack Schage: Model (cell package)


### Resources Used


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
* Foreign languages supported via different config_<language code>.properties files
* Games can be played on a loop whose speed changes with a speed slider or can be stepped through generation by generation
* Clicking on the grid can alter the state of the cell, the particular state can be set using a picker


### Notes/Assumptions

Assumptions or Simplifications:

Assumes the playing space to be finite since the core data structure is a 2D Array

Interesting data files:

exampleGameOfLife.xml
exampleFire.xml
exampleWaTor.xml
exampleSegregation.xml
examplePercolation.xml

Known Bugs:

Triangular neighborhoods are not fully working for games with mobile entities such as WaTor or Schelling Segregation.
Triangular neighborhoods are currently not displayed in triangular shaped cells

Noteworthy Features:


### Impressions

