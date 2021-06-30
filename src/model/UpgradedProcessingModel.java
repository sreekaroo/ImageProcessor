package model;

import model.imagerepresentation.Image;

/**
 * Represents an upgraded processing model implementation with added functionality that most
 * processing model's don't have.
 */
public class UpgradedProcessingModel extends ComplexProcessingModel implements
    PremiumProcessingModel {


  /**
   * Default constructor : Constructs this processing model.
   */
  public UpgradedProcessingModel() {
    super();
  }

  @Override
  public void mosaic(int numSeeds, int randomSeed) {
    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }
    // applying the mosaic
    Image mosaic = this.currLayer.getContent().export();
    mosaic.mosaic(numSeeds, randomSeed);

    // uploading the mosaicked image to the current layer
    currLayer.getContent().upload(mosaic);
  }

  @Override
  public void downscale(int width, int height) {

    if (currLayer == null) {
      throw new IllegalStateException("No current layer");
    }

    Image img = this.currLayer.getContent().export();
    img.downscale(width, height);

    // uploading the mosaicked image to the current layer
    currLayer.getContent().upload(img);

  }
}
