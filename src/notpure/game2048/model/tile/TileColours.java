package notpure.game2048.model.tile;

import org.newdawn.slick.Color;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The class which fetches the colours which are associated with the given tile.
 *
 * @author Pure_
 */
public final class TileColours {

    /**
     * The idx for the tile background colour.
     */
    public static int TILE_COLOUR_IDX = 0;

    /**
     * The idx for the tile text colour.
     */
    public static int TEXT_COLOUR_IDX = 1;
    /**
     * A mapping of tile value to colour scheme.
     */
    private static HashMap<Integer, Color[]> COLOURS = new HashMap<>();

    public static void load(InputStream in) {
        Scanner s = new Scanner(in);

        // Try to parse whole file
        while (s.hasNextLine()) {
            String line = s.nextLine();

            // Skip invalid lines
            if (!isValidLine(line))
                continue;

            // Parse line
            String[] frag = line.split(" ");
            int value = Integer.parseInt(frag[0]);
            Color color1 = colorFromText(frag[2]);
            Color color2 = colorFromText(frag[3]);

            // Add to list
            COLOURS.put(value, new Color[] {
                    color1, color2
            });
        }

        // Ensure we have valid entries
        // TODO impl
    }

    private static Color colorFromText(String s) {
        if (s.startsWith("("))
            s = s.substring(1);

        if (s.endsWith(")"))
            s = s.substring(0, s.length() - 1);

        // Parse
        String[] frag = s.split(",");
        int r = Integer.parseInt(frag[0].trim());
        int g = Integer.parseInt(frag[1].trim());
        int b = Integer.parseInt(frag[2].trim());
        return new Color(r, g, b);
    }

    /**
     * If the given line is not a comment and not empty.
     * @param line
     * @return
     */
    private static boolean isValidLine(String line) {
        return line.length() > 0 && !line.startsWith("#") && line.contains(" = ");
    }

    /**
     * Gets the colour scheme associated with the given value.
     *
     * @param value tile value
     * @return colour scheme
     */
    public static Color[] getColourScheme(int value) {
        return COLOURS.containsKey(value) ? COLOURS.get(value) : null;
    }
}
