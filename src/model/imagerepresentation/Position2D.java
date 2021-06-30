package model.imagerepresentation;


import java.util.Objects;

/**
 * Represents the position in a 2D plane, including a 2D array where the x and y would represent the
 * row and column, respectively.
 */
public final class Position2D {

  private final int x;
  private final int y;

  /**
   * Initialize this object to the specified position.
   */
  public Position2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Copy constructor.
   */
  public Position2D(Position2D v) {
    this(v.x, v.y);
  }

  /**
   * Gets the x related value for this position.
   *
   * @return the x value for this position.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y related value for this position.
   *
   * @return the y value for this position.
   */
  public int getY() {
    return y;
  }

  /**
   * Determines the absolute cartesian distance between this position and that position.
   *
   * @param that the other position
   * @return the absolute distance
   */
  public double findDistanceTo(Position2D that) {
    if (that == null) {
      throw new IllegalArgumentException("Null argument");
    }
    if (this.equals(that)) {
      return 0;
    }
    return Math.sqrt(Math.pow((that.x - this.x), 2) + Math.pow((that.y - this.y), 2));

  }


  @Override
  public String toString() {
    return "" + this.x + " " + this.y;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01) && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}

