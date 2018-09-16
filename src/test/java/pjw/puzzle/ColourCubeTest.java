package pjw.puzzle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by philip on 16/09/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ColourCubeTest {


    @Test
    public void testAllColoursUnallocatedAtStart() {
        ColourCube cube = new ColourCube();
        assertThat(cube).isNotNull();
        assertThat(cube.getUnallocatedColours()).isNotNull();
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32768);
    }

    @Test
    public void testTakeOneAdjustsUnallocated() throws InvalidPuzzleColourException {
        ColourCube cube = new ColourCube();
        assertThat(cube).isNotNull();
        assertThat(cube.getUnallocatedColours()).isNotNull();
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32768);
        cube.takeColour(1,1,1);
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32767);
    }

    @Test
    public void testTakingSameColourTwiceIsGood() throws InvalidPuzzleColourException {
        ColourCube cube = new ColourCube();
        assertThat(cube).isNotNull();
        assertThat(cube.getUnallocatedColours()).isNotNull();
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32768);
        cube.takeColour(1,1,1);
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32767);
        cube.takeColour(1,1,1);
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32767);
    }

    @Test(expected = InvalidPuzzleColourException.class)
    public void testTakeInvalidColourFails() throws InvalidPuzzleColourException {
        ColourCube cube = new ColourCube();
        assertThat(cube).isNotNull();
        assertThat(cube.getUnallocatedColours()).isNotNull();
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32768);

        cube.takeColour(51,1,1);
    }

    @Test(expected = InvalidPuzzleColourException.class)
    public void testGetInvalidColourFails() throws InvalidPuzzleColourException {
        ColourCube cube = new ColourCube();
        assertThat(cube).isNotNull();
        assertThat(cube.getUnallocatedColours()).isNotNull();
        assertThat(cube.getUnallocatedColours().size()).isEqualTo(32768);

        cube.getColour(51,1,1);
    }
}
