package model;

import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;
import model.programmaticimage.ProgrammaticImage;

/**
 * This interface represents all the observer operations and all operations that do no mutate this
 * model, to support an Image processing application.
 */
public interface ImageProcessingModelState {

  /**
   * Imports a given image resetting the current image state to the given one.
   *
   * @param image the image to be uploaded
   * @throws IllegalArgumentException if image is null.
   */
  void upload(Image image) throws IllegalArgumentException;

  /**
   * Exports a copy of the image in this image processor.
   *
   * @return the image.
   */
  Image export();

  /**
   * Creates an image from the given image template programmatically .
   *
   * @param template the object that represents the type of programmatic image to be created.
   * @return the generated image.
   * @throws IllegalArgumentException if template is null.
   */
  Image createImage(ProgrammaticImage template);

  /**
   * gets the pixel at the specified location.
   *
   * @param x the row index of the pixel in the image
   * @param y the column index of the pixel in the image
   * @return a copy of the specified pixel.
   * @throws IllegalArgumentException if given invalid coordinates.
   * @throws IllegalStateException    if there's no image uploaded.
   */
  Pixel getPixelAt(int x, int y) throws IllegalArgumentException, IllegalStateException;


  /**
   * gets the width of the image in number of pixels.
   *
   * @return int representing width.
   * @throws IllegalStateException if theres no image uploaded.
   */
  int getWidth() throws IllegalStateException;

  /**
   * gets the height of the image, in number of pixels.
   *
   * @return int representing height.
   * @throws IllegalStateException if there's no image uploaded.
   */
  int getHeight() throws IllegalStateException;
}
