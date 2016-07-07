package net.pure2.game2048.model.tile;

/**
 * Created by pure on 07/07/2016.
 */
public final class UnitTile {

    private int width;
    private int height;

    public UnitTile(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
