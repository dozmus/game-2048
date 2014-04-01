package net.pure2.game2048;

import java.io.File;
import net.pure2.game2048.tiles.TileSet;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
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
        System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
        
        // Creating a game container
        AppGameContainer gc = new AppGameContainer(new Game("2048"));

        // Graphics options
        gc.setDisplayMode(510, 510, false);
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        gc.setAlwaysRender(true);
        gc.setSmoothDeltas(true);
        gc.setVSync(true);

        // Starting the game
        gc.start();
    }

    private InputHandler inputHandler;
    private TileSet tiles;
    private int score = 0;

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
        inputHandler = new InputHandler(this);

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
        inputHandler.handle(gc.getInput());
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
        // Renders the tile set
        tiles.render(g);

        // Rendering the player score
        renderScore(g, gc.isShowingFPS());

        // Rendering the game over text
        if (!tiles.canMove()) {
            renderGameOverText(g);
        }
    }

    /**
     * Renders the player's score.
     *
     * @param g
     * @param showingFps whether or not the fps display is enabled
     */
    private void renderScore(Graphics g, boolean showingFps) {
        int y = showingFps ? 30 : 5;
        
        g.setColor(Color.white);
        g.drawString("Score: " + score, 8, y);
    }

    /**
     * Renders the game over text.
     *
     * @param g
     */
    private void renderGameOverText(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Game over, your score is: " + score + "!", 150, 100);
        g.drawString("Press r to play again!", 150, 120);
    }

    /**
     * Resets the current game.
     */
    public void reset() {
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
}
