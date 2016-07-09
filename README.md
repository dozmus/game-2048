game-2048
============

Another clone of the popular [2048 game](http://gabrielecirulli.github.io/2048/).

#### Usage
You can either compile the source code yourself or download the
latest [release](https://github.com/PureCS/game-2048/releases).
You will then just need to execute the game JAR in the archive.  
Example: `java -jar game-2048.jar`

### Keys
* `R` - Resets the state of the game.
* `F` - Toggles FPS display.
* `Up/Down/Left/Right Arrows` - Shifts tiles in said direction.

### Customisation
* Tile size: Modify [`TileGrid#tileDimensions`](src/main/java/notpure/game2048/model/tile/TileGrid.java)
* Tile colors/score text color/background color: Modify [`styles.txt`](src/main/resources/notpure/game2048/model/styles.txt)
* Tile count: Modify `tiles = new TileGrid(this, 4, 4)` in [`Game#init(GameContainer)`](src/main/java/notpure/game2048/Game.java)

### Compatibility
This game should work on most Windows, Linux and Mac distributions, as supported by slick2d.

#### Libraries
* Slick (and its dependencies)