import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.imagerepresentation.Image;
import model.imagerepresentation.Kernel;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;
import model.programmaticimage.Checkerboard;
import org.junit.Before;
import org.junit.Test;

/**
 * Examples and tests for Image class.
 */
public class ImageTest {

  List<List<Pixel>> twoXTwo;
  List<List<Pixel>> twoXTwoBlurred;
  List<List<Pixel>> twoXTwoSharpened;
  List<List<Pixel>> twoXThree;
  List<List<Pixel>> twoXThreeBlurred;
  List<List<Pixel>> twoXTwoMonochrome;
  List<List<Pixel>> twoXThreeMonochrome;
  List<List<Pixel>> twoXTwoSepia;
  List<List<Pixel>> twoXThreeSepia;

  Image image2x2;
  Image blurred2x2;
  Image sharpened2x2;
  Image image2x3;
  Image blurred2x3;
  Kernel blur;
  Image monochrome2x2;
  Image monochrome2x3;
  Image sepia2x2;
  Image sepia2x3;

  Kernel monochrome;
  Kernel sepia;


  @Before
  public void setUp() {

    blur = Kernel.BLUR_KERNEL;

    twoXTwo = new ArrayList<>();
    twoXTwo.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(16, 64, 128, new Position2D(0, 1)))));
    twoXTwo.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(80, 0, 80, new Position2D(1, 0)),
        new Pixel(0, 0, 240, new Position2D(1, 1)))));
    image2x2 = new Image(twoXTwo, 255);

    twoXTwoBlurred = new ArrayList<>();
    twoXTwoBlurred
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(12, 8, 41, new Position2D(0, 0)),
            new Pixel(9, 16, 67, new Position2D(0, 1)))));
    twoXTwoBlurred
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(21, 4, 58, new Position2D(1, 0)),
            new Pixel(12, 8, 86, new Position2D(1, 1)))));

    blurred2x2 = new Image(twoXTwoBlurred, 255);

    twoXThree = new ArrayList<>();
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

    twoXThreeBlurred = new ArrayList<>();
    twoXThreeBlurred.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(8, 8, 4, new Position2D(0, 0)),
        new Pixel(16, 4, 8, new Position2D(0, 1)))));
    twoXThreeBlurred.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(6, 17, 8, new Position2D(1, 0)),
        new Pixel(9, 10, 16, new Position2D(1, 1)))));
    twoXThreeBlurred.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(4, 10, 4, new Position2D(2, 0)),
        new Pixel(2, 8, 8, new Position2D(2, 1)))));

    blurred2x3 = new Image(twoXThreeBlurred, 255);

    twoXTwoSharpened = new ArrayList<>();
    twoXTwoSharpened.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(24, 16, 112, new Position2D(0, 0)),
        new Pixel(36, 64, 208, new Position2D(0, 1)))));
    twoXTwoSharpened.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(
            84, 16, 172, new Position2D(1, 0)),
        new Pixel(24, 16, 292, new Position2D(1, 1)))));
    sharpened2x2 = new Image(twoXTwoSharpened, 255);

    monochrome = new Kernel(new ArrayList<>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722)),
        new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722)),
        new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722)))));

    sepia = new Kernel(new ArrayList<>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(0.393, 0.769, 0.189)),
        new ArrayList<Double>(Arrays.asList(0.349, 0.686, 0.168)),
        new ArrayList<Double>(Arrays.asList(0.272, 0.534, 0.131)))));

    twoXTwoMonochrome = new ArrayList<>();
    twoXTwoMonochrome.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(57, 57, 57, new Position2D(0, 1)))));
    twoXTwoMonochrome.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(22, 22, 22, new Position2D(1, 0)),
        new Pixel(17, 17, 17, new Position2D(1, 1)))));

    monochrome2x2 = new Image(twoXTwoMonochrome, 255);

    twoXThreeMonochrome = new ArrayList<>();
    twoXThreeMonochrome.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(13, 13, 13, new Position2D(0, 1)))));
    twoXThreeMonochrome.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(45, 45, 45, new Position2D(1, 0)),
        new Pixel(4, 4, 4, new Position2D(1, 1)))));
    twoXThreeMonochrome.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(3, 3, 3, new Position2D(2, 0)),
        new Pixel(11, 11, 11, new Position2D(2, 1)))));

    monochrome2x3 = new Image(twoXThreeMonochrome, 255);

    twoXTwoSepia = new ArrayList<>();
    twoXTwoSepia.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(79, 69, 54, new Position2D(0, 1)))));
    twoXTwoSepia.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(46, 40, 31, new Position2D(1, 0)),
        new Pixel(45, 40, 31, new Position2D(1, 1)))));
    sepia2x2 = new Image(twoXTwoSepia, 255);

    twoXThreeSepia = new ArrayList<>();
    twoXThreeSepia.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(25, 22, 17, new Position2D(0, 1)))));
    twoXThreeSepia.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(49, 43, 34, new Position2D(1, 0)),
        new Pixel(12, 10, 8, new Position2D(1, 1)))));
    twoXThreeSepia.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(6, 5, 4, new Position2D(2, 0)),
        new Pixel(12, 10, 8, new Position2D(2, 1)))));
    sepia2x3 = new Image(twoXThreeSepia, 255);


  }


  // testing that filter properly filters the entire image with given kernel 2by2 image
  @Test
  public void testFilterWithBlur2By2() {
    image2x2.filter(blur);

    // checking all pixels were filtered by blur kernel by looping
    for (int i = 0; i < image2x2.getHeight(); i++) {
      for (int j = 0; j < image2x2.getWidth(); j++) {
        assertEquals(blurred2x2.getPixelAt(i, j).toString(), image2x2.getPixelAt(i, j).toString());
      }
    }
  }

  // testing filter properly filters the entire image with given kernel 2by3 image
  @Test
  public void testFilterWithBlur2By3() {
    image2x3.filter(blur);
    // checking all pixels were filtered by blur kernel by looping
    for (int i = 0; i < image2x3.getHeight(); i++) {
      for (int j = 0; j < image2x3.getWidth(); j++) {
        assertEquals(blurred2x3.getPixelAt(i, j).toString(),
            image2x3.getPixelAt(i, j).toString());
      }
    }
  }

  // testing filter properly filters the entire image with given 5 by 5 sharpen kernel
  @Test
  public void testFilterWithSharpen2By2() {

    image2x2.filter(Kernel.SHARPEN_KERNEL);

    // checking all pixels were filtered by blur kernel by looping
    for (int i = 0; i < image2x2.getHeight(); i++) {
      for (int j = 0; j < image2x2.getWidth(); j++) {
        assertEquals(sharpened2x2.getPixelAt(i, j).toString(),
            image2x2.getPixelAt(i, j).toString());
      }
    }

  }

  // round down channel values
  @Test
  public void testMonochrome2x2() {
    image2x2.colorTransform(monochrome);
    for (int i = 0; i < image2x2.getHeight(); i++) {
      for (int j = 0; j < image2x2.getWidth(); j++) {
        assertEquals(monochrome2x2.getPixelAt(i, j).toString(),
            image2x2.getPixelAt(i, j).toString());
      }
    }
  }

  // round down channel values
  @Test
  public void testMonochrome2x3() {
    image2x3.colorTransform(monochrome);
    for (int i = 0; i < image2x3.getHeight(); i++) {
      for (int j = 0; j < image2x3.getWidth(); j++) {
        assertEquals(monochrome2x3.getPixelAt(i, j).toString(),
            image2x3.getPixelAt(i, j).toString());
      }
    }
  }

  @Test
  public void testSepia2x2() {
    image2x2.colorTransform(sepia);
    for (int i = 0; i < image2x2.getHeight(); i++) {
      for (int j = 0; j < image2x2.getWidth(); j++) {
        assertEquals(sepia2x2.getPixelAt(i, j).toString(), image2x2.getPixelAt(i, j).toString());
      }
    }
  }

  @Test
  public void testSepia2x3() {
    image2x3.colorTransform(sepia);
    for (int i = 0; i < image2x3.getHeight(); i++) {
      for (int j = 0; j < image2x3.getWidth(); j++) {
        assertEquals(sepia2x3.getPixelAt(i, j).toString(), image2x3.getPixelAt(i, j).toString());
      }
    }
  }

  @Test
  public void testMosaic() throws IOException {
    Image mosaicCheckerBoard = new Checkerboard(4, Color.CYAN, Color.DARK_GRAY).create();
    mosaicCheckerBoard.mosaic(10, 2);

    StringBuilder actualPixels = new StringBuilder();
    String expected = "42 127 127 0 0\n"
        + "42 127 127 0 1\n"
        + "0 255 255 0 2\n"
        + "64 64 64 0 3\n"
        + "42 127 127 1 0\n"
        + "21 191 191 1 1\n"
        + "32 159 159 1 2\n"
        + "32 159 159 1 3\n"
        + "0 255 255 2 0\n"
        + "21 191 191 2 1\n"
        + "21 191 191 2 2\n"
        + "32 159 159 2 3\n"
        + "64 64 64 3 0\n"
        + "0 255 255 3 1\n"
        + "64 64 64 3 2\n"
        + "32 159 159 3 3\n";

    for (int i = 0; i < mosaicCheckerBoard.getHeight(); i++) {
      for (int j = 0; j < mosaicCheckerBoard.getWidth(); j++) {
        actualPixels.append(mosaicCheckerBoard.getPixelAt(i, j).toString() + "\n");
      }
    }

    assertEquals(expected, actualPixels.toString());

  }

}