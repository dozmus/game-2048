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

package notpure.game2048.model.tile;

import java.awt.*;
import java.util.Random;
import notpure.game2048.Game;
import org.newdawn.slick.Graphics;

/**
 * A TileSet.
 *
 * @author Pure_
 */
public final class TileGrid {

    /**
     * Random number generator.
     */
    private static final Random RANDOM = new Random();
    
    /**
     * The Game this TileSet is bound to.
     */
    private final Game game;
    
    /**
     * The tiles.
     */
    private final Tile[][] tiles;
    
    /**
     * The tile array width.
     */
    private final int width;
    
    /**
     * The tile array height.
     */
    private final int height;

    /**
     * The dimensions of a single tile.
     */
    private Dimension tileDimensions = new Dimension(128, 128);

    /**
     * Creates a new TileSet.
     *
     * @param game Game to attach to
     * @param width tiles width (amount of tiles, x)
     * @param height tiles height(amount of tiles, y)
     */
    public TileGrid(Game game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;
        tiles = new Tile[height][width];
    }

    /**
     * Sets the tile size.
     *
     * @param width tile width
     * @param height tile height
     */
    public void setTileSize(int width, int height) {
        tileDimensions.setSize(width, height);
    }

    /**
     * Shifts tiles and combines them in the given direction.
     *
     * @param dir
     */
    private void moveInDirection(Direction dir) {
        shiftTiles(dir);
        combineTiles(dir);
    }

    /**
     * Shifts the tile set in the given direction and inserts a random tile.
     * @param direction
     */
    public void performMove(Direction direction) {
        game.getTiles().moveInDirection(direction);
        insertSafeRandomTile();
    }

