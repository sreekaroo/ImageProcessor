import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;
import model.programmaticimage.Checkerboard;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests and examples for checkerboard.
 */
public class CheckerboardTest {

  Checkerboard checkerboard2b2;
  Checkerboard checkerboard3b3;
  List<List<Pixel>> twoByTwoPixels;
  List<List<Pixel>> threeByThreePixels;
  Image twoByTwo;
  Image threeByThree;

  @Before
  public void setUp() {
    checkerboard2b2 = new Checkerboard(2, Color.RED, Color.BLUE);
    checkerboard3b3 = new Checkerboard(3, Color.GREEN, Color.RED);

    twoByTwoPixels = new ArrayList<>();
    twoByTwoPixels.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(255, 0, 0, new Position2D(0, 0)),
        new Pixel(0, 0, 255, new Position2D(0, 1)))));
    twoByTwoPixels.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 0, 255, new Position2D(1, 0)),
        new Pixel(255, 0, 0, new Position2D(1, 1)))));
    twoByTwo = new Image(twoByTwoPixels, 255);

    threeByThreePixels = new ArrayList<>();
    threeByThreePixels.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 255, 0, new Position2D(0, 0)),
        new Pixel(255, 0, 0, new Position2D(0, 1)),
        new Pixel(0, 255, 0, new Position2D(0, 2)))));
    threeByThreePixels.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(255, 0, 0, new Position2D(1, 0)),
        new Pixel(0, 255, 0, new Position2D(1, 1)),
        new Pixel(255, 0, 0, new Position2D(1, 2)))));
    threeByThreePixels.add(new ArrayList<Pixel>(Arrays.asList(
        new Pixel(0, 255, 0, new Position2D(2, 0)),
        new Pixel(255, 0, 0, new Position2D(2, 1)),
        new Pixel(0, 255, 0, new Position2D(2, 2)))));
    threeByThree = new Image(threeByThreePixels, 255);

  }

  // testing invariant of num tiles being positive
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvariant() {
    new Checkerboard(-1, Color.BLACK, Color.BLUE);
  }

  // testing constructor null check
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullCheck1() {
    new Checkerboard(1, null, Color.BLUE);
  }

  // testing constructor null check
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullCheck2() {
    new Checkerboard(1, Color.BLUE, null);
  }


  // testing checkerboard creates two by two checker board
  @Test
  public void testCreate2b2() {

    Image actual = checkerboard2b2.create();

    assertEquals(2, actual.getHeight());
    assertEquals(2, actual.getWidth());

    for (int i = 0; i < twoByTwo.getHeight(); i++) {
      for (int j = 0; j < twoByTwo.getWidth(); j++) {
        assertEquals(twoByTwo.getPixelAt(i, j).toString(), actual.getPixelAt(i, j).toString());
      }
    }
  }


  @Test
  public void testCreate3b3() {
    Image actual = checkerboard3b3.create();

    for (int i = 0; i < threeByThree.getHeight(); i++) {
      for (int j = 0; j < threeByThree.getWidth(); j++) {
        assertEquals(threeByThree.getPixelAt(i, j).toString(), actual.getPixelAt(i, j).toString());
      }
    }

  }
}