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

package net.pure2.game2048.tile;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * A game tile.
 *
 * @author Pure_
 */
public final class Tile {

    private final int x;
    private final int y;
    private final int sizeY;
    private final int sizeX;
    private int valRenderPosX;
    private int valRenderPosY;
    private int value;
    private boolean combinedThisTurn;
    private Color[] colours;

    /**
     * Constructs a new Tile and calculates related information.
     *
     * @param x position x
     * @param y position y
     * @param sizeX tile size x
     * @param sizeY tile size y
     */
    public Tile(int x, int y, int sizeX, int sizeY) {
        this(-1, x, y, sizeX, sizeY);
    }

    /**
     * Constructs a new Tile and calculates related information.
     *
     * @param value tile value
     * @param x position x
     * @param y position y
     * @param sizeX tile size x
     * @param sizeY tile size y
     */
    public Tile(int value, int x, int y, int sizeX, int sizeY) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        fetchColours();
        calcValRenderPos();
    }
    
    /**
     * Increases the value of this tile.
     */
    public void increaseValue() {
        setValue(value * 2);
    }

    /**
     * Sets this tiles value and re-calculates related information.
     *
     * @param value value
     */
    public void setValue(int value) {
        this.value = value;
        fetchColours();
        calcValRenderPos();
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
        fetchColours();
        calcValRenderPos();
    }

    /**
     * Renders this tile and its value (if necessary).
     *
     * @param g
     */
    public void render(Graphics g) {
        // Drawing the tile
        g.setColor(colours[TileColours.TILE_COLOUR_ID]);
        g.fill(new RoundedRectangle(x + 1, y + 1, sizeX, sizeY, 8));

        // Drawing the tile value
        if (value != -1) {
            g.setColor(colours[TileColours.TEXT_COLOUR_ID]);
            g.drawString(Integer.toString(value), valRenderPosX, valRenderPosY);
        }
    }

    /**
     * Fetches this tiles colours.
     */
    private void fetchColours() {
        colours = TileColours.getColourScheme(value);
    }

    /**
     * Calculates this tiles value render position.
     */
    private void calcValRenderPos() {
        valRenderPosX = x + sizeX / 2 - Integer.toString(value).length() * 3;
        valRenderPosY = y + sizeY / 2 - 8;
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
    
    /**
     * Checks if two tiles can be combined.
     * 
     * @param tile1
     * @param tile2
     * @return whether or not they can be combined
     */
    public static boolean canCombine(Tile tile1, Tile tile2) {
        return tile1.isValid() && tile1.getValue() != 1024 && tile1.equals(tile2);
    }
}
