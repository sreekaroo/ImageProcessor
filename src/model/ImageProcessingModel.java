package model;

/**
 * Represents a basic ImageProcessing model's possible operations, to manipulate an image.
 */
public interface ImageProcessingModel extends ImageProcessingModelState {


  /**
   * Blurs the entire current image using a gaussian blur kernel, changing the current image state
   * to blurred image ,clamping at a max value of 255 Does nothing if there is no image.
   *
   * @throws IllegalStateException - if there is no image.
   */
  void blur() throws IllegalStateException;

  /**
   * Sharpens the entire current image, clamping at a max value of 255, changing the current image
   * state to sharpened image.
   *
   * @throws IllegalStateException - if there is no image.
   */
  void sharpen() throws IllegalStateException;


  /**
   * Applies a greyscale color transformation to the entirety of the current image, clamping at max
   * channel values of 255.
   *
   * @throws IllegalStateException if there is no image.
   */
  void monochrome() throws IllegalStateException;


  /**
   * Applies a sepia color transformation to the current image, clamping at max channel values of
   * 255.
   *
   * @throws IllegalStateException if there is no image.
   */
  void sepia() throws IllegalStateException;


}
