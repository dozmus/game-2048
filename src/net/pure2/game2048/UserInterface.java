package net.pure2.game2048;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * The UserInterface renderer.
 *
 * @author Pure_ <mail@pure2.net>
 */
public final class UserInterface {

    /**
     * {@link Game} instance this UserInterface is bound to.
     */
    private final Game game;

    /**
     * Constructs a new UserInterface renderer.
     *
     * @param game Game to bind to
     */
    public UserInterface(Game game) {
        this.game = game;
    }

    /**
     * Renders the game background.
     *
     * @param gc
     * @param g
     */
    public void renderBackground(GameContainer gc, Graphics g) {
        g.setColor(new Color(209, 213, 255));
        g.fill(new Rectangle(0, 0, gc.getWidth(), gc.getHeight()));
    }

    /**
     * Renders the player's score.
     *
     * @param g
     * @param showingFps whether or not the FPS display is enabled
     */
    public void renderScore(Graphics g, boolean showingFps) {
        int y = showingFps ? 30 : 5;

        g.setColor(Color.white);
        g.drawString("Score: " + game.getScore(), 8, y);
        g.drawString("Best Score: " + game.getBestScore(), 8, y + 20);
    }

    /**
     * Renders the game over text.
     *
     * @param g
     */
    public void renderGameOverText(Graphics g) {
        // Background rectangle, to make the text clearer
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fill(new Rectangle(128, 100, 300, 40));

        // Text
        g.setColor(Color.white);
        g.drawString("Game over, your score is: " + game.getScore() + "!", 130, 100);
        g.drawString("Press r to play again!", 130, 120);
    }
}
