package controller.fileinterpreting;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import controller.fileinterpreting.ImageFileInterpreterFactory.FileType;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.imagerepresentation.Channel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;
import model.imagerepresentation.Position2D;


/**
 * This class contains utility methods to read and interpret image files.
 */
public class OurImageUtil {


  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(String filename, Appendable destination, List<List<Pixel>> pixelAcc)
      throws IOException, IllegalArgumentException {
    Scanner sc;

    // remember to catch this exception in controller
    sc = new Scanner(new FileInputStream(filename));

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Not a PPM file.");
    }
    int width = sc.nextInt();
    destination.append("" + width + " ");
    int height = sc.nextInt();
    destination.append("" + height + " ");
    int maxValue = sc.nextInt();
    destination.append("" + maxValue);

    List<Pixel> currRow = new ArrayList<Pixel>();

    for (int i = 0; i < height; i++) {
      currRow = new ArrayList<Pixel>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel currPixel = new Pixel(r, g, b, new Position2D(i, j));
        currRow.add(currPixel);
      }
      pixelAcc.add(currRow);
    }
  }

  /**
   * Represents the filetype of a file.
   *
   * @param fileName the name of the file.
   * @return the filetype the file is.
   */
  public static FileType findFileExtension(String fileName) {

    int lastPeriodIndex = fileName.lastIndexOf(".");
    String extension;
    if (lastPeriodIndex == -1) {
      throw new IllegalArgumentException("No file extension found");
    }
    try {
      extension = fileName.substring(lastPeriodIndex + 1).toLowerCase();
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("No file extension found");
    }

    for (FileType type : FileType.values()) {
      if (extension.equals(type.toString())) {
        return type;
      }
    }

    throw new IllegalArgumentException("Unsupported file extension");

  }

  /**
   * converts the Image into a buffered image.
   *
   * @param ourImageRep an Image
   * @return a buffered image.
   */
  public static BufferedImage convertToBufferedImage(Image ourImageRep) {
    if (ourImageRep == null) {
      throw new IllegalStateException("No Image to export");
    }

    BufferedImage imgData = new BufferedImage(ourImageRep.getWidth(), ourImageRep.getHeight(),
        TYPE_INT_RGB);

    for (int i = 0; i < ourImageRep.getHeight(); i++) {
      for (int j = 0; j < ourImageRep.getWidth(); j++) {
        Pixel currPixel = ourImageRep.getPixelAt(i, j);
        // putting the color of the pixel in a color object
        Color rgbPixel = new Color(currPixel.getChannel(Channel.RED),
            currPixel.getChannel(Channel.GREEN),
            currPixel.getChannel(Channel.BLUE));

        // need to set with inverse indices because buffered image uses coordinate x y system
        imgData.setRGB(j, i, rgbPixel.getRGB());
      }
    }

    return imgData;

  }


}

