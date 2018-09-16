package pjw.puzzle;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by philip on 16/09/18.
 */
public class ColourCanvas {
    Color[][] canvas;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int width;
    private int height;

    public ColourCanvas() {
        this.width = 256;
        this.height = 128;
        this.canvas = new Color[this.width][this.height];
    }

    public Color getPixel(int x, int y) {
        return canvas[x][y];
    }

    public void setPixel(int x, int y, Color colour) {
        canvas[x][y] = colour;
    }


    public void drawBox(int startX, int startY, int width, Set<Color> colours) {
        drawBox(startX, startY, width, new ArrayList<Color>(colours));
    }

    public void drawBox(int startX, int startY, int width, java.util.List<Color> colours) {
        int rightMostX = startX + width - 1;
        int x = startX;
        int y = startY;
        for (Color colour: colours) {
            boolean done = false;
            while (!done) {
                if (getPixel(x, y) == null) {
                    setPixel(x, y, colour);
                    done = true;
                }
                x++;
                if (x > rightMostX) {
                    x = startX;
                    y++;
                }
            }
        }
    }

    public void drawCircle(int startX, int startY, Set<Color> colors) {

        int circleRadius = 0;
        List<Point> path = buildCircleCoords(startX, startY, circleRadius);
        for (Color colour: colors) {
            boolean done = false;
            while (!done) {
                if (path == null || path.size() == 0) {
                    circleRadius++;
                    path = buildCircleCoords(startX, startY, circleRadius);
                }
                Point thisPoint = path.remove(0);
                if (getPixel(thisPoint.x, thisPoint.y) == null) {
                    setPixel(thisPoint.x, thisPoint.y,colour);
                    done = true;
                }
            }
        }
    }

    private List<Point> buildCircleCoords(int startX, int startY, int radius) {
        List<Point> points = new ArrayList<>();

        for (int x = (-1 * radius); x <= radius; x++) {
            for (int y = (-1 * radius); y <= radius ; y++) {
                if ( (x * x) + (y * y) < (radius * radius)) {
                    int newX = x + startX;
                    int newY = y + startY;
                    if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                        points.add(new Point(newX, newY));
                    }
                }
            }
            
        }
        return points;
    }


    public void fillRest(ColourCube cc) {
        Set<Color> colours = cc.getUnallocatedColours();
        Color[] colourArray;
        List<Color> randomColours = new ArrayList<>();

        Random rand = new Random();
        while (colours.size() > 0) {
            colourArray = new Color[colours.size()];
            colourArray =  colours.toArray(colourArray);
            int idx = rand.nextInt(colourArray.length);
            randomColours.add(colourArray[idx]);
            colours.remove(colourArray[idx]);
        }
        drawBox(0,0,256, randomColours);
    }

    public void renderCanvas(Graphics2D graphics) {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.canvas[x][y] != null) {
                    graphics.setColor(this.canvas[x][y]);
                    graphics.draw(new Rectangle(x, y, 1, 1));
                }
            }
        }
    }

    public int blankSpaces() {
        int count = 0;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (this.canvas[x][y] == null) {
                    count++;
                }
            }
        }
        return count;
    }

}
