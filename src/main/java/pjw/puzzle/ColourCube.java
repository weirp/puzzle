package pjw.puzzle;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by philip on 16/09/18.
 */
public class ColourCube {

    private static final int MIN_COLOUR = 0;
    private static final int MAX_COLOUR = 31;

    private Color[][][] colours;

    public ColourCube() {
        this.colours = new Color[32][32][32];
        for (int red = 0; red < 32; red++) {
            for (int green = 0; green < 32; green++) {
                for (int blue = 0; blue < 32; blue++) {
                    colours[red][green][blue] =
                        new Color(
                            scaleIndexUp(red),
                            scaleIndexUp(green),
                            scaleIndexUp(blue));
                }
            }
        }
    }

    public Color getColour(int red, int green, int blue) throws InvalidPuzzleColourException {
        if (!areCoordinatesValid(red, green, blue)) {
            throw new InvalidPuzzleColourException("r:"+red+",g:"+green+",b:"+blue);
        }
        return colours[red][green][blue];
    }

    public Color takeColour(int red, int green, int blue) throws InvalidPuzzleColourException {
        if (!areCoordinatesValid(red, green, blue)) {
            throw new InvalidPuzzleColourException("r:"+red+",g:"+green+",b:"+blue);
        }
        Color colour = colours[red][green][blue];
        colours[red][green][blue] = null;
        return colour;
    }

    private boolean areCoordinatesValid(int red, int green, int blue) {
        return red >= MIN_COLOUR && red <= MAX_COLOUR
                && green >= MIN_COLOUR && green <= MAX_COLOUR
                && blue >= MIN_COLOUR && blue <= MAX_COLOUR;
    }

    public Set<Color> takeScoop(Color colour, int count) throws InvalidPuzzleColourException {
        return takeScoop(colour.getRed()/8, colour.getGreen()/8, colour.getBlue()/8, count);
    }
    /**
     * is like a scoop of ice-cream. take a bunch of colours surrounding a
     * point from the colour cube.
     * @param red
     * @param green
     * @param blue
     * @param count
     * @return
     */
    public Set<Color> takeScoop(int red, int green, int blue, int count) throws InvalidPuzzleColourException {
        Set<Color> coloursTaken = new HashSet<Color>();

        int neighbourLevel = 0;

        ImageCoordinate rgb = new ImageCoordinate(red, green, blue);
        // ok, no termination at moment for out of colours
        while (coloursTaken.size() < count) {
            Set<ImageCoordinate> neighbours = rgb.getNeighboursAtLevel(neighbourLevel);
            for (ImageCoordinate neighbour: neighbours) {
                if (coloursTaken.size() < count
                        && getColour(neighbour.getRed(), neighbour.getGreen(), neighbour.getBlue()) != null) {
                    coloursTaken.add(takeColour(neighbour.getRed(), neighbour.getGreen(), neighbour.getBlue()));
                }
            }
            neighbourLevel++;
        }

        return coloursTaken;
    }

    public Set<Color> getUnallocatedColours() {
        Set<Color> remainingColours = new HashSet<Color>();
        for (int r = 0; r < 32; r++) {
            for (int g = 0; g < 32; g++) {
                for (int b = 0; b < 32; b++) {
                    if (colours[r][g][b] != null) {
                        remainingColours.add(colours[r][g][b]);
                    }
                }
            }
        }
        return remainingColours;
    }
    private int scaleIndexUp(int idx) {
        return idx == 0 ? 0 : ((idx * 8) - 1);
    }


}
