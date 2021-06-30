package controller.fileinterpreting;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.imagerepresentation.Image;

/**
 * Represents an JPEG image interpreter object that can interpret JPEG image data into an Image
 * representation.
 */
public class JPEGInterpreter extends CommonFileInterpreter {

  @Override
  public void export(String name, Image content) throws IOException {
    if (name == null) {
      throw new IllegalArgumentException("Null name");
    }

    // writing to new file with the image data
    ImageIO.write(this.getImageData(content), "jpg", new File(name));

  }
}
