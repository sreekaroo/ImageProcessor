package model;

import model.imagerepresentation.Image;
import model.imagerepresentation.Kernel;
import model.imagerepresentation.Pixel;
import model.programmaticimage.ProgrammaticImage;

/**
 * represents the model which represents the state of a single layered image processor.
 */
public class SimpleImageProcessingModel implements ImageProcessingModel {

  private Image currentImage;

  /**
   * Constructs this model with no current image and an empty stack of saved images.
   */
  public SimpleImageProcessingModel() {
    this.currentImage = null;
  }


  @Override
  public void upload(Image image) {
    this.currentImage = new Image(image);
  }


  @Override
  public Image export() {
    if (this.currentImage == null) {
      throw new IllegalStateException("No image to export");
    }
    return new Image(this.currentImage);
  }

  @Override
  public void blur() throws IllegalStateException {
    if (currentImage == null) {
      throw new IllegalStateException("No image found");
    }
    currentImage.filter(Kernel.BLUR_KERNEL);
    // model automatically clamps at 0 and 255
    this.currentImage.clamp(0, 255);
  }

  @Override
  public void sharpen() throws IllegalStateException {
    if (currentImage == null) {
      throw new IllegalStateException("No image to modify");
    }
    currentImage.filter(Kernel.SHARPEN_KERNEL);
    // model automatically clamps at 0 and 255
    this.currentImage.clamp(0, 255);
  }

  @Override
  public void monochrome() throws IllegalStateException {
    if (currentImage == null) {
      throw new IllegalStateException("No image to modify");
    }

    currentImage.colorTransform(Kernel.GREYSCALE_KERNEL);

    // model automatically clamps at 0 and 255
    this.currentImage.clamp(0, 255);
  }

  @Override
  public void sepia() throws IllegalStateException {
    if (currentImage == null) {
      throw new IllegalStateException("No image to modify");
    }
    currentImage.colorTransform(Kernel.SEPIA_KERNEL);
    // model automatically clamps at 0 and 255
    this.currentImage.clamp(0, 255);

  }


  @Override
  public Image createImage(ProgrammaticImage template) {
    return template.create();
  }


  @Override
  public Pixel getPixelAt(int x, int y) throws IllegalStateException, IllegalArgumentException {
    if (this.currentImage == null) {
      throw new IllegalStateException("No Image found.");
    }

    return currentImage.getPixelAt(x, y);
  }

  @Override
  public int getWidth() {
    if (this.currentImage == null) {
      throw new IllegalStateException("No Image found.");
    }
    return this.currentImage.getWidth();
  }

  @Override
  public int getHeight() {
    if (this.currentImage == null) {
      throw new IllegalStateException("No Image found.");
    }
    return this.currentImage.getHeight();
  }

}
