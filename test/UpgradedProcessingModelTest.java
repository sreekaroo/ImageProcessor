import static org.junit.Assert.assertEquals;

import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.PPMInterpreter;
import java.io.IOException;
import model.PremiumProcessingModel;
import model.UpgradedProcessingModel;
import model.imagerepresentation.Image;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for an upgraded processing model.
 */
public class UpgradedProcessingModelTest {

  PremiumProcessingModel model;

  @Before
  public void setUp() throws Exception {
    model = new UpgradedProcessingModel();
  }

  // testing valid mosaic
  @Test
  public void mosaicValid() throws IOException {

    ImageFileInterpreter<Image> ppmConverter = new PPMInterpreter();

    Image birdImg1 = ppmConverter.convert("res/Birds.ppm");

    birdImg1.mosaic(10, 4);

    model.createLayer("Bird");
    model.upload(birdImg1);
    model.mosaic(10, 4);

    Image expectedImg = new Image(birdImg1);
    expectedImg.mosaic(10, 4);

    // testing that mosaic is properly called on the image
    for (int i = 0; i < model.getHeight(); i++) {
      for (int j = 0; j < model.getWidth(); j++) {
        assertEquals(expectedImg.getPixelAt(i, j).toString(), model.getPixelAt(i, j).toString());
      }
    }

  }

  // testing mosaic throws exception when no current layer exists
  @Test(expected = IllegalStateException.class)
  public void testingNoCurrLayerMosaic() {
    model.mosaic(100, 4);
  }


}