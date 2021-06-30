package view;

import java.io.IOException;

/**
 * Represents a basic text view for text views of an image processing application.
 */
public class ImageProcessingTextView implements ImageProcessingView {

  private final Appendable destination;

  public ImageProcessingTextView(Appendable destination) {
    this.destination = destination;
  }

  /**
   * Renders the given message to the destination.
   *
   * @param message the message to be conveyed to destination.
   * @throws IOException if destination is invalid.
   */
  @Override
  public void renderMessage(String message) {

    try {
      destination.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
