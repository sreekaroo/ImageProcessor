package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.imagerepresentation.Image;
import model.imagerepresentation.Layer;
import model.imagerepresentation.Pixel;
import model.programmaticimage.ProgrammaticImage;

/**
 * represents the state and operations to change or observer the state of a multi layered image
 * processing application.
 */
public class ComplexProcessingModel implements LayeredImageProcessingModel {

  protected Layer currLayer;
  protected List<Layer> layers;

  //INVARIANT: any images in layers have the same dimensions.

  // default constructor
  public ComplexProcessingModel() {
    this(null, new ArrayList<>());
  }

  private ComplexProcessingModel(Layer currLayer, List<Layer> layers) {
    this.currLayer = currLayer;
    this.layers = layers;
  }


  @Override
  public void uploadMultiLayer(List<Layer> layers) {

    // resetting this projects layers
    this.layers = new ArrayList<>();
    // resetting the current layer
    this.currLayer = null;

    // setting this models layers to the uploaded ones
    this.layers = new ArrayList<>(layers);

  }

  @Override
  public List<Layer> exportAll() {
    List<Layer> copy = new ArrayList<>();
    for (Layer l : layers) {
      copy.add(new Layer(l));
    }
    return copy;
  }

  @Override
  public void upload(Image image) throws IllegalArgumentException {

    if (currLayer == null) {
      throw new IllegalStateException("No current layer, create layer first");
    }

    if (this.isSameDim(image)) {
      this.currLayer.getContent().upload(image);
    } else {
      throw new IllegalArgumentException("Image must be same size as other layers");
    }

  }

  // determines whether image has the same dimensions in pixels as other layers
  private boolean isSameDim(Image image) {
    ImageProcessingModel delegate;

    int actualWidth;
    int actualHeight;

    boolean flag = true;

    for (Layer l : layers) {
      delegate = l.getContent();
      try {
        actualWidth = delegate.getWidth();
        actualHeight = delegate.getHeight();

        flag = flag && actualWidth == image.getWidth() && actualHeight == image.getHeight();
      } catch (IllegalStateException e) {
        // catching cases where there is no image in a layer
      }
    }

    return flag;
  }

  @Override
  public Image export() throws IllegalStateException {
    for (Layer l : layers) {
      try {
        if (l.isVisible()) {
          return l.getContent().export();
        }
      } catch (IllegalStateException e) {
        // ignore layers without images
      }
    }
    throw new IllegalStateException("no layers");
  }


  @Override
  public void blur() throws IllegalStateException {

    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    this.currLayer.getContent().blur();

  }

  @Override
  public void sharpen() throws IllegalStateException {
    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    this.currLayer.getContent().sharpen();
  }

  @Override
  public void monochrome() throws IllegalStateException {

    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    this.currLayer.getContent().monochrome();

  }

  @Override
  public void sepia() throws IllegalStateException {

    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    this.currLayer.getContent().sepia();

  }


  @Override
  public void setCurrent(String layerName) {
    int layerIndex = this.findLayer(layerName);
    if (layerIndex == -1) {
      throw new IllegalArgumentException("Layer not found");
    }

    // setting the current layer to the name specified
    this.currLayer = this.layers.get(layerIndex);

  }


  @Override
  public void remove() {
    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    // check whether remove actually removes the layer we want
    // if not write our own remove layer method
    layers.remove(currLayer);
    this.currLayer = null;
  }

  @Override
  public void createLayer(String layerName) throws IllegalArgumentException {
    // checking for duplicate layer name
    if (findLayer(layerName) != -1) {
      throw new IllegalArgumentException("Layer already exists");
    }

    Layer newLayer = new Layer(layerName);

    // adds the new layer to the bottom or end of layer list
    this.layers.add(newLayer);

    // setting the newly created layer to the current layer automatically
    // if that is the only layer in this model
    if (layers.size() == 1) {
      this.setCurrent(layerName);
    }
  }

  @Override
  public void toggle(String layerName) throws IllegalArgumentException {

    int layerIndex = this.findLayer(layerName);
    if (layerIndex == -1) {
      throw new IllegalArgumentException("Cannot find layer");
    }

    Layer l = this.layers.get(layerIndex);
    l.toggleVisibility();
  }


  @Override
  public Image createImage(ProgrammaticImage template) {
    return new SimpleImageProcessingModel().createImage(template);
  }

  @Override
  public Pixel getPixelAt(int x, int y) throws IllegalArgumentException, IllegalStateException {
    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }
    return this.currLayer.getContent().getPixelAt(x, y);
  }

  @Override
  public int getWidth() throws IllegalStateException {
    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    return this.currLayer.getContent().getWidth();
  }

  @Override
  public int getHeight() throws IllegalStateException {
    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    return this.currLayer.getContent().getHeight();
  }

  @Override
  public String toString() {
    Appendable acc = new StringBuilder();
    String currLayerName = "";
    if (currLayer != null) {
      currLayerName = currLayer.getName();
    }

    for (Layer l : layers) {
      try {
        acc.append(l.getName());
        if (!l.isVisible()) {
          acc.append("*");
        }
        if (l.getName().equals(currLayerName)) {
          acc.append("^");
        }
        acc.append("\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return acc.toString();

  }

  // finds the layer with the given name
  // if cannot find returns -1
  private int findLayer(String layerName) {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName().equals(layerName)) {
        return i;
      }
    }
    return -1;
  }

  private Layer findTopVisibleLayer() throws IllegalStateException {
    for (Layer l : layers) {
      if (l.isVisible()) {
        return l;
      }
    }
    throw new IllegalStateException("No visible layers");
  }

}

