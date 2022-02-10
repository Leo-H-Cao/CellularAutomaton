# Cell Society Design Final
### Nolan Gelinas, Leo Cao, Zach Schrage

## Team Roles and Responsibilities

 * Nolan Gelinas - Frontend

 * Leo Cao - File IO

 * Zach Schrage - Backend model
 
## Design goals

#### What Features are Easy to Add
* Cell Types
* Game Types
* Neighborhood Types

## High-level Design

#### Core Classes
* Main
  * Creates window and calls Game
* Game
  * Maintains current game state
  * Has a step function that is called by an animation timer
* Cell
  * Stores Shape object that can be drawn on screen
  * Stores current state
  * implemented by each type of cell required for each game
* CellGrid
  * Stores 2D array of Blocks
  * Implemented by each type of game
* ViewController
  * Stores UI elements
  * Updates UI elements
* GridManager
  * Outputs a Node to be drawn on the window
* FileReader
  * Reads in data from xml files and stores in a hashmap
  * This data can be accessed by any class
* XMLExport
  * Writes current cell configuration to an XML file


## Assumptions that Affect the Design
* Max grid dimensions = 100

#### Features Affected by Assumptions
* Max grid size, max update speed

## Significant differences from Original Plan
* Instead of having an abstract rules class, we abstracted Cell and CellGrid classes that can be implemented with different sets of rules and neighborhood detection

## New Features HowTo
* Adding new simulations
  * Create required Cell Types for the game in CellType (or reuse currently defined ones)
  * Create Game Type name in GameType
  * Define custom neighborhood detection in NeighborhoodTypes if necessary
  * Implement Cell and CellGrid with your game's rules
  * Define which cells your game uses in GameCellMapping
  * Create XML file for your game

#### Easy to Add Features
* Different cell shapes
  * The model is already able to simulate triangular grids
  * Currently, the limitation is the view

#### Other Features not yet Done
* More efficient updating of nodes

