package model.imagerepresentation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Represents an image as a sequence of pixels.
 */
public class Image {

  private List<List<Pixel>> pixels;
  private final int maxVal;

  // INVARIANT: columns should all be the same size.
  // INVARIANT: rows should all be the same size.


  /**
   * Default constructor.
   *
   * @param pixels the pixels to be represented by this image
   */
  public Image(List<List<Pixel>> pixels) {

    if (pixels == null) {
      throw new IllegalArgumentException("Null list");
    }
    this.pixels = pixels;
    maxVal = 255;
  }

  /**
   * Main constructor that intilalzes pixels and max val.
   *
   * @param pixels the sequence of pixels for this image
   * @param maxVal the max channel value.
   */
  public Image(List<List<Pixel>> pixels, int maxVal) {
    if (pixels == null) {
      throw new IllegalArgumentException("Null list");
    }

    this.pixels = pixels;
    this.maxVal = maxVal;
  }

  /**
   * Copy constructor.
   *
   * @param image the image to be copied
   */
  public Image(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Null image");
    }

    List<List<Pixel>> acc = new ArrayList<List<Pixel>>();
    List<Pixel> currRow = new ArrayList<Pixel>();

    for (int i = 0; i < image.getHeight(); i++) {
      currRow = new ArrayList<Pixel>();

      for (int j = 0; j < image.getWidth(); j++) {
        Pixel currPixel = image.getPixelAt(i, j);
        currRow.add(
            new Pixel(currPixel.getChannel(Channel.RED), currPixel.getChannel(Channel.GREEN),
                currPixel.getChannel(Channel.BLUE),
                new Position2D(i, j)));
      }
      acc.add(currRow);
    }
    this.pixels = acc;
    this.maxVal = image.getMaxVal();
  }


  /**
   * gets a copy of the Pixel at the specified location of the image.
   *
   * @param row    the row index of the pixel.
   * @param column the column index of the pixel.
   * @return a pixel from the specified location.
   * @throws IllegalArgumentException if invalid row or column index.
   */
  public Pixel getPixelAt(int row, int column) throws IllegalArgumentException {
    try {
      return new Pixel(this.pixels.get(row).get(column));
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid row or column index for pixel.");
    }

  }

  /**
   * gets the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
    return this.pixels.get(0).size();
  }

  /**
   * gets the height of the image.
   *
   * @return the height of the image.
   */
  public int getHeight() {
    return this.pixels.size();
  }

  /**
   * Filters this entire image according to the given kernel.
   *
   * @param k the specified kernel
   */
  public void filter(Kernel k) {

    if (k == null) {
      throw new IllegalArgumentException("Null kernel");
    }

    // need a copy to prevent mutated pixels from affecting future calculations
    Image copy = new Image(this);

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        Pixel currPixel = this.pixels.get(i).get(j);
        this.filterPixel(k, currPixel, copy);
      }
    }
  }

  /**
   * Filters a specific pixel according to the channel and kernel. Mutates the given pixel with the
   * new filtered values.
   *
   * @param k     the specified kernel
   * @param pixel the specified pixel
   * @param copy  the copy of the original image to be filtered.
   */
  private void filterPixel(Kernel k, Pixel pixel, Image copy) {

    // calculating the index offset to line up the center of kernel with pixel
    int rowOffset = pixel.getPosition().getX() - k.getCenter();
    int columnOffset = pixel.getPosition().getY() - k.getCenter();

    double sumRed = 0;
    double sumGreen = 0;
    double sumBlue = 0;

    for (int row = 0; row < k.size(); row++) {
      for (int column = 0; column < k.size(); column++) {
        try {
          // getting the overlapping digit to multiply by
          double kernelFactor = k.getMatrix().get(row).get(column);
          // getting a copy of the current pixel
          Pixel currentPixel = copy.getPixelAt(row + rowOffset, column + columnOffset);
          // summing the kernel value * overlapping pixel channel value
          sumRed += currentPixel.getChannel(Channel.RED) * kernelFactor;
          sumGreen += currentPixel.getChannel(Channel.GREEN) * kernelFactor;
          sumBlue += currentPixel.getChannel(Channel.BLUE) * kernelFactor;
        } catch (IllegalArgumentException e) {
          // portions of the kernel do not overlap any pixels
        }
      }
    }

    // rounding double to int for pixel rgb value after kernel calculations.
    pixel.setChannel((int) Math.round(sumRed), Channel.RED);
    pixel.setChannel((int) Math.round(sumGreen), Channel.GREEN);
    pixel.setChannel((int) Math.round(sumBlue), Channel.BLUE);
  }


  /**
   * Applies a color trasnformation to this image depending on the matrix passed in.
   *
   * @param k matrix to linearly combine this images channels into new values
   * @throws IllegalArgumentException if matrix is not 3 by 3 or kernel is null
   */
  public void colorTransform(Kernel k) throws IllegalArgumentException {

    if (k == null) {
      throw new IllegalArgumentException("Null kernel");
    }
    if (k.size() != 3) {
      throw new IllegalArgumentException("Matrix size must be 3");
    }
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        Pixel currPixel = this.pixels.get(i).get(j);
        this.colorTransformHelper(currPixel, k);
      }

    }
  }

  // transforms a given pixel based on matrix
  private void colorTransformHelper(Pixel p, Kernel k) {
    Channel[] channelArray = Channel.values();

    Pixel pixelCopy = new Pixel(p);

    for (int i = 0; i < channelArray.length; i++) {
      int newVal = 0;
      int j = 0;
      for (Double d : k.getMatrix().get(i)) {
        newVal += pixelCopy.getChannel(channelArray[j]) * d;
        j++;
      }
      p.setChannel(newVal, channelArray[i]);
    }
  }

  /**
   * Manipulates this image for stained glass effect by setting clusters of pixels based on the
   * number of seeds specified.
   *
   * @param numSeeds   number of mosaic seeds
   * @param randomSeed a value to seed the random object, making testing easier.
   * @throws IllegalArgumentException if number of seeds is greater than number of pixels
   */
  public void mosaic(int numSeeds, int randomSeed) {

    List<Position2D> seeds = this
        .getRandomSeeds(numSeeds, randomSeed, this.getHeight(), this.getWidth());

    Map<Position2D, List<Pixel>> clusters = new HashMap<>();

    //initializing clusters as empty
    for (Position2D seed : seeds) {
      clusters.put(seed, new ArrayList<>());
    }

    // making cluster map
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        Pixel currPixel = this.pixels.get(i).get(j);

        Position2D pixelLoc = currPixel.getPosition();
        Position2D closestSeed = this.findClosestSeed(pixelLoc, seeds);

        // adding the pixel to the cluster it is associated with
        clusters.get(closestSeed).add(currPixel);
      }
    }

    // averaging cluster
    for (List<Pixel> cluster : clusters.values()) {
      this.averageClusterColors(cluster);
    }

  }

  // averages the colors of the pixel cluster by adding up all the rgb values
  // and setting all colors to that
  private void averageClusterColors(List<Pixel> cluster) {

    if (cluster.size() == 0) {
      return;
    }

    int rSum = 0;
    int gSum = 0;
    int bSum = 0;

    for (Pixel p : cluster) {
      rSum += p.getChannel(Channel.RED);
      gSum += p.getChannel(Channel.GREEN);
      bSum += p.getChannel(Channel.BLUE);
    }

    int rAvg = rSum / cluster.size();
    int gAvg = gSum / cluster.size();
    int bAvg = bSum / cluster.size();

    for (Pixel p : cluster) {
      p.setChannel(rAvg, Channel.RED);
      p.setChannel(gAvg, Channel.GREEN);
      p.setChannel(bAvg, Channel.BLUE);
    }

  }

  // determines the seed with the closest location to the given position
  private Position2D findClosestSeed(Position2D pixelLoc, List<Position2D> seeds) {

    double minDist = Integer.MAX_VALUE;
    Position2D currClosestSeed = null;

    for (Position2D seed : seeds) {
      double currDist = pixelLoc.findDistanceTo(seed);
      if (currDist < minDist) {
        minDist = currDist;
        currClosestSeed = seed;
      }
    }

    return currClosestSeed;

  }

  // does not allow duplicate seeds
  private List<Position2D> getRandomSeeds(int numSeeds, int randSeed, int rowBound, int colBound) {

    Set<Position2D> seeds = new HashSet<>();

    Random rand = new Random(randSeed);
    for (int i = 0; i < numSeeds; i++) {

      // continuous trying different random numbers till a new position is generated
      boolean flag = true;
      while (flag) {
        Position2D attemptedSeed = new Position2D(rand.nextInt(rowBound), rand.nextInt(colBound));
        flag = !seeds.add(attemptedSeed);
      }
    }

    if (seeds.size() != numSeeds) {
      throw new IllegalStateException("Did not generate proper number of seeds");
    }

    return new ArrayList<>(seeds);

  }


  /**
   * downscales this image with the specified width and height w represents the new width and h
   * represents the new height.
   *
   * @param w width
   * @param h height
   */
  public void downscale(int w, int h) {

    // checking that new width and height aren't bigger than current dimensions
    if (w > this.getWidth() || h > this.getHeight()) {
      throw new IllegalArgumentException("Cannot upscale");
    }

    List<List<Pixel>> pixelAcc = new ArrayList<>();
    List<Pixel> currRow;

    for (int i = 0; i < h; i++) {

      currRow = new ArrayList<>();
      for (int j = 0; j < w; j++) {
        // determining the proportional distance from the edge
        double wScale = (double) j / this.getWidth();
        double hScale = (double) i / this.getHeight();

        // the mapped coordinate of the downscaled image to the original image
        double xPrime = wScale * w;
        double yPrime = hScale * h;

        // finding the mapped color by using the 4 closest pixels in the original image
        int r = this.findMappedColor(xPrime, yPrime, Channel.RED);
        int g = this.findMappedColor(xPrime, yPrime, Channel.GREEN);
        int b = this.findMappedColor(xPrime, yPrime, Channel.BLUE);

        currRow.add(new Pixel(r, g, b, new Position2D(i, j)));

      }

      pixelAcc.add(currRow);
    }

    // resetting the current image state to downscaled image
    this.pixels = new ArrayList<>(pixelAcc);

  }

  // finding the mapped color by using the 4 closest pixels in the original image
  // xPrime represents mapped col location in the original image
  // yPrime represents the mapped row location in the original image
  private int findMappedColor(double xPrime, double yPrime, Channel channel) {

    int prevX = (int) Math.floor(xPrime);
    int nextX = (int) Math.ceil(xPrime);
    int prevY = (int) Math.floor(yPrime);
    int nextY = (int) Math.ceil(yPrime);

    // getting the 4 closest channel values
    int a = this.getPixelAt(prevY, prevX).getChannel(channel);
    int b = this.getPixelAt(prevY, nextX).getChannel(channel);
    int c = this.getPixelAt(nextY, prevX).getChannel(channel);
    int d = this.getPixelAt(nextY, nextX).getChannel(channel);

    int m = (int) (b * (xPrime - prevX) + a * (nextX - xPrime));
    int n = (int) (d * (xPrime - prevX) + c * (nextX - xPrime));

    return (int) (n * (yPrime - prevY) + m * (nextY - yPrime));

  }


  /**
   * Clamps each value in an image that is lesser than the minimum is assigned to the minimum, and
   * each value greater than the maximum is assigned to the maximum.
   *
   * @param min the permissible minimum channel value.
   * @param max the permissible maximum channel value.
   * @throws IllegalArgumentException when the permissible minimum is greater than max
   */
  public void clamp(int min, int max) {
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        this.pixels.get(i).get(j).clamp(min, max);
      }
    }

  }

  /**
   * Gets the max channel value for this image.
   *
   * @return the max channel value.
   */
  public int getMaxVal() {
    return this.maxVal;
  }
}