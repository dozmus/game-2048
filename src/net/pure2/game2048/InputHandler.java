/*
 * A clone of the popular 2048 game.
 * Copyright (C) 2016 Pure
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.pure2.game2048;

import net.pure2.game2048.tile.TileSet.Direction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * An InputHandler.
 *
 * @author Pure_
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
     * @param gc GameContainer
     */
    public void handle(GameContainer gc) {
        // Checking if the input delay has been met
        if (System.currentTimeMillis() - lastInput < INPUT_DELAY) {
            return;
        }
        
        // Fetching the current input state
        Input input = gc.getInput();
        
        // Resetting the board
        if (input.isKeyDown(Input.KEY_R)) {
            game.reset();
        }
        
        // Display FPS toggle
        if (input.isKeyDown(Input.KEY_F)) {
            gc.setShowFPS(!gc.isShowingFPS());
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
