package model.programmaticimage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;

/**
 * to represent a checkerboard type of programmatic image.
 */
public class Checkerboard implements ProgrammaticImage {

  private final int numTiles;
  // INVARIANT: positive number
  private final Color color1;
  private final Color color2;

  /**
   * constructor for checkerboard.
   *
   * @param numTiles the number of tiles in one side of a checkerboard.
   * @param color1   first color.
   * @param color2   second color.
   */
  public Checkerboard(int numTiles, Color color1, Color color2) {

    if (numTiles <= 0) {
      throw new IllegalArgumentException("Number of tiles must be a positive number");
    }

    if (color1 == null || color2 == null) {
      throw new IllegalArgumentException("Null color");
    }
    this.numTiles = numTiles;
    this.color1 = color1;
    this.color2 = color2;
  }


  @Override
  public Image create() {
    List<List<Pixel>> acc = new ArrayList<>();
    List<Pixel> currRow = new ArrayList<>();

    boolean rowFlag = true;
    boolean columnFlag = true;

    for (int i = 0; i < numTiles; i++) {
      columnFlag = rowFlag;

      currRow = new ArrayList<>();
      for (int j = 0; j < numTiles; j++) {
        if (columnFlag) {
          currRow.add(new Pixel(color1, new Position2D(i, j)));
        } else {
          currRow.add(new Pixel(color2, new Position2D(i, j)));
        }
        columnFlag = !columnFlag;
      }
      rowFlag = !rowFlag;
      acc.add(currRow);

    }
    return new Image(acc);
  }
}
