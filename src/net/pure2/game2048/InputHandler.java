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

import net.pure2.game2048.model.tile.TileSet.Direction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import java.util.HashMap;
import java.util.Map;

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
     * A mapping, translating key code (key) to direction (value).
     */
    private static final HashMap<Integer, Direction> MOVEMENT_MAP = new HashMap<>();

    static {
        MOVEMENT_MAP.put(Input.KEY_UP, Direction.UP);
        MOVEMENT_MAP.put(Input.KEY_DOWN, Direction.DOWN);
        MOVEMENT_MAP.put(Input.KEY_LEFT, Direction.LEFT);
        MOVEMENT_MAP.put(Input.KEY_RIGHT, Direction.RIGHT);
    }

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

        // Performing tile set movement
        if (game.getTiles().canMove()) {
            for (Map.Entry<Integer, Direction> entry : MOVEMENT_MAP.entrySet()) {
                if (input.isKeyDown(entry.getKey())) {
                    game.getTiles().performMove(entry.getValue());
                    break;
                }
            }
        }

        // Resetting the last input time
        lastInput = System.currentTimeMillis();
    }
}
