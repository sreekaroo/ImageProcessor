package model.imagerepresentation;

import model.ImageProcessingModel;
import model.SimpleImageProcessingModel;

/**
 * Represents a layer in a multilayered image processing application.
 */
public class Layer {

  private final String name;
  private ImageProcessingModel content;
  private boolean isVisible;


  /**
   * Constructs this layer with no image and defaults visible to true.
   *
   * @param name the name of the layer.
   */
  public Layer(String name) {

    if (name == null) {
      throw new IllegalArgumentException();
    }

    this.name = name;
    this.content = new SimpleImageProcessingModel();
    this.isVisible = true;
  }

  /**
   * Copy constructor.
   *
   * @param l the layer to be copied.
   */
  public Layer(Layer l) {

    if (l == null) {
      throw new IllegalArgumentException();
    }
    this.name = l.getName();
    this.content = l.getContent();
    this.isVisible = l.isVisible;
  }

  /**
   * Determines whether this layer is visible.
   *
   * @return true if visible.
   */
  public boolean isVisible() {
    return isVisible;
  }

  /**
   * Toggles this layer's visibility.
   */
  public void toggleVisibility() {
    this.isVisible = !isVisible;
  }

  /**
   * Gets the name of this layer.
   *
   * @return the name
   */
  public String getName() {
    //TODO null check

    return this.name;
  }

  /**
   * Gets the image content from this layer.
   *
   * @return the image.
   */
  public ImageProcessingModel getContent() {
    return this.content;
  }

  //  public void load(Image content) {
  //    //TODO null check
  //    this.content.upload(content);
  //  }

}
