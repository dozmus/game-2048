package net.pure2.game2048;

import java.io.File;
import net.pure2.game2048.tiles.TileSet;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * The Game main-class.
 *
 * @author Pure_ <mail@pure2.net>
 */
public final class Game extends BasicGame {

    /**
     * @param args the command line arguments
     *
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
        AppGameContainer gc = new AppGameContainer(new Game("2048"));

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
    private InputHandler input;
    
    /**
     * The UserInterface renderer.
     */
    private UserInterface ui;
    
    /**
     * Tile set.
     */
    private TileSet tiles;
    
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
     *
     * @param title frame title
     */
    public Game(String title) {
        super(title);
    }

    /**
     * Initialises the game.
     *
     * @param gc
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
        // Initialises the InputHandler
        input = new InputHandler(this);
        
        // Initialises the UserInterface renderer
        ui = new UserInterface(this);

        // Initialises and creates the tile set
        tiles = new TileSet(this, 4, 4);
        tiles.populate();
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
        if (!tiles.canMove()) {
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
        getTiles().populate();
        getTiles().insertRandomTile();
    }

    /**
     * Returns the TileSet associated with this Game.
     *
     * @return TileSet
     */
    public TileSet getTiles() {
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
