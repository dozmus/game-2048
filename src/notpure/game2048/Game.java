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

package notpure.game2048;

import java.io.File;

import notpure.game2048.model.ColourScheme;
import notpure.game2048.model.tile.TileGrid;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Application main class.
 *
 * @author Pure_
 */
public final class Game extends BasicGame {

    private static final String VERSION = "v1.0.2";
    private static final String WINDOW_TITLE = "game-2048 (" + VERSION + ")";

    /**
     * The application entry-point, initialises and starts the game.
     * 
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        // Setting the LWJGL class path
        System.setProperty("org.lwjgl.librarypath",
                new File(new File(System.getProperty("user.dir"), "lib/natives"),
                        LWJGLUtil.getPlatformName()).getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath",
                System.getProperty("org.lwjgl.librarypath"));
        
        // Creating a game container
        AppGameContainer gc = new AppGameContainer(new Game());

        // Graphics options
        gc.setDisplayMode(514, 514, false);
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        gc.setAlwaysRender(true);
        gc.setSmoothDeltas(true);
        gc.setVSync(true);

        // Starting the game
        gc.start();
    }

    /**
     * User Input handler.
     */
    private SimpleInputHandler input;
    
    /**
     * The UserInterface renderer.
     */
    private UserInterface ui;
    
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
     *
     * @param gc
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        // Load tile colours
        ColourScheme.load(getClass().getResourceAsStream("/notpure/game2048/tile/styles.txt"));

        // Initialises the InputHandler
        input = new SimpleInputHandler(this);
        
        // Initialises the UserInterface renderer
        ui = new UserInterface(this);

        // Initialises and creates the tile set
        tiles = new TileGrid(this, 4, 4);
        tiles.reset();
        tiles.insertRandomTile();
    }

    /**
     * Performs game updating logic.
     *
     * @param gc
     * @param i
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        // Handles input updates
        input.handle(gc);
    }

    /**
     * Renders the game elements.
     *
     * @param gc
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        // Game background
        ui.renderBackground(gc, g);
        
        // Renders the tile set
        tiles.render(g);

        // Rendering the player score
        ui.renderScore(g, gc.isShowingFPS());
        
        // Rendering the game over text
        if (!tiles.hasMoves()) {
            ui.renderGameOverText(g);
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
