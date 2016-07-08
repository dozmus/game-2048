package notpure.game2048.model;

import org.newdawn.slick.Color;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Loads the colour scheme of the game from a resource file.
 * @author Pure_
 */
public final class ColourScheme {

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
    /**
     * The background color.
     */
    private static Color BACKGROUND_COLOR = null;
    /**
     * The colour to be used when drawing the score items.
     */
    private static Color SCORE_TEXT_COLOR = null;

    public static void load(InputStream inputStream) {
        Scanner in = new Scanner(inputStream);

        while (in.hasNextLine()) {
            String line = in.nextLine();

            // Validate line
            if (!isEntryLine(line))
                continue;

            // Process line
            parseEntry(line);
        }
    }

    private static void parseEntry(String line) {
        String[] parts = line.split("=");
        String key = parts[0].trim();
        String value = parts[1].trim();

        switch(key) {
            case "BackgroundColor":
                BACKGROUND_COLOR = parseColor(value);
                break;
            case "ScoreTextColor":
                SCORE_TEXT_COLOR = parseColor(value);
                break;
            case "TileTextColor":
                String[] values = value.split(" ");
                int tileValue = Integer.parseInt(values[0]);

                // Insert into map
                COLOURS.put(tileValue, new Color[] {
                        parseColor(values[1]),
                        parseColor(values[2])
                });
                break;
        }
    }

    private static Color parseColor(String s) {
        // Remove syntactical components
        if (s.startsWith("rgb("))
            s = s.substring(4);

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

    private static boolean isEntryLine(String s) {
        return s != null && !s.isEmpty() && !s.startsWith("#") && s.contains("=");
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

    public static Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }

    public static Color getScoreTextColor() {
        return SCORE_TEXT_COLOR;
    }
}
