# Refactor Discussion
## Nolan Gelinas


## Refactor Game Rule and Type Implementations
* Our primary design problem is with our implementation of new simulation games
* We want it to be easily to add new game rule sets
* I had the idea to use nested enums to help map GameTypes to CellTypes
  * This idea seemed promising at first, but we quickly discovered that our current implementation would not lend well to that structure
  * Instead, I created a GameType to CellType mapping file so that the frontend knows which games can have which cell types
* I also helped refactor our property loader to use a ResourceBundle 
* We discussed unifying our empty cell type to be EMPTY instead of EMPTY and DEAD
* Otherwise, we generally cleaned up our code and methods and removed unnecessary comments and print statements
