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
import java.util.ArrayList;
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
     * Shifts tiles and combines them in the given direction.
     * @param dir
     */
    private void moveInDirection(Direction dir) {
        shiftTiles(dir);
        combineTiles(dir);
    }

    /**
     * Shifts the tile set in the given direction and inserts a random tile.
     * @param dir
     */
    public void performMove(Direction dir) {
        game.getTiles().moveInDirection(dir);
        insertSafeRandomTile();
    }

    /**
     * Attempts to combine tiles in direction.
     * @return If the combine was successful.
     */
    private boolean tryCombineTilesInDirection(int x, int y, int dx, int dy, boolean swapped) {
        if (Tile.canCombine(tiles[y + dy][x + dx], tiles[y][x])) {
            tiles[y + dy][x + dx].increaseValue();
            tiles[y][x].reset();

            tiles[y + dy][x + dx].setCombinedThisTurn(true);
            tiles[y][x].setCombinedThisTurn(true);

            game.addScore(tiles[y + dy][x + dx].getValue());
            return true;
        }
        return swapped;
    }

    /**
     * Performs tile combining in the given direction, roughly an offspring of bubble-sort.
     * @param dir directions to combine tiles in
     */
    private void combineTiles(Direction dir) {
        boolean swapped = true;

        while (swapped) {
            swapped = false;

            switch (dir) {
                case UP:
                    for (int x = width - 1; x > 0; x--) {
                        for (int y = 0; y < height; y++) {
                            swapped = tryCombineTilesInDirection(x, y, -1, 0, swapped);
                        }
                    }
                    break;
                case DOWN:
                    for (int x = 0; x < width - 1; x++) {
                        for (int y = 0; y < height; y++) {
                            swapped = tryCombineTilesInDirection(x, y, 1, 0, swapped);
                        }
                    }
                    break;
                case RIGHT:
                    for (int y = 0; y < height - 1; y++) {
                        for (int x = 0; x < width; x++) {
                            swapped = tryCombineTilesInDirection(x, y, 0, 1, swapped);
                        }
                    }
                    break;
                case LEFT:
                    for (int y = height - 1; y > 0; y--) {
                        for (int x = 0; x < width; x++) {
                            swapped = tryCombineTilesInDirection(x, y, 0, -1, swapped);
                        }
                    }
                    break;
            }
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

        while (swapped) {
            swapped = false;

            switch (dir) {
                case UP:
                    for (int x = width - 1; x > 0; x--) {
                        for (int y = 0; y < height; y++) {
                            swapped = tryShiftTilesInDirection(x, y, -1, 0, swapped);
                        }
                    }
                    break;
                case DOWN:
                    for (int x = 0; x < width - 1; x++) {
                        for (int y = 0; y < height; y++) {
                            swapped = tryShiftTilesInDirection(x, y, 1, 0, swapped);
                        }
                    }
                    break;
                case RIGHT:
                    for (int y = 0; y < height - 1; y++) {
                        for (int x = 0; x < width; x++) {
                            swapped = tryShiftTilesInDirection(x, y, 0, 1, swapped);
                        }
                    }
                    break;
                case LEFT:
                    for (int y = height - 1; y > 0; y--) {
                        for (int x = 0; x < width; x++) {
                            swapped = tryShiftTilesInDirection(x, y, 0, -1, swapped);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Attempts to shift tiles in direction.
     * @return
     */
    private boolean tryShiftTilesInDirection(int x, int y, int dx, int dy, boolean swapped) {
        if (!tiles[y + dy][x + dx].isValid() && tiles[y][x].isValid()) {
            tiles[y + dy][x + dx].setValue(tiles[y][x].getValue());
            tiles[y][x].setValue(-1);
            return true;
        }
        return swapped;
    }

    /**
     * Checks if the player can make a move.
     *
     * @return whether or not a move can be made
     */
    public boolean hasMoves() {
        // Checking if a free slot exists (and thus another move can be made)
        if (hasFreeSlot()) {
            return true;
        }

        // Up/Down directions
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height; y++) {
                if (Tile.canCombine(tiles[y][x + 1], tiles[y][x])) {
                    return true;
                }
            }
        }

        // Left/Right directions
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                if (Tile.canCombine(tiles[y + 1][x], tiles[y][x])) {
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
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col].render(g);
            }
        }
    }

    /**
     * Resets the TileSet to contain only empty tiles.
     */
    public void reset() {
        int rowOffset = 0;
        int colOffset = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col] = new Tile(rowOffset, colOffset, tileDimensions);
                colOffset += tileDimensions.getWidth();
            }
            colOffset = 0;
            rowOffset += tileDimensions.getHeight();
        }
    }

    /**
     * Resets the combined turn flags of all tiles.
     */
    private void resetCombineFlags() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col].setCombinedThisTurn(false);
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
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tiles[row][col].getValue() == value) {
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
     * @return free slots
     */
    public int freeSlots() {
        return getFreeTiles().length;
    }

    /**
     * Gets the free tiles.
     * @return free tiles
     */
    public Tile[] getFreeTiles() {
        ArrayList<Tile> freeTiles = new ArrayList<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tiles[row][col].getValue() == -1) {
                    freeTiles.add(tiles[row][col]);
                }
            }
        }
        return freeTiles.toArray(new Tile[freeTiles.size()]);
    }

    /**
     * Inserts a random tile.
     */
    public void insertRandomTile() {
        Tile[] tiles = getFreeTiles();
        int randomIdx = RANDOM.nextInt(tiles.length);
        tiles[randomIdx].setValue(randomTileValue());
    }

    /**
     * Attempting to insert a new random tile into the tile set.
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
     * @return tile value
     */
    private int randomTileValue() {
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