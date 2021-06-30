import controller.MultiLayeredFeatures;
import java.awt.Color;
import java.awt.image.BufferedImage;
import view.GraphicalView;

/**
 * Represents a fake view for testing.
 */
public class MockGUIView implements GraphicalView {

  private final StringBuilder log;

  public MockGUIView(StringBuilder log) {
    this.log = log;
  }

  @Override

  public void renderImage(BufferedImage img) {

    this.log.append("renderImage\n");

  }

  @Override
  public void updateLayerLog(String layerLog) {
    this.log.append("updateLayerLog\n");
  }

  @Override
  public void addFeatures(MultiLayeredFeatures features) {
    this.log.append("addFeatures\n");
  }

  @Override
  public String openFileManager() {
    this.log.append("openFileManager\n");
    return null;
  }

  @Override
  public String saveFileManager() {
    this.log.append("saveFileManager\n");
    return null;
  }

  @Override
  public void resetImage() {
    this.log.append("resetImage\n");
  }

  @Override
  public String openFolderManager() {
    this.log.append("openFolderManager\n");
    return null;
  }

  @Override
  public String getInput(String prompt) {
    this.log.append("getInput\n");
    return "";
  }

  @Override
  public Color getColor() {
    this.log.append("getColor\n");
    // returns dummy null value
    return null;
  }

  @Override
  public void renderMessage(String message) {
    this.log.append("renderMessage\n");
  }
}
