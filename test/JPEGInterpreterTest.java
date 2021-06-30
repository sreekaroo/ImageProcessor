import static org.junit.Assert.assertTrue;

import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.JPEGInterpreter;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import model.imagerepresentation.Image;
import model.programmaticimage.Checkerboard;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests and examples for JPEGInterpreter.
 */
public class JPEGInterpreterTest {

  Image cb2by2;


  @Before
  public void setUp() {
    cb2by2 = new Checkerboard(2, Color.BLACK, Color.RED).create();
  }

  //TODO figure out how to test class
  @Test
  public void testExport() throws IOException {

    ImageFileInterpreter<Image> interpreter = new JPEGInterpreter();

    interpreter.export("actual1.jpg", cb2by2);
    File actualFile = new File("actual1.jpg");

    assertTrue(actualFile.exists());
  }


}