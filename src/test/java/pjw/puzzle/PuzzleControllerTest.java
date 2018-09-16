package pjw.puzzle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by philip on 16/09/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PuzzleControllerTest {

    @Autowired
    PuzzleController puzzleController;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() throws Exception {
        assertThat(puzzleController).isNotNull();
    }
}
