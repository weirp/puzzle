package pjw.puzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by philip on 16/09/18.
 */
public class ImageCoordinate {
    public static final int MAX_COLOUR_VALUE = 31;
    private int red, green, blue;

    public ImageCoordinate(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public Set<ImageCoordinate> getNeighboursAtLevel(int lvl) {
        Set<ImageCoordinate> neighbours = new HashSet<>();
        Set<Integer> reds = new HashSet<Integer>();
        Set<Integer> greens = new HashSet<Integer>();
        Set<Integer> blues = new HashSet<Integer>();

        reds.add(red);
        greens.add(green);
        blues.add(blue);

        for (int i = 0; i <= lvl; i++) {
            reds.add(Math.max(red - i, 0));
            reds.add(Math.min(red + i, MAX_COLOUR_VALUE));

            greens.add(Math.max(green - i, 0));
            greens.add(Math.min(green + i, MAX_COLOUR_VALUE));

            blues.add(Math.max(blue - i, 0));
            blues.add(Math.min(blue + i, MAX_COLOUR_VALUE));
        }
        if (lvl == 0) {
            neighbours.add(this);
        }

        for (int redValue: reds) {
            for (int greenValue: greens) {
                for (int blueValue: blues) {
                    neighbours.add(new ImageCoordinate(redValue,greenValue,blueValue));
                }
            }
        }

        return neighbours;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public  String toString() {
        return "red:"+ red +" green:"+ green +" blue:"+ blue;
    }
}

