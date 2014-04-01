package net.pure2.game2048;

import net.pure2.game2048.tiles.TileSet.Direction;
import org.newdawn.slick.Input;

/**
 * An InputHandler.
 *
 * @author Pure_ <mail@pure2.net>
 */
public final class InputHandler {

    /**
     * The delay between inputs.
     */
    private static final long INPUT_DELAY = 100L;

    /**
     * The Game object this handler is bound to.
     */
    private final Game game;

    /**
     * The last time user input was recorded.
     */
    private long lastInput = System.currentTimeMillis();

    /**
     * Constructs a new InputHandler.
     *
     * @param game Game to bind to
     */
    public InputHandler(Game game) {
        this.game = game;
    }

    /**
     * Handles user the captured user Input.
     *
     * @param input captured input state
     */
    public void handle(Input input) {
        // Checking if the input delay has been met
        if (System.currentTimeMillis() - lastInput < INPUT_DELAY) {
            return;
        }
        
        // Resetting the board
        if (input.isKeyDown(Input.KEY_R)) {
            game.reset();
        }

        // Checking if the player can move
        if (game.getTiles().canMove()) {
            // Up
            if (input.isKeyDown(Input.KEY_UP)) {
                game.getTiles().process(Direction.UP);
                insertRandomTile();
            }

            // Down
            if (input.isKeyDown(Input.KEY_DOWN)) {
                game.getTiles().process(Direction.DOWN);
                insertRandomTile();
            }

            // Left
            if (input.isKeyDown(Input.KEY_LEFT)) {
                game.getTiles().process(Direction.LEFT);
                insertRandomTile();
            }

            // Right
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                game.getTiles().process(Direction.RIGHT);
                insertRandomTile();
            }
        }

        // Resetting the last input time
        lastInput = System.currentTimeMillis();
    }

    /**
     * Attempting to insert a new random tile into the tile set.
     *
     * @return success
     */
    private boolean insertRandomTile() {
        if (game.getTiles().hasFreeSlot()) {
            game.getTiles().insertRandomTile();
            return true;
        }
        return false;
    }
}
