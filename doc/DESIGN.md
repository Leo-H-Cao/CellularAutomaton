# Cell Society Design Final
### Nolan Gelinas, Leo Cao, Zach Schrage

## Team Roles and Responsibilities

 * Nolan Gelinas - 

 * Leo Cao - 

 * Zach Schrage - 



## Design goals

#### What Features are Easy to Add


## High-level Design

#### Core Classes
* Main
  * Creates window and calls GameController
* GameController
  * Maintains current game state
  * Has a step function that can be
* Cell
  * Stores Shape object that can be drawn on screen
  * Stores current state
* CellGrid
  * Stores 2D array of Blocks
* UserInterface
  * Stores UI elements
  * Updates UI elements
* DrawGame
  * Called by Main
  * Draws initial game state
  * Calls GameController when finished
* Input
  * Handles mouse input
  * Handles keyboard input
* DataReader
  * Reads in JSON or XML data and returns data structures to GameController


## Assumptions that Affect the Design

#### Features Affected by Assumptions


## Significant differences from Original Plan


## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

