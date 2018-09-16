package pjw.puzzle;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This is the picture/image. holds an array of Color objects.
 * These are populated using operations like drawBox, drawCircle  until done.
 * At the end a fillRest should be used to fill in the unpainted pixels with
 * random colours
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
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.canvas[i][j] = null;
            }

        }
    }

    public Color getPixel(int x, int y) throws InvalidCanvasCoordinateException {
        validateCoordinates(x, y);
        return canvas[x][y];
    }

    public void setPixel(int x, int y, Color colour) throws InvalidCanvasCoordinateException {
        validateCoordinates(x, y);
        canvas[x][y] = colour;
    }

    private void validateCoordinates(int x, int y) throws InvalidCanvasCoordinateException {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            throw new InvalidCanvasCoordinateException("x:"+x+",y:"+y);
        }
    }

    public void drawBox(int startX, int startY, int width, Set<Color> colours) throws InvalidCanvasCoordinateException {
        drawBox(startX, startY, width, new ArrayList<Color>(colours));
    }

    /**
     * draw a box @ x,y of width until the colours run out
     * @param startX left of box
     * @param startY top of box
     * @param width width of box
     * @param colours colours to apply
     * @throws InvalidCanvasCoordinateException
     */
    public void drawBox(int startX, int startY, int width, java.util.List<Color> colours) throws InvalidCanvasCoordinateException {
        int rightMostX = Math.min(getWidth() -1, startX + width - 1);
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
                    if (y >= height) {
                        y--;
                        done = true;
                    }
                }
            }
        }
    }

    /**
     * draw a circle starting at the x/y coords. Keep drawing, and spiralling
     * outwards, until the colours (paint) runs out.
     * @param startX circle centre x coord
     * @param startY circle centre y coord
     * @param colors colours to apply
     * @throws InvalidCanvasCoordinateException
     */
    public void drawCircle(int startX, int startY, Set<Color> colors) throws InvalidCanvasCoordinateException {

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

    /**
     * get unallocated colours from the Colour cube,
     * randomly choose one an put in a list.
     * then draw a box as big as the image and splash the colours
     * into the gaps.
     * @param cc the ColourCube
     * @throws InvalidCanvasCoordinateException
     * @throws InvalidPuzzleColourException
     */
    public void fillRest(ColourCube cc) throws InvalidCanvasCoordinateException, InvalidPuzzleColourException {
        Set<Color> colours = cc.getUnallocatedColours();
        if (colours.size() > 0) {

            Color[] colourArray;
            List<Color> randomColours = new ArrayList<>();

            Random rand = new Random();
            while (colours.size() > 0) {
                colourArray = new Color[colours.size()];
                colourArray = colours.toArray(colourArray);
                int idx = rand.nextInt(colourArray.length);
                Color colourToTake = colourArray[idx];
                randomColours.add(colourToTake);
                cc.takeColour(colourToTake.getRed()/8, colourToTake.getGreen()/8,colourToTake.getBlue()/8);
                colours.remove(colourArray[idx]);
            }
            if (randomColours.size() > 0) {
                drawBox(0, 0, getWidth(), randomColours);
            }
        }
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
