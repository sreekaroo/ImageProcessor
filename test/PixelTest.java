import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import model.imagerepresentation.Channel;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;
import org.junit.Test;

/**
 * Tests for Pixel class.
 */
public class PixelTest {

  Pixel pixel1 = new Pixel(Color.red, new Position2D(2, 3));
  Pixel pixel2 = new Pixel(Color.green, new Position2D(10, 25));
  Pixel pixel3 = new Pixel(Color.blue, new Position2D(44, 54));
  Pixel pixel4 = new Pixel(255, 192, 203, new Position2D(10, 4));
  Pixel pixel5 = new Pixel(128, 0, 128, new Position2D(87, 23));
  Pixel pixel6 = new Pixel(256, 0, 400, new Position2D(35, 0));
  Pixel pixel7 = new Pixel(-100, 0, 2000, new Position2D(45, 100));

  Pixel pixel8 = new Pixel(255, 192, 203, new Position2D(10, 4));
  Pixel pixel9 = new Pixel(128, 0, 128, new Position2D(87, 23));
  Pixel pixel10 = new Pixel(256, 0, 400, new Position2D(35, 0));
  Pixel pixel11 = new Pixel(-100, 0, 2000, new Position2D(45, 100));


  @Test
  public void testGetPosition() {
    assertEquals(new Position2D(2, 3), pixel1.getPosition());
    assertEquals(new Position2D(10, 25), pixel2.getPosition());
    assertEquals(new Position2D(44, 54), pixel3.getPosition());
    assertEquals(new Position2D(10, 4), pixel4.getPosition());
    assertEquals(new Position2D(87, 23), pixel5.getPosition());
  }

  @Test
  public void testGetChannel() {
    assertEquals(255, pixel1.getChannel(Channel.RED));
    assertEquals(255, pixel2.getChannel(Channel.GREEN));
    assertEquals(255, pixel3.getChannel(Channel.BLUE));
    assertEquals(203, pixel4.getChannel(Channel.BLUE));
    assertEquals(192, pixel4.getChannel(Channel.GREEN));
    assertEquals(0, pixel5.getChannel(Channel.GREEN));
  }

  @Test
  public void testSetChannel() {
    pixel1.setChannel(125, Channel.RED);
    assertEquals(125, pixel1.getChannel(Channel.RED));

    pixel2.setChannel(90, Channel.BLUE);
    assertEquals(90, pixel2.getChannel(Channel.BLUE));

    pixel3.setChannel(78, Channel.GREEN);
    assertEquals(78, pixel3.getChannel(Channel.GREEN));

    pixel4.setChannel(220, Channel.RED);
    assertEquals(220, pixel4.getChannel(Channel.RED));

    pixel5.setChannel(50, Channel.BLUE);
    assertEquals(50, pixel5.getChannel(Channel.BLUE));
  }

  @Test
  public void clamp() {
    pixel1.clamp(0, 250);
    assertEquals(250, pixel1.getChannel(Channel.RED));
    assertEquals(0, pixel1.getChannel(Channel.BLUE));
    assertEquals(0, pixel1.getChannel(Channel.GREEN));

    pixel2.clamp(50, 100);
    assertEquals(100, pixel2.getChannel(Channel.GREEN));
    assertEquals(50, pixel2.getChannel(Channel.RED));
    assertEquals(50, pixel2.getChannel(Channel.BLUE));

    pixel6.clamp(0, 255);
    assertEquals(255, pixel6.getChannel(Channel.RED));
    assertEquals(255, pixel6.getChannel(Channel.BLUE));
    assertEquals(0, pixel6.getChannel(Channel.GREEN));

    pixel7.clamp(0, 255);
    assertEquals(0, pixel7.getChannel(Channel.RED));
    assertEquals(0, pixel7.getChannel(Channel.GREEN));
    assertEquals(255, pixel7.getChannel(Channel.BLUE));
  }

  // testing the overridden equals method
  @Test
  public void testEquals() {
    assertNotEquals(pixel4, new Position2D(1, 1));
    assertEquals(pixel4, pixel8);
    assertEquals(pixel5, pixel9);
    assertEquals(pixel6, pixel10);
  }

  // testing the overridden toString method
  @Test
  public void testToString() {
    assertEquals("255 0 0 2 3", pixel1.toString());
    assertEquals("0 255 0 10 25", pixel2.toString());
    assertEquals("0 0 255 44 54", pixel3.toString());
    assertEquals("255 192 203 10 4", pixel4.toString());
    assertEquals("128 0 128 87 23", pixel5.toString());
    assertEquals("256 0 400 35 0", pixel6.toString());
    assertEquals("-100 0 2000 45 100", pixel7.toString());
  }

  @Test
  public void testingDistanceTo() {

    Position2D pos1 = new Position2D(0, 0);
    Position2D pos2 = new Position2D(3, 4);

    assertEquals(5, pos1.findDistanceTo(pos2), .001);
  }
}

