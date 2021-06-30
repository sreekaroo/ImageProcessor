import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.ComplexProcessingModel;
import model.ImageProcessingModel;
import model.LayeredImageProcessingModel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Kernel;
import model.imagerepresentation.Layer;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;
import model.programmaticimage.Checkerboard;
import model.programmaticimage.ProgrammaticImage;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Complex Processing Model.
 */
public class ComplexProcessingModelTest {

  LayeredImageProcessingModel model1;
  ImageProcessingModel model2;

  ProgrammaticImage checkerBoard1b1;
  ProgrammaticImage checkerBoard2b2;


  Layer layer1;
  Layer layer2;
  Layer layer3;

  Image cb1b1;
  Image image2x3;
  Image image2x2;


  @Before
  public void setUp() throws IOException {
    model1 = new ComplexProcessingModel();

    cb1b1 = new Checkerboard(1, Color.black, Color.red).create();
    checkerBoard1b1 = new Checkerboard(1, Color.BLACK, Color.RED);
    checkerBoard2b2 = new Checkerboard(2, Color.BLACK, Color.WHITE);

    layer1 = new Layer("Cherries");
    layer2 = new Layer("Birds");
    layer3 = new Layer("NeelCookie");

    List<List<Pixel>> twoXThree = new ArrayList<>();
    twoXThree.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(64, 0, 0, new Position2D(0, 1)))));
    twoXThree.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 64, 0, new Position2D(1, 0)),
        new Pixel(0, 0, 64, new Position2D(1, 1)))));
    twoXThree.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(16, 0, 0, new Position2D(2, 0)),
        new Pixel(0, 16, 0, new Position2D(2, 1)))));

    image2x3 = new Image(twoXThree, 255);

    List<List<Pixel>> twoXTwo = new ArrayList<>();
    twoXTwo.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(16, 64, 128, new Position2D(0, 1)))));
    twoXTwo.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(80, 0, 80, new Position2D(1, 0)),
        new Pixel(0, 0, 240, new Position2D(1, 1)))));
    image2x2 = new Image(twoXTwo, 255);

  }


  @Test
  public void testBlur() {

    model1.createLayer("Image2x3");
    model1.setCurrent("Image2x3");
    model1.upload(image2x3);
    Image copy2x3 = new Image(image2x3);
    model1.blur();
    copy2x3.filter(Kernel.BLUR_KERNEL);
    copy2x3.clamp(0, 255);
    for (int i = 0; i < copy2x3.getHeight(); i++) {
      for (int j = 0; j < copy2x3.getWidth(); j++) {
        assertEquals(copy2x3.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }

  }

  @Test
  public void testSharpen() {
    model1.createLayer("Image2x3");
    model1.setCurrent("Image2x3");
    model1.upload(image2x3);
    Image copy = new Image(image2x3);
    model1.sharpen();
    copy.filter(Kernel.SHARPEN_KERNEL);
    copy.clamp(0, 255);
    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }

  }

  @Test
  public void testMonochrome() {
    model1.createLayer("Image2x3");
    model1.setCurrent("Image2x3");
    model1.upload(image2x3);
    Image copy2x3 = new Image(image2x3);
    model1.monochrome();
    copy2x3.colorTransform(Kernel.GREYSCALE_KERNEL);
    copy2x3.clamp(0, 255);
    for (int i = 0; i < copy2x3.getHeight(); i++) {
      for (int j = 0; j < copy2x3.getWidth(); j++) {
        assertEquals(copy2x3.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }

  }

  @Test
  public void testSepia() {
    model1.createLayer("Image2x3");
    model1.setCurrent("Image2x3");
    model1.upload(image2x3);
    Image copy2x3 = new Image(image2x3);
    model1.sepia();
    copy2x3.colorTransform(Kernel.SEPIA_KERNEL);
    for (int i = 0; i < copy2x3.getHeight(); i++) {
      for (int j = 0; j < copy2x3.getWidth(); j++) {
        assertEquals(copy2x3.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }


  @Test
  public void testUpload() throws IOException {

    // create image
    // upload image
    // check that its the same image
    model1.createLayer("Checkerboard1b1");
    model1.setCurrent("Checkerboard1b1");
    model1.upload(cb1b1);
    // check to see if both checkerboards are the same
    for (int i = 0; i < cb1b1.getHeight(); i++) {
      for (int j = 0; j < cb1b1.getWidth(); j++) {
        assertEquals(cb1b1.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }


  @Test
  public void testCreateImage() {

    Image actual1b1 = model1.createImage(checkerBoard1b1);
    Image actual2b2 = model1.createImage(checkerBoard2b2);
    assertEquals(checkerBoard1b1.create().getPixelAt(0, 0),
        actual1b1.getPixelAt(0, 0));

    for (int i = 0; i < actual2b2.getHeight(); i++) {
      for (int j = 0; j < actual2b2.getWidth(); j++) {
        assertEquals(checkerBoard2b2.create().getPixelAt(i, j), actual2b2.getPixelAt(i, j));
      }
    }
  }

  @Test
  public void testGetPixelAt() {
    Image actual2b2 = model1.createImage(checkerBoard2b2);
    assertEquals(new Pixel(0, 0, 0, new Position2D(0, 0)),
        actual2b2.getPixelAt(0, 0));
    assertEquals(new Pixel(255, 255, 255, new Position2D(0, 1)),
        actual2b2.getPixelAt(0, 1));
    assertEquals(new Pixel(255, 255, 255, new Position2D(1, 0)),
        actual2b2.getPixelAt(1, 0));
    assertEquals(new Pixel(0, 0, 0, new Position2D(1, 1)),
        actual2b2.getPixelAt(1, 1));
  }

  @Test
  public void testGetWidth() {
    cb1b1 = checkerBoard1b1.create();
    model1.createLayer("Checkerboard1b1");
    model1.setCurrent("Checkerboard1b1");
    model1.upload(cb1b1);
    assertEquals(1, model1.getWidth());
  }

  @Test
  public void testGetHeight() {
    cb1b1 = checkerBoard1b1.create();
    model1.createLayer("Checkerboard1b1");
    model1.setCurrent("Checkerboard1b1");
    model1.upload(cb1b1);
    assertEquals(1, model1.getHeight());
  }


  @Test
  public void testSetCurrent() {
    model1.createLayer("Birds");
    model1.setCurrent("Birds");
    assertEquals("Birds^ ", model1.toString());
    model1.createLayer("Cherries");
    model1.setCurrent("Cherries");
    assertEquals("Cherries^ Birds ", model1.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentException() {
    model1.createLayer("Birds");
    model1.setCurrent("Cherries");
  }


  // tests remove in normal case.
  @Test
  public void testRemove() {
    model1.createLayer("Cherries");
    model1.setCurrent("Cherries");
    model1.createLayer("Birds");
    model1.remove();
    model1.setCurrent("Birds");
    assertEquals("Birds^ ", model1.toString());
  }

  // tests an exception if thrown when there is no layer to remove.
  @Test(expected = IllegalStateException.class)
  public void testRemoveException() {
    model1.remove();
  }

  // creating a layer normal case.
  @Test
  public void testCreateLayer() {

    model1.createLayer("Cherries");
    assertEquals("Cherries^ ", model1.toString());
    model1.createLayer("Birds");
    assertEquals("Cherries^ Birds ", model1.toString());
  }

  // tests creating a layer with the same name.
  @Test(expected = IllegalArgumentException.class)
  public void testCreateLayerException() {
    model1.createLayer("Cherries");
    assertEquals("Cherries^ ", model1.toString());
    model1.createLayer("Cherries");
  }


  @Test
  public void testToggle() {
    model1.createLayer("Birds");
    model1.setCurrent("Birds");
    model1.toggle("Birds");
    assertEquals("Birds*^ ", model1.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCanOnlyUploadSameDimensions() {
    model1.createLayer("TwoByTwo");
    model1.createLayer("4By4");

    model1.setCurrent("TwoByTwo");
    model1.upload(new Checkerboard(2, Color.black, Color.red).create());

    model1.setCurrent("4By4");
    model1.upload(new Checkerboard(4, Color.black, Color.red).create());
  }

}







