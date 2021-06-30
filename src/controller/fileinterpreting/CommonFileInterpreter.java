package controller.fileinterpreting;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;

/**
 * Represents an interpreter that supports a common file type such as jpeg or png.
 */
public abstract class CommonFileInterpreter implements ImageFileInterpreter<Image> {


  @Override
  public Image convert(String fileName) throws IOException, IllegalArgumentException {
    BufferedImage imageData = ImageIO.read(new File(fileName));

    List<List<Pixel>> pixelAcc = new ArrayList<>();
    List<Pixel> currRow;

    for (int i = 0; i < imageData.getHeight(); i++) {
      currRow = new ArrayList<>();
      for (int j = 0; j < imageData.getWidth(); j++) {
        // creates a color object that ignores the alpha component of the image data
        // getRGB goes by x y coordinate system so we need to invert indices
        Color argbColor = new Color(imageData.getRGB(j, i));
        currRow.add(new Pixel(argbColor, new Position2D(i, j)));
      }
      pixelAcc.add(currRow);
    }

    return new Image(pixelAcc);
  }

  @Override
  public abstract void export(String name, Image content) throws IOException;


  /**
   * Converts the image representation into a more manageable form for javas ImageIo class, which
   * has support for common image file types.
   *
   * @return a RenderedImage object that contains this images pixel data.
   */
  public BufferedImage getImageData(Image img) {

    return OurImageUtil.convertToBufferedImage(img);
  }


}
