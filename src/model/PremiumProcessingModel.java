package model;

/**
 * Represents an upgraded layered image processing model with premium effect operations.
 */
public interface PremiumProcessingModel extends LayeredImageProcessingModel {

  /**
   * Manipulates the currenty layer's image with a stained glass effect by setting clusters of
   * pixels based on the number of seeds specified.
   *
   * @param numSeeds   number of mosaic seeds
   * @param randomSeed a value to seed the random object, making testing easier.
   * @throws IllegalArgumentException if number of seeds is greater than number of pixels
   * @throws IllegalStateException    if no current layer or current layer has no image
   */
  void mosaic(int numSeeds, int randomSeed);

  /**
   * Resizes the image in the current layer with the new given width and height arguments.
   *
   * @param width  new width dim.
   * @param height new height dim.
   * @throws IllegalArgumentException if not a downscale resizing
   */
  void downscale(int width, int height);
}
