package net.pure2.game2048.tiles;

import org.newdawn.slick.Color;

/**
 * The class which fetches the colours which are associated with the given tile.
 *
 * @author Pure_ <mail@pure2.net>
 */
public final class ColourUtils {

    /**
     * The id for the tile background colour.
     */
    public static int TILE_COLOUR_ID = 0;
    
    /**
     * The id for the tile text colour.
     */
    public static int TEXT_COLOUR_ID = 1;
    
    /**
     * Gets the colour scheme associated with the given value.
     * 
     * @param value tile value
     * @return colour scheme
     */
    public static Color[] getColourScheme(int value) {
        Color[] colours = new Color[2];
        
        switch (value) {
            case -1:
                colours[TILE_COLOUR_ID] = new Color(209, 213, 255);
                colours[TEXT_COLOUR_ID] = new Color(0, 0, 0);
                break;
                
            case 2:
                colours[TILE_COLOUR_ID] = new Color(150, 158, 255);
                colours[TEXT_COLOUR_ID] = new Color(0, 0, 0);
                break;
                
            case 4:
                colours[TILE_COLOUR_ID] = new Color(99, 11, 255);
                colours[TEXT_COLOUR_ID] = new Color(102, 250, 176);
                break;
                
            case 8:
                colours[TILE_COLOUR_ID] = new Color(188, 136, 247);
                colours[TEXT_COLOUR_ID] = new Color(38, 34, 168);
                break;
                
            case 16:
                colours[TILE_COLOUR_ID] = new Color(117, 34, 168);
                colours[TEXT_COLOUR_ID] = new Color(181, 247, 189);
                break;
                
            case 32:
                colours[TILE_COLOUR_ID] = new Color(0, 255, 81);
                colours[TEXT_COLOUR_ID] = new Color(43, 46, 224);
                break;
                
            case 64:
                colours[TILE_COLOUR_ID] = new Color(242, 255, 0);
                colours[TEXT_COLOUR_ID] = new Color(59, 212, 209);
                break;
                
            case 128:
                colours[TILE_COLOUR_ID] = new Color(219, 136, 136);
                colours[TEXT_COLOUR_ID] = new Color(234, 235, 232);
                break;
                
            case 256:
                colours[TILE_COLOUR_ID] = new Color(235, 40, 40);
                colours[TEXT_COLOUR_ID] = new Color(220, 220, 230);
                break;
                
            case 512:
                colours[TEXT_COLOUR_ID] = new Color(118, 0, 173);
                colours[TEXT_COLOUR_ID] = new Color(240, 223, 93);
                break;
                
            case 1024:
                colours[TEXT_COLOUR_ID] = new Color(179, 36, 7);
                colours[TEXT_COLOUR_ID] = new Color(220, 222, 222);
                break;
                
            case 2048:
                colours[TILE_COLOUR_ID] = new Color(240, 34, 161);
                colours[TEXT_COLOUR_ID] = new Color(34, 240, 64);
                break;
        }
        return colours;
    }
}
