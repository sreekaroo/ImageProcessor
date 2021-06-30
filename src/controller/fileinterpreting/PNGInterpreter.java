package controller.fileinterpreting;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.imagerepresentation.Image;

/**
 * Represents an PNG image interpreter object that can interpret PNG image data into an Image
 * representation.
 */
public class PNGInterpreter extends CommonFileInterpreter {


  @Override
  public void export(String name, Image content) throws IOException {
    if (name == null) {
      throw new IllegalArgumentException("Null name");
    }
    // writing to new file with the image data
    ImageIO.write(this.getImageData(content), "png", new File(name));
  }
}
