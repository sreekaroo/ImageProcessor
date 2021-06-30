import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.ImageProcessingModel;
import model.SimpleImageProcessingModel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Kernel;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;
import model.programmaticimage.Checkerboard;
import model.programmaticimage.ProgrammaticImage;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the tests and examples for the model.
 */
public class SimpleImageProcessingModelTest {

  // models
  ImageProcessingModel model1;
  ImageProcessingModel model2;
  ImageProcessingModel modelKoala;

  // images
  Image image2x2;
  Image unclamped2x2;
  Image image2x3;
  Image image2x3New;


  ProgrammaticImage checkerBoard1b1;
  ProgrammaticImage checkerBoard3b3;


  @Before
  public void setUp() throws IOException {
    model1 = new SimpleImageProcessingModel();
    model2 = new SimpleImageProcessingModel();

    modelKoala = new SimpleImageProcessingModel();

    List<List<Pixel>> twoXTwo = new ArrayList<>();
    twoXTwo.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(16, 64, 128, new Position2D(0, 1)))));
    twoXTwo.add(new ArrayList<Pixel>(Arrays.asList(new Pixel(80, 0, 80, new Position2D(1, 0)),
        new Pixel(0, 0, 240, new Position2D(1, 1)))));
    image2x2 = new Image(twoXTwo, 255);

    List<List<Pixel>> unclampedPixels;
    unclampedPixels = new ArrayList<>();
    unclampedPixels
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(1000, 1000, 1000, new Position2D(0, 0)),
            new Pixel(1000, 1000, 1000, new Position2D(1000, 1000)))));
    unclampedPixels
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(1000, 1000, 1000, new Position2D(1, 0)),
            new Pixel(1000, 1000, 1000, new Position2D(1, 1)))));
    unclamped2x2 = new Image(unclampedPixels, 255);

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

    List<List<Pixel>> twoXThree1 = new ArrayList<>();
    twoXThree1.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 0, new Position2D(0, 0)),
        new Pixel(0, 0, 0, new Position2D(0, 1)))));
    twoXThree1.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(200, 0, 0, new Position2D(1, 0)),
        new Pixel(0, 0, 0, new Position2D(1, 1)))));
    twoXThree1.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(200, 0, 0, new Position2D(2, 0)),
        new Pixel(0, 0, 0, new Position2D(2, 1)))));

    image2x3New = new Image(twoXThree1, 255);

    checkerBoard1b1 = new Checkerboard(1, Color.BLACK, Color.RED);
    checkerBoard3b3 = new Checkerboard(3, Color.BLACK, Color.RED);

  }

  // testing model properly uploads an image
  @Test
  public void testUpload() throws IOException {

    Image boardImg = checkerBoard3b3.create();
    model1.upload(boardImg);

    // testing same data
    for (int i = 0; i < boardImg.getHeight(); i++) {
      for (int j = 0; j < boardImg.getWidth(); j++) {
        assertEquals(boardImg.getPixelAt(i, j).toString(), model1.getPixelAt(i, j).toString());
      }
    }

  }

  //  // testing upload properly converts file into image representation
  //  @Test
  //  public void testUpload() throws IOException {
  //    Image boardImg = checkerBoard3b3.create();
  //
  //    // generating checkerboard ppm file
  //    ImageFileInterpreter<Image> interpreter = new PPMInterpreter();
  //    interpreter.export("CheckerBoard3by3.ppm", boardImg);
  //
  //    model1.uploadFile("CheckerBoard3by3.ppm");
  //
  //    // testing uploaded checkerboard file is same
  //    for (int i = 0; i < boardImg.getHeight(); i++) {
  //      for (int j = 0; j < boardImg.getWidth(); j++) {
  //        assertEquals(boardImg.getPixelAt(i, j), model1.getPixelAt(i, j));
  //      }
  //    }
  //  }
  //
  //  // testing exports proper ppm image data to ppm file
  //  @Test
  //  public void testExport() throws IOException {
  //
  //    Image boardImg = checkerBoard3b3.create();
  //
  //    model1.upload(boardImg);
  //    model1.exportAs("ExportCheckerBoard.ppm", FileType.PPM);
  //
  //    File exportedFile = new File("ExportCheckerBoard.ppm");
  //    Scanner scan = new Scanner(exportedFile);
  //
  //    assertEquals("P3", scan.next());
  //    assertEquals(3, scan.nextInt());
  //    assertEquals(3, scan.nextInt());
  //    assertEquals(255, scan.nextInt());
  //
  //    for (int i = 0; i < 3; i++) {
  //      for (int j = 0; j < 3; j++) {
  //        Pixel currPixel = boardImg.getPixelAt(i, j);
  //        assertEquals(currPixel.getChannel(Channel.RED), scan.nextInt());
  //        assertEquals(currPixel.getChannel(Channel.GREEN), scan.nextInt());
  //        assertEquals(currPixel.getChannel(Channel.BLUE), scan.nextInt());
  //      }
  //    }
  // }

  // testing model properly exports image data as a copy
  @Test
  public void testExport() {

    Image boardImg = checkerBoard3b3.create();
    model1.upload(boardImg);

    Image actual = model1.export();

    // testing copy not same reference
    assertNotEquals(boardImg, actual);

    // testing same data
    for (int i = 0; i < boardImg.getHeight(); i++) {
      for (int j = 0; j < boardImg.getWidth(); j++) {
        assertEquals(boardImg.getPixelAt(i, j).toString(), actual.getPixelAt(i, j).toString());
      }
    }

  }


  // testing a valid blur for a 2by2 image without clamping
  @Test
  public void testBlurValidNoClamp2by2() throws IOException {
    model1.upload(image2x2);
    Image copy = new Image(image2x2);

    model1.blur();
    copy.filter(Kernel.BLUR_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }

  // testing a valid blur for a 2by3 image without clamping
  @Test
  public void testBlurValidNoClamp2by3() throws IOException {
    model1.upload(image2x3);
    Image copy = new Image(image2x3);

    model1.blur();
    copy.filter(Kernel.BLUR_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }


  // testing blur() clamps after filtering
  @Test
  public void testBlurClampsForMaxValueOf255() {
    Image clampedTop;

    List<List<Pixel>> clampedPixels;
    clampedPixels = new ArrayList<>();
    clampedPixels
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(255, 255, 255, new Position2D(0, 0)),
            new Pixel(255, 255, 255, new Position2D(0, 1)))));
    clampedPixels
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(255, 255, 255, new Position2D(1, 0)),
            new Pixel(255, 255, 255, new Position2D(1, 1)))));
    clampedTop = new Image(clampedPixels, 255);

    model1.upload(unclamped2x2);
    model1.blur();

    for (int i = 0; i < model1.getHeight(); i++) {
      for (int j = 0; j < model1.getWidth(); j++) {
        assertEquals(clampedTop.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }


  }


  // test sharpen() properly filters entire image with sharpen kernel
  @Test
  public void testSharpenValidNoClamp2by3() throws IOException {
    model1.upload(image2x3New);
    Image copy = new Image(image2x3New);

    model1.sharpen();
    copy.filter(Kernel.SHARPEN_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }


  // test sharpen() properly clamps after filtering
  @Test
  public void testSharpenClampsForMaxValueOf255() {
    Image clampedTop;
    List<List<Pixel>> clampedPixels;
    clampedPixels = new ArrayList<>();
    clampedPixels
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(255, 255, 255, new Position2D(0, 0)),
            new Pixel(255, 255, 255, new Position2D(0, 1)))));
    clampedPixels
        .add(new ArrayList<Pixel>(Arrays.asList(new Pixel(255, 255, 255, new Position2D(1, 0)),
            new Pixel(255, 255, 255, new Position2D(1, 1)))));
    clampedTop = new Image(clampedPixels, 255);
    model1.upload(unclamped2x2);
    model1.sharpen();
    for (int i = 0; i < model1.getHeight(); i++) {
      for (int j = 0; j < model1.getWidth(); j++) {
        assertEquals(clampedTop.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }


  @Test
  public void testMonochrome2by2() {
    model1.upload(image2x2);
    Image copy = new Image(image2x2);

    model1.monochrome();
    copy.colorTransform(Kernel.GREYSCALE_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }

  }

  @Test
  public void testMonochrome2by3() {
    model1.upload(image2x3);
    Image copy = new Image(image2x3);

    model1.monochrome();
    copy.colorTransform(Kernel.GREYSCALE_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }
  }


  @Test
  public void testSepia2by2() {
    model1.upload(image2x2);
    Image copy = new Image(image2x2);

    model1.sepia();
    copy.colorTransform(Kernel.SEPIA_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }

  }

  @Test
  public void testSepia2by3() {
    model1.upload(image2x3);
    Image copy = new Image(image2x3);

    model1.sepia();
    copy.colorTransform(Kernel.SEPIA_KERNEL);

    for (int i = 0; i < copy.getHeight(); i++) {
      for (int j = 0; j < copy.getWidth(); j++) {
        assertEquals(copy.getPixelAt(i, j), model1.getPixelAt(i, j));
      }
    }

  }

  // testing creating a checkerboard programmatic image
  @Test
  public void testCreateCheckerBoard() {

    Image actual1b1 = model1.createImage(checkerBoard1b1);
    Image actual3b3 = model1.createImage(checkerBoard3b3);

    assertEquals(checkerBoard1b1.create().getPixelAt(0, 0), actual1b1.getPixelAt(0, 0));

    for (int i = 0; i < actual3b3.getHeight(); i++) {
      for (int j = 0; j < actual3b3.getWidth(); j++) {
        assertEquals(checkerBoard3b3.create().getPixelAt(i, j), actual3b3.getPixelAt(i, j));
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testNoImageMonochrome() {
    model1.monochrome();
    model2.monochrome();
  }


  @Test(expected = IllegalStateException.class)
  public void testNoImageSepia() {
    model1.sepia();
    model2.sepia();
  }


  @Test
  public void testGetPixelAt() {

    model1.upload(image2x2);
    assertEquals(new Pixel(0, 0, 0, new Position2D(0, 0)), model1.getPixelAt(0, 0));
    assertEquals(new Pixel(16, 64, 128, new Position2D(0, 1)), model1.getPixelAt(0, 1));
    assertEquals(new Pixel(80, 0, 80, new Position2D(1, 0)), model1.getPixelAt(1, 0));
    assertEquals(new Pixel(0, 0, 240, new Position2D(1, 1)), model1.getPixelAt(1, 1));

  }

  @Test(expected = IllegalStateException.class)
  public void noImageFoundGetPixelAt() {
    model1.getPixelAt(0, 0);
    model1.getPixelAt(0, 1);
    model1.getPixelAt(1, 0);
    model1.getPixelAt(1, 1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelAtError() {
    model1.upload(image2x2);
    model2.upload(image2x3);
    model1.getPixelAt(0, -1);
    model1.getPixelAt(-20, 0);
    model2.getPixelAt(-35, 100);
    model2.getPixelAt(20, -4);

  }


  @Test
  public void getWidth() {
    model1.upload(image2x2);
    model2.upload(image2x3);
    assertEquals(2, model1.getWidth());
    assertEquals(2, model2.getWidth());

  }

  @Test(expected = IllegalStateException.class)
  public void noImageFoundGetWidth() {

    model1.getWidth();
    model2.getWidth();
  }


  @Test
  public void getHeight() {
    model1.upload(image2x2);
    model2.upload(image2x3);
    assertEquals(2, model1.getHeight());
    assertEquals(3, model2.getHeight());

  }

  @Test(expected = IllegalStateException.class)
  public void noImageFoundGetHeight() {

    model1.getHeight();
    model2.getHeight();

  }


}