package model.imagerepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * represents a Kernel in a filtering operation.
 */
public class Kernel {

  private List<List<Double>> matrix;
  // INVARIANT: rows and column sizes are the same.
  // INVARIANT: rows and column sizes are odd.

  // CONSTANTS:
  public static final Kernel BLUR_KERNEL = new Kernel(new ArrayList<>(Arrays.asList(
      new ArrayList<Double>(Arrays.asList(0.0625, 0.125, 0.0625)),
      new ArrayList<Double>(Arrays.asList(0.125, 0.25, 0.125)),
      new ArrayList<Double>(Arrays.asList(0.0625, 0.125, 0.0625)))));

  public static final Kernel SHARPEN_KERNEL = new Kernel(new ArrayList<>(Arrays.asList(
      new ArrayList<Double>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)),
      new ArrayList<Double>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)),
      new ArrayList<Double>(Arrays.asList(-0.125, 0.25, 1.0, 0.25, -0.125)),
      new ArrayList<Double>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)),
      new ArrayList<Double>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)))));

  public static final Kernel GREYSCALE_KERNEL = new Kernel(new ArrayList<>(Arrays.asList(
      new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722)),
      new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722)),
      new ArrayList<Double>(Arrays.asList(0.2126, 0.7152, 0.0722)))));

  public static final Kernel SEPIA_KERNEL = new Kernel(new ArrayList<>(Arrays.asList(
      new ArrayList<Double>(Arrays.asList(0.393, 0.769, 0.189)),
      new ArrayList<Double>(Arrays.asList(0.349, 0.686, 0.168)),
      new ArrayList<Double>(Arrays.asList(0.272, 0.534, 0.131)))));

  /**
   * constructor for Kernel class. Maintains the invariants of the matrix.
   *
   * @param matrix a 2D matrix of doubles.
   * @throws IllegalArgumentException if matrix dimensions are not odd or the same or invalid.
   */
  public Kernel(List<List<Double>> matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null");
    }
    if (matrix.size() % 2 != 1) {
      throw new IllegalArgumentException("Matrix must be odd size");
    }
    for (int i = 0; i < matrix.size(); i++) {
      if (matrix.get(i) == null) {
        throw new IllegalArgumentException("List cannot be null");
      }
      if (matrix.get(i).size() != matrix.size()) {
        throw new IllegalArgumentException("Matrix columns and rows must be same size");
      }
    }
    this.matrix = matrix;
  }

  /**
   * Gets a copy of the matrix.
   *
   * @return a copy of the matrix.
   */
  public List<List<Double>> getMatrix() {
    List<List<Double>> acc = new ArrayList<>();
    List<Double> currRow = new ArrayList<>();
    for (int i = 0; i < matrix.size(); i++) {
      currRow = new ArrayList<>();
      for (int j = 0; j < matrix.size(); j++) {
        Double currentInt = matrix.get(i).get(j);
        currRow.add(currentInt);
      }
      acc.add(currRow);
    }

    return acc;
  }


  /**
   * Gets the center of the matrix.
   *
   * @return the center index of the matrix.
   */
  public int getCenter() {
    return this.matrix.size() / 2;
  }

  /**
   * gets the size of the matrix.
   *
   * @return the size of the matrix.
   */
  public int size() {
    return this.matrix.size();
  }


}
