package pjw.puzzle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by philip on 16/09/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ColourCanvasTest {

    @Test
    public void dimensionsLookGood() {
        ColourCanvas canvas = new ColourCanvas();
        assertThat(canvas).isNotNull();
        assertThat(canvas.getHeight() * canvas.getWidth()).isEqualTo(32768);
        assertThat(canvas.blankSpaces()).isEqualTo(32768);
    }

    @Test
    public void scoopsAddUp1() throws InvalidPuzzleColourException, InvalidCanvasCoordinateException {
        ColourCanvas canvas = new ColourCanvas();
        assertThat(canvas).isNotNull();
        assertThat(canvas.blankSpaces()).isEqualTo(32768);

        ColourCube cc = new ColourCube();
        Set<Color> scoop = cc.takeScoop(Color.RED, 2000);
        assertThat(cc.getUnallocatedColours().size()).isEqualTo(32768 - 2000);

        canvas.drawCircle(23, 22, scoop);
        assertThat(canvas.blankSpaces()).isEqualTo(32768 - 2000);
    }
}
