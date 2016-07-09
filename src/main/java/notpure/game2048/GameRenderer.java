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
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * The game renderer.
 *
 * @author Pure <https://github.com/purecs>
 */
public final class GameRenderer {

    /**
     * {@link Game} instance this UserInterface is bound to.
     */
    private final Game game;

    /**
     * Constructs a new UserInterface renderer.
     *
     * @param game Game to bind to
     */
    public GameRenderer(Game game) {
        this.game = game;
    }

    /**
     * Renders the game background.
     *
     * @param gc
     * @param g
     */
    public void renderBackground(GameContainer gc, Graphics g) {
        g.setColor(ColourScheme.getBackgroundColor());
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

        g.setColor(ColourScheme.getScoreTextColor());
        g.drawString("Score: " + game.getScore(), 8, y);
        g.drawString("Best Score: " + game.getBestScore(), 8, y + 20);
    }

    /**
     * Renders the game-over text.
     *
     * @param g
     */
    public void renderGameOverText(Graphics g) {
        final String scoreText = "Game over, your score is: " + game.getScore() + "!";
        int scoreTextWidth = g.getFont().getWidth(scoreText) + 10;

        // Background rectangle, to make the text clearer
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fill(new Rectangle(128, 100, scoreTextWidth, 40));

        // Text
        g.setColor(Color.white);
        g.drawString(scoreText, 130, 100);
        g.drawString("Press r to play again!", 130, 120);
    }
}
