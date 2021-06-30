package controller;


/**
 * Represents a high level overview of the features supported by an image processing application;
 * essentially represents the supported callback methods needed for an interactive view and controller.
 */
public interface MultiLayeredFeatures {

  /**
   * Loads in an image to the current layer of a multilayered model.
   */
  void load();

  /**
   * Exports the top most visible layer as an image file.
   */
  void save();

  /**
   * Imports an entire multilayered image project, resetting the state of the model to the imported
   * one.
   */
  void importProject();

  /**
   * Exports an entire mulilayered image project to a directory with all the layers, image data, and
   * visibility data.
   */
  void exportProject();

  /**
   * Creates a visible layer with the given name, with no image.
   *
   * @param name name of desired layer.
   */
  void createLayer(String name);

  /**
   * Sets the current layer in mulitlayered image processing application to the layer specified.
   *
   * @param layer the layer specified.
   */
  void setCurrent(String layer);

  /**
   * Toggles the visibility of the specified layer in mulitlayered image processing application.
   *
   * @param layer the layer specified.
   */
  void toggle(String layer);

  /**
   * Removes the current layer in multilayered image processing application.
   */
  void remove();

  /**
   * Applies a blurring filter to the current layer in an image processing application.
   */
  void blur();

  /**
   * Applies a sharpening filter to the current layer in an image processing application.
   */
  void sharpen();

  /**
   * Applies a sepia filter to the current layer in an image processing application.
   */
  void sepia();

  /**
   * Applies a greyscale filter to the current layer in an image processing application.
   */
  void monochrome();


  /**
   * Applies a mosaic operation on the current layer's image.
   */
  void mosaic();

  /**
   * Applies a downscale resizing of the current layer's image.
   */
  void downscale();

  /**
   * Creates a checkerboard image for the current layer in the image processing application.
   */
  void createCheckerboard();


  /**
   * Reads in script commands to execute for a layered image processing application.
   */
  void readScript();

  /**
   * Exits the image processing application.
   */
  void quit();


}
