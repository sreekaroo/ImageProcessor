import static org.junit.Assert.assertEquals;

import model.imagerepresentation.Layer;
import org.junit.Before;
import org.junit.Test;

/**
 * tests for Layer class.
 */
public class LayerTest {

  Layer layer1;
  Layer layer2;
  Layer layer3;
  Layer layer4;


  @Before
  public void setUp() {
    layer1 = new Layer("Koala.ppm");
    layer2 = new Layer("Birds.ppm");
    layer3 = new Layer("KoalaSepia.ppm");
    layer4 = new Layer("KoalaMonochrome.ppm");

  }


  @Test
  public void testIsVisible() {
    assertEquals(true, layer1.isVisible());
    assertEquals(true, layer2.isVisible());

  }

  @Test
  public void testToggleVisibility() {
    layer1.toggleVisibility();
    layer2.toggleVisibility();

    assertEquals(false, layer1.isVisible());
    assertEquals(false, layer2.isVisible());


  }

  @Test
  public void testGetName() {

    assertEquals("Koala.ppm", layer1.getName());
    assertEquals("Birds.ppm", layer2.getName());
    assertEquals("KoalaSepia.ppm", layer3.getName());
    assertEquals("KoalaMonochrome.ppm", layer4.getName());

  }


}
