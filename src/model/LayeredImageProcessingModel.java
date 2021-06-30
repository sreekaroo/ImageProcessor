package model;

import java.util.List;
import model.imagerepresentation.Layer;

/**
 * To represents a Image Processing model interface that supports multilayer operations.
 */
public interface LayeredImageProcessingModel extends ImageProcessingModel {

  /**
   * Sets the specified layer as the current layer.
   *
   * @param layer the layer
   * @throws IllegalArgumentException if the layer is invalid.
   */
  void setCurrent(String layer) throws IllegalArgumentException;

  /**
   * removes the current layer.
   *
   * @throws IllegalStateException if there is no current layer to remove or if it is null.
   */
  void remove() throws IllegalStateException;

  /**
   * adds a new visible layer with no content.
   *
   * @param name name to make layer with.
   * @throws IllegalArgumentException if there is already a layer with that name.
   */
  void createLayer(String name) throws IllegalArgumentException;

  /**
   * toggles a layer as visble or invisible.
   *
   * @throws IllegalArgumentException if there is no layer to toggle with given name.
   */
  void toggle(String layerName) throws IllegalArgumentException;

  /**
   * uploads and resets this model state to the layers given.
   *
   * @param layers the layers to reset this model's state.
   */
  void uploadMultiLayer(List<Layer> layers);

  /**
   * Exports all the layers with content, in order from top to bottom.
   *
   * @return the layers
   */
  List<Layer> exportAll();

  /**
   * Formats all the layers in this multilayered model as followed:  If a layer is invisible the
   * layer name will be followed by an asterisk:  "*". If the layer is the current layer it will be
   * followed by a "^"
   *
   * <p>For example: a multilayer model with layers called "one", "two" ,
   * and "three" , where two is invisible and one is the current will be formatted as such:
   *
   * <p>"Current Layer: one
   *
   * </p>
   *
   * <p>one two* three "
   *
   * </p>@return the formatted string
   */
  @Override
  String toString();


}
