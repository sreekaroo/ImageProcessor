import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.PremiumProcessingModel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Layer;
import model.imagerepresentation.Pixel;
import model.programmaticimage.ProgrammaticImage;


/**
 * To represent a mock Complex Processing model (testing purposes only).
 */
public class MockComplex implements PremiumProcessingModel {

  private final Appendable appendable;

  MockComplex(Appendable s) {
    this.appendable = s;
  }

  @Override
  public void blur() throws IllegalStateException {
    try {
      this.appendable.append("Blur was called ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void sharpen() throws IllegalStateException {
    try {
      this.appendable.append("Sharpen was called ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void monochrome() throws IllegalStateException {
    try {
      this.appendable.append("Monochrome was called ");
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  @Override
  public void sepia() throws IllegalStateException {
    try {
      this.appendable.append("Sepia was called ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void upload(Image image) throws IllegalArgumentException {
    try {
      this.appendable.append("Upload was called ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Image export() {
    try {
      this.appendable.append("Image was exported");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Image createImage(ProgrammaticImage template) {
    try {
      this.appendable.append("Image was created ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Pixel getPixelAt(int x, int y) throws IllegalArgumentException, IllegalStateException {
    try {
      this.appendable.append("Got pixel ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      this.appendable.append(x + " " + y);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    try {
      this.appendable.append("Got width ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    try {
      this.appendable.append("Got height ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public void setCurrent(String layer) throws IllegalArgumentException {
    try {
      this.appendable.append("Set" + layer + " as current ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void remove() throws IllegalStateException {
    try {
      this.appendable.append("removed the layer ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createLayer(String name) throws IllegalArgumentException {
    try {
      this.appendable.append("created " + name);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void toggle(String layerName) throws IllegalArgumentException {
    try {
      this.appendable.append("Toggled " + layerName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void uploadMultiLayer(List<Layer> layers) {
    try {
      this.appendable.append("Uploaded " + layers);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Layer> exportAll() {
    try {
      this.appendable.append("Exported all layers ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  @Override
  public void mosaic(int numSeeds, int randomSeed) {
    try {
      this.appendable.append("mosaic ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void downscale(int width, int height) {
    try {
      this.appendable.append("downscale ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

