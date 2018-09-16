package pjw.puzzle;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * Created by philip on 16/09/18.
 */
@RestController
public class PuzzleController {

    @RequestMapping(value = "/", produces = MediaType.IMAGE_PNG_VALUE)
    public void showImage(HttpServletResponse response) throws InvalidPuzzleColourException, InvalidCanvasCoordinateException {
        BufferedImage img = new BufferedImage(256,128,BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = img.createGraphics();
        ColourCube cc = new ColourCube();
        ColourCanvas canvas = new ColourCanvas();

        buildPicture(cc, canvas);

        populateResponse(response, cc, canvas, gr, img);
    }

    @RequestMapping(value = "/pic2", produces = MediaType.IMAGE_PNG_VALUE)
    public void showImage2(HttpServletResponse response) throws InvalidPuzzleColourException, InvalidCanvasCoordinateException {
        BufferedImage img = new BufferedImage(256,128,BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = img.createGraphics();
        ColourCube cc = new ColourCube();
        ColourCanvas canvas = new ColourCanvas();

        buildPicture2(cc, canvas);

        populateResponse(response, cc, canvas, gr, img);
    }

    private void buildPicture(ColourCube cc, ColourCanvas canvas) throws InvalidPuzzleColourException, InvalidCanvasCoordinateException {
        Set<Color> scoop = cc.takeScoop(Color.CYAN, 1320);
        canvas.drawBox(25, 10, 50, scoop);

        scoop = cc.takeScoop(Color.YELLOW, 2620);
        canvas.drawCircle(53, 20, scoop);


        scoop = cc.takeScoop(Color.RED, 7300);
        canvas.drawCircle(130, 60, scoop);

        scoop = cc.takeScoop(Color.BLUE, 8000);
        canvas.drawBox(133, 0, 90, scoop);

        canvas.fillRest(cc);
    }

    private void buildPicture2(ColourCube cc, ColourCanvas canvas) throws InvalidPuzzleColourException, InvalidCanvasCoordinateException {
        Set<Color> scoop = cc.takeScoop(Color.PINK, 1320);
        canvas.drawCircle(45, 10, scoop);

        scoop = cc.takeScoop(Color.PINK, 120);
        canvas.drawBox(12, 16, 25, scoop);


        scoop = cc.takeScoop(Color.RED, 7300);
        canvas.drawCircle(130, 60, scoop);

        scoop = cc.takeScoop(Color.BLUE, 8000);
        canvas.drawBox(133, 0, 90, scoop);

        canvas.fillRest(cc);
    }

    private void populateResponse(HttpServletResponse response, ColourCube cc,
                                  ColourCanvas canvas, Graphics2D gr, BufferedImage img) throws InvalidCanvasCoordinateException, InvalidPuzzleColourException {
        canvas.fillRest(cc);
        canvas.renderCanvas(gr);

        try {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());

            IOUtils.copy(is,  response.getOutputStream());

        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }


}
