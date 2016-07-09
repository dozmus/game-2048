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

package notpure.game2048.model.tile;

import notpure.game2048.model.ColourScheme;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

import java.awt.*;

/**
 * A game tile.
 *
 * @author Pure <https://github.com/purecs>
 */
public final class Tile {

    private final Point position;
    private final Dimension size;
    private int valueRenderPosX;
    private int valueRenderPosY;
    private int value;
    private boolean combinedThisTurn;
    private Color[] colours;

    /**
     * Constructs a new Tile and calculates related information, the value is set to -1.
     *
     * @param x
     * @param y
     * @param size
     */
    public Tile(int x, int y, Dimension size) {
        this(-1, x, y, (int) size.getWidth(), (int) size.getHeight());
    }

    /**
     * Constructs a new Tile and calculates related information.
     *
     * @param value  tile value
     * @param x      position x
     * @param y      position y
     * @param width  tile width
     * @param height tile height
     */
    public Tile(int value, int x, int y, int width, int height) {
        this.value = value;
        size = new Dimension(width, height);
        position = new Point(x, y);
        updateColour();
        updateRenderPosition();
    }

    /**
     * Checks if two tiles can be combined.
     *
     * @param tile1
     * @param tile2
     * @return whether or not they can be combined
     */
    public static boolean canCombine(Tile tile1, Tile tile2) {
        return tile1.isValid() && tile1.equals(tile2);
    }

    /**
     * Increases the value of this tile.
     */
    public void increaseValue() {
        setValue(value * 2);
    }

    /**
     * Gets this tiles value.
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets this tiles value and re-calculates related information.
     *
     * @param value value
     */
    public void setValue(int value) {
        this.value = value;
        updateColour();
        updateRenderPosition();
    }

    /**
     * Sets whether or not this tile was combined this turn.
     *
     * @param combinedThisTurn
     */
    public void setCombinedThisTurn(boolean combinedThisTurn) {
        this.combinedThisTurn = combinedThisTurn;
    }

    /**
     * Gets whether or not this tile was combined this turn.
     *
     * @return combined this turn
     */
    public boolean wasCombinedThisTurn() {
        return combinedThisTurn;
    }

    /**
     * Checks whether or not this tile is valid.
     *
     * @return tile is valid
     */
    public boolean isValid() {
        return value != -1;
    }

    /**
     * Resets the value of this tile.
     */
    public void reset() {
        value = -1;
        updateColour();
        updateRenderPosition();
    }

    /**
     * Renders this tile and its value (if necessary).
     *
     * @param g
     */
    public void render(Graphics g) {
        // Drawing the tile
        g.setColor(colours[ColourScheme.TILE_COLOUR_IDX]);
        g.fill(new RoundedRectangle((int) position.getX() + 1, (int) position.getY() + 1,
                (int) size.getWidth(), (int) size.getHeight(), 8));

        // Drawing the tile value, if the tile is valid
        if (value != -1) {
            g.setColor(colours[ColourScheme.TEXT_COLOUR_IDX]);
            g.drawString(Integer.toString(value), valueRenderPosX, valueRenderPosY);
        }
    }

    /**
     * Sets this tiles colours.
     */
    private void updateColour() {
        colours = ColourScheme.getColourScheme(value);
    }

    /**
     * Calculates this tiles value render position.
     */
    private void updateRenderPosition() {
        valueRenderPosX = (int) position.getX() + (int) size.getWidth() / 2 - Integer.toString(value).length() * 3; // XXX find length based on g.getfont
        valueRenderPosY = (int) position.getY() + (int) size.getHeight() / 2 - 8;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile tile = (Tile) obj;

            return tile.getValue() == value
                    && tile.wasCombinedThisTurn() == combinedThisTurn;
        }
        return false;
    }
}
