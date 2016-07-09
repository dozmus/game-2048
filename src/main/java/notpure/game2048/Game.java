/*
 * A clone of the popular 2048 game.
 * Copyright (C) 2016 Pure <https://github.com/purecs>
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

package notpure.game2048;

import notpure.game2048.model.ColourScheme;
import notpure.game2048.model.tile.TileGrid;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Application main class.
 *
 * @author Pure <https://github.com/purecs>
 */
public final class Game extends BasicGame {

    private static final String VERSION = "v1.0.3";
    private static final String WINDOW_TITLE = "game-2048 (" + VERSION + ")";
    /**
     * A mapping, translating key code (key) to direction (value).
     */
    private static final HashMap<Integer, TileGrid.Direction> MOVEMENT_MAP = new HashMap<>();

    static {
        MOVEMENT_MAP.put(Input.KEY_UP, TileGrid.Direction.UP);
        MOVEMENT_MAP.put(Input.KEY_DOWN, TileGrid.Direction.DOWN);
        MOVEMENT_MAP.put(Input.KEY_LEFT, TileGrid.Direction.LEFT);
        MOVEMENT_MAP.put(Input.KEY_RIGHT, TileGrid.Direction.RIGHT);
    }

    /**
     * The application entry-point, initialises and starts the game.
     *
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        // Setting the LWJGL class path
        System.setProperty("org.lwjgl.librarypath",
                new File(new File(System.getProperty("user.dir"), "natives"),
                        LWJGLUtil.getPlatformName()).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath",
                System.getProperty("org.lwjgl.librarypath"));

        // Creating a game container
        AppGameContainer gc = new AppGameContainer(new Game());

        // Graphics options
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        gc.setAlwaysRender(true);
        gc.setSmoothDeltas(true);
        gc.setVSync(true);

        // Starting the game
        gc.start();
    }

    private GameContainer gc;
    /**
     * The game renderer.
     */
    private GameRenderer gameRenderer;
    /**
     * Tile set.
     */
    private TileGrid tiles;
    /**
     * Current score.
     */
    private int score = 0;
    /**
     * Best score.
     */
    private int bestScore = 0;

    /**
     * Constructs a new Game.
     */
    public Game() {
        super(WINDOW_TITLE);
    }

    /**
     * Initialises the game.
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        this.gc = gc;

        // Load tile colours
        ColourScheme.load(getClass().getResourceAsStream("/notpure/game2048/model/styles.txt"));

        // Initialises the renderer
        gameRenderer = new GameRenderer(this);

        // Initialises and creates the tile set
        tiles = new TileGrid(this, 4, 4);
        tiles.reset();
        tiles.insertRandomTile();

        // Update game size
        Dimension gameDimension = tiles.getGameDimensions();
        ((AppGameContainer) gc).setDisplayMode((int) gameDimension.getWidth(), (int) gameDimension.getHeight(), false);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

    }

    /**
     * Renders the game elements.
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        // Game background
        gameRenderer.renderBackground(gc, g);

        // Renders the tile set
        tiles.render(g);

        // Rendering the player score
        gameRenderer.renderScore(g, gc.isShowingFPS());

        // Rendering the game over text
        if (!tiles.hasMoves()) {
            gameRenderer.renderGameOverText(g);
        }
    }

    /**
     * Handles game input.
     */
    @Override
    public void keyReleased(int keyCode, char keyChar) {
        // Display FPS toggle
        if (keyCode == Input.KEY_F) {
            gc.setShowFPS(!gc.isShowingFPS());
        }

        // Reset game
        if (keyCode == Input.KEY_R) {
            reset();
        }

        // Performing tile set movement
        if (tiles.hasMoves()) {
            for (Map.Entry<Integer, TileGrid.Direction> entry : MOVEMENT_MAP.entrySet()) {
                if (keyCode == entry.getKey()) {
                    tiles.performMove(entry.getValue());
                    break;
                }
            }
        }
    }

    /**
     * Resets the current game and updates the best score is necessary.
     */
    public void reset() {
        // Updating the best score
        if (score > bestScore) {
            bestScore = score;
        }

        // Resetting the game state
        setScore(0);
        getTiles().reset();
        getTiles().insertRandomTile();
    }

    /**
     * Returns the TileSet associated with this Game.
     *
     * @return TileSet
     */
    public TileGrid getTiles() {
        return tiles;
    }

    /**
     * Gets the current score.
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score to display.
     *
     * @param score score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Adds score.
     *
     * @param score score to add
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Gets the current best score.
     *
     * @return best score
     */
    public int getBestScore() {
        return bestScore;
    }
}
