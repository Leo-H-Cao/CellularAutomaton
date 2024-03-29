# Cell Society Design Final
### Nolan Gelinas, Leo Cao, Zach Schrage

## Team Roles and Responsibilities

 * Nolan Gelinas - 

 * Leo Cao - 

 * Zach Schrage - 



## Design goals

#### What Features are Easy to Add
* Cells
* Buttons
* Basic update function

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
* (abstract) Rules
  * Outlines the basic structure to check for a specific set of Rules
  * Implemented by each specific games' Rules class


## Assumptions that Affect the Design
* Fixed screen size

#### Features Affected by Assumptions
* Dynamic cell resizing

## Significant differences from Original Plan


## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