    /**
     * Performs tile combining in the given direction.
     *
     * @param dir directions to combine tiles in
     */
    private void combineTiles(Direction dir) {
        boolean swapped = true;

        switch (dir) {
            case UP:
                while (swapped) {
                    swapped = false;

                    for (int x = width - 1; x > 0; x--) {
                        for (int y = 0; y < height; y++) {
                            if (Tile.canCombine(tiles[y][x - 1], tiles[y][x])) {
                                tiles[y][x - 1].increaseValue();
                                tiles[y][x].reset();

                                tiles[y][x - 1].setCombinedThisTurn(true);
                                tiles[y][x].setCombinedThisTurn(true);

                                game.addScore(tiles[y][x - 1].getValue());
                                swapped = true;
                            }
                        }
                    }
                }
                break;

            case DOWN:
                while (swapped) {
                    swapped = false;

                    for (int x = 0; x < width - 1; x++) {
                        for (int y = 0; y < height; y++) {
                            if (Tile.canCombine(tiles[y][x + 1], tiles[y][x])) {
                                tiles[y][x + 1].increaseValue();
                                tiles[y][x].reset();

                                tiles[y][x + 1].setCombinedThisTurn(true);
                                tiles[y][x].setCombinedThisTurn(true);

                                game.addScore(tiles[y][x + 1].getValue());
                                swapped = true;
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                while (swapped) {
                    swapped = false;

                    for (int y = 0; y < height - 1; y++) {
                        for (int x = 0; x < width; x++) {
                            if (Tile.canCombine(tiles[y + 1][x], tiles[y][x])) {
                                tiles[y + 1][x].increaseValue();
                                tiles[y][x].reset();

                                tiles[y + 1][x].setCombinedThisTurn(true);
                                tiles[y][x].setCombinedThisTurn(true);

                                game.addScore(tiles[y + 1][x].getValue());
                                swapped = true;
                            }
                        }
                    }
                }
                break;

            case LEFT:
                while (swapped) {
                    swapped = false;

                    for (int y = height - 1; y > 0; y--) {
                        for (int x = 0; x < width; x++) {
                            if (Tile.canCombine(tiles[y - 1][x], tiles[y][x])) {
                                tiles[y - 1][x].increaseValue();
                                tiles[y][x].reset();

                                tiles[y - 1][x].setCombinedThisTurn(true);
                                tiles[y][x].setCombinedThisTurn(true);

                                game.addScore(tiles[y - 1][x].getValue());
                                swapped = true;
                            }
                        }
                    }
                }
                break;
        }
        resetCombineFlags();
    }

    /**
     * Shifts all game tiles in the given direction.
     *
     * @param dir direction to shift tiles in
     */
    private void shiftTiles(Direction dir) {
        boolean swapped = true;

        switch (dir) {
            case UP:
                while (swapped) {
                    swapped = false;

                    for (int x = width - 1; x > 0; x--) {
                        for (int y = 0; y < height; y++) {
                            if (!tiles[y][x - 1].isValid() && tiles[y][x].isValid()) {
                                tiles[y][x - 1].setValue(tiles[y][x].getValue());
                                tiles[y][x].setValue(-1);
                                swapped = true;
                            }
                        }
                    }
                }
                break;

            case DOWN:
                while (swapped) {
                    swapped = false;

                    for (int x = 0; x < width - 1; x++) {
                        for (int y = 0; y < height; y++) {
                            if (!tiles[y][x + 1].isValid() && tiles[y][x].isValid()) {
                                tiles[y][x + 1].setValue(tiles[y][x].getValue());
                                tiles[y][x].setValue(-1);
                                swapped = true;
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                while (swapped) {
                    swapped = false;

                    for (int y = 0; y < height - 1; y++) {
                        for (int x = 0; x < width; x++) {
                            if (!tiles[y + 1][x].isValid() && tiles[y][x].isValid()) {
                                tiles[y + 1][x].setValue(tiles[y][x].getValue());
                                tiles[y][x].setValue(-1);
                                swapped = true;
                            }
                        }
                    }
                }
                break;

            case LEFT:
                while (swapped) {
                    swapped = false;

                    for (int y = height - 1; y > 0; y--) {
                        for (int x = 0; x < width; x++) {
                            if (!tiles[y - 1][x].isValid() && tiles[y][x].isValid()) {
                                tiles[y - 1][x].setValue(tiles[y][x].getValue());
                                tiles[y][x].setValue(-1);
                                swapped = true;
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * Checks if the player can make a move.
     *
     * @return whether or not a move can be made
     */
    public boolean canMove() {
        // Checking if a free slot exists (and thus another move can be made)
        if (hasFreeSlot()) {
            return true;
        }

        // Up
        for (int x = width - 1; x > 0; x--) {
            for (int y = 0; y < height; y++) {
                if (Tile.canCombine(tiles[y][x - 1], tiles[y][x])) {
                    return true;
                }
            }
        }

        // Down
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height; y++) {
                if (Tile.canCombine(tiles[y][x + 1], tiles[y][x])) {
                    return true;
                }
            }
        }

        // Right
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                if (Tile.canCombine(tiles[y + 1][x], tiles[y][x])) {
                    return true;
                }
            }
        }

        // Left
        for (int y = height - 1; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                if (Tile.canCombine(tiles[y - 1][x], tiles[y][x])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Renders the tiles.
     *
     * @param g
     */
    public void render(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x].render(g);
            }
        }
    }

    /**
     * Populates the TileSet with empty tiles.
     */
    public void populate() {
        int offsetY = 0;
        int offsetX = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(offsetY, offsetX, tileDimensions);
                offsetX += tileDimensions.getWidth();
            }
            offsetX = 0;
            offsetY += tileDimensions.getHeight();
        }
    }

    /**
     * Resets the combined turn flags of all tiles.
     */
    private void resetCombineFlags() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x].setCombinedThisTurn(false);
            }
        }
    }

    /**
     * Checks if the tile set contains the given tile value.
     *
     * @param value tile value
     * @return whether or not the tile value appears
     */
    public boolean hasTile(int value) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].getValue() == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a free tile slot.
     *
     * @return
     */
    public boolean hasFreeSlot() {
        return freeSlots() > 0;
    }

    /**
     * Gets the amount of free slots on the tile.
     *
     * @return free slots
     */
    public int freeSlots() {
        int count = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].getValue() == -1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Gets the free tiles.
     *
     * @return free tiles
     */
    public Tile[] getFreeTiles() {
        Tile[] freeTiles = new Tile[freeSlots()];
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x].getValue() == -1) {
                    freeTiles[index] = tiles[y][x];
                    index++;
                }
            }
        }
        return freeTiles;
    }

    /**
     * Inserts a random tile.
     */
    public void insertRandomTile() {
        Tile[] tiles = getFreeTiles();
        tiles[RANDOM.nextInt(tiles.length)].setValue(getRandomTileValue());
    }

    /**
     * Attempting to insert a new random tile into the tile set.
     *
     * @return success
     */
    private boolean insertSafeRandomTile() {
        if (game.getTiles().hasFreeSlot()) {
            game.getTiles().insertRandomTile();
            return true;
        }
        return false;
    }

    /**
     * Gets the value of the next random tile.
     *
     * @return tile value
     */
    private int getRandomTileValue() {
        return RANDOM.nextInt(100) > 30 ? 2 : 4;
    }


    /**
     * The direction to shift/combine the tiles in.
     *
     * @author Pure_
     */
    public enum Direction {

        UP, DOWN, LEFT, RIGHT
    }
}
