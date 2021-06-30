import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.imagerepresentation.Kernel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the tests and examples for the Kernel invariant class.
 */
public class KernelTest {

  private final List<List<Double>> evenRow;
  private final List<List<Double>> evenCol;
  private final List<List<Double>> empty;
  private final List<List<Double>> nonSquare;
  private final List<List<Double>> jagged;
  private final List<List<Double>> nullCols;
  private List<List<Double>> validMat;


  /**
   * Initializes the examples for this class.
   */
  public KernelTest() {

    evenRow = new ArrayList<List<Double>>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0))));

    evenCol = new ArrayList<>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0))));

    empty = new ArrayList<List<Double>>();

    nonSquare = new ArrayList<>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0))));

    jagged = new ArrayList<>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(1.0)),
        new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)),
        new ArrayList<Double>(Arrays.asList(1.0))));

    nullCols = new ArrayList<>((Arrays.asList(null, null, null)));

  }

  /**
   * Initializes the examples that are mutated.
   */
  @Before
  public void setUp() {
    validMat = new ArrayList<>(Arrays.asList(
        new ArrayList<Double>(Arrays.asList(0.0625, 0.125, 0.0625)),
        new ArrayList<Double>(Arrays.asList(0.125, 0.25, 0.125)),
        new ArrayList<Double>(Arrays.asList(0.0625, 0.125, 0.0625))));

  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullCheck() {
    new Kernel(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmpty() {
    new Kernel(empty);
  }

  // test odd row size only allowed
  @Test(expected = IllegalArgumentException.class)
  public void testEvenRow() {
    new Kernel(evenRow);
  }

  // test odd col size only allowed
  @Test(expected = IllegalArgumentException.class)
  public void testEvenColumn() {
    new Kernel(evenCol);
  }


  // test rows are the same size
  @Test(expected = IllegalArgumentException.class)
  public void testJaggedMatrix() {
    new Kernel(jagged);
  }

  // test rows and cols are same size
  @Test(expected = IllegalArgumentException.class)
  public void testDiffRowColSize() {
    new Kernel(nonSquare);
  }

  // test null columns
  @Test(expected = IllegalArgumentException.class)
  public void testNullColumns() {
    new Kernel(nullCols);
  }


  // test getMatrix returns a deep copy of the matrix
  @Test
  public void getMatrix() {
    Kernel k = new Kernel(validMat);

    List<List<Double>> objectMat = k.getMatrix();

    // checking contents are the same
    for (int i = 0; i < validMat.size(); i++) {
      assertEquals(validMat.get(i), objectMat.get(i));
    }

    // checking deep copy
    objectMat.remove(2);
    assertEquals(3, validMat.size());
    assertEquals(3, k.size());
  }

  @Test
  public void getCenter() {
    assertEquals(1, new Kernel(validMat).getCenter());
  }

  @Test
  public void size() {
    assertEquals(3, new Kernel(validMat).size());
  }
}