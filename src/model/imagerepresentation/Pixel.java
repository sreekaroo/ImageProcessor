package model.imagerepresentation;


import java.awt.Color;
import java.util.Objects;

/**
 * represents a Pixel.
 */
public class Pixel {

  private int r;
  private int g;
  private int b;
  private final Position2D position;

  /**
   * Main Constructor of the pixel class.
   *
   * @param r        the red channel value
   * @param g        the green channel value
   * @param b        the blue channel value
   * @param position the position in an in image of this pixel.
   */
  public Pixel(int r, int g, int b, Position2D position) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.position = position;
  }


  /**
   * Convenience cosntrucotr for constructing pixel with Java's color class.
   *
   * @param color    the color of this pixel
   * @param position the position of this pixel in the image.
   */
  public Pixel(Color color, Position2D position) {
    this.r = color.getRed();
    this.g = color.getGreen();
    this.b = color.getBlue();
    this.position = position;
  }

  /**
   * Copy Constructor.
   *
   * @param p the pixel to be replicated and constructed.
   */
  public Pixel(Pixel p) {
    this.r = p.r;
    this.g = p.g;
    this.b = p.b;
    this.position = p.getPosition();
  }


  /**
   * Returns a copy of the position of the pixel.
   *
   * @return the position of the pixel.
   */
  public Position2D getPosition() {
    return new Position2D(position);
  }


  /**
   * Returns the value in the channel specified.
   *
   * @param channel the specified color channel
   * @return the value of the channel
   */
  public int getChannel(Channel channel) {

    switch (channel) {
      case RED:
        return this.r;
      case GREEN:
        return this.g;
      case BLUE:
        return this.b;
      default:
        throw new IllegalArgumentException("Invalid channel.");
    }
  }

  /**
   * Sets the value to the given value according to the channel.
   *
   * @param value   the value to be changed to
   * @param channel the channel to change
   */
  public void setChannel(int value, Channel channel) {

    switch (channel) {
      case RED:
        this.r = value;
        break;
      case GREEN:
        this.g = value;
        break;
      case BLUE:
        this.b = value;
        break;
      default:
        throw new IllegalArgumentException("not a valid color");

    }
  }

  /**
   * clamps this pixel to the specified minimum and maximum.
   *
   * @param min minimum value
   * @param max maximum value
   */
  public void clamp(int min, int max) {
    int currChannelVal = 0;

    for (Channel c : Channel.values()) {
      currChannelVal = this.getChannel(c);
      if (currChannelVal < min) {
        this.setChannel(min, c);
      }
      if (currChannelVal > max) {
        this.setChannel(max, c);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel that = (Pixel) o;

    return this.r == that.r && this.g == that.g && this.b == that.b && this.position
        .equals(that.position);

  }

  @Override
  public int hashCode() {
    return Objects.hash(this.r, this.g, this.b, this.position.hashCode());
  }

  @Override
  public String toString() {
    return "" + this.r + " " + this.g + " " + this.b + " " + this.position.toString();
  }
}



