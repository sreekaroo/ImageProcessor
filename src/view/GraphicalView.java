package view;

import controller.MultiLayeredFeatures;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Represents a graphical based interactive view for a multilayered image processing application.
 * Does not deal with processing input but provides query methods for taking in input.
 */
public interface GraphicalView extends ImageProcessingView {

  /**
   * Updates the image displayed in this graphical view.
   *
   * @param img the image to be updated with.
   */
  void renderImage(BufferedImage img);

  /**
   * Updates the layer log according to the string of layer names provided.
   *
   * @param layers the layers to log.
   */
  void updateLayerLog(String layers);

  /**
   * Associates the multilayerd features with aspects of this view.
   *
   * @param multiLayeredFeatures the features to utilize.
   */
  void addFeatures(MultiLayeredFeatures multiLayeredFeatures);

  /**
   * Gets the path of a file when trying to open a file.
   *
   * @return the file path
   */
  String openFileManager();

  /**
   * Gets the path of a file when trying to save a file.
   *
   * @return the name of the file to be saved
   */
  String saveFileManager();

  /**
   * Gets the path of the directory trying to be opened.
   *
   * @return the absolute path of the directory.
   */
  String openFolderManager();

  /**
   * Resets the image in this view to nothing.
   */
  void resetImage();

  /**
   * Prompts the user for input and returns the input.
   *
   * @param prompt the prompt.
   * @return the user's input as a string
   */
  String getInput(String prompt);

  /**
   * Gets the color from choice of user.
   *
   * @return the color chosen.
   */
  Color getColor();
}
