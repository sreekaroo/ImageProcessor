package view;

import java.io.IOException;

/**
 * To represent the view of the image processor.
 */
public interface ImageProcessingView {

  /**
   * Renders the given message to the destination.
   *
   * @param message the message to be conveyed to destination.
   * @throws IOException if destination is invalid.
   */
  void renderMessage(String message);
}
