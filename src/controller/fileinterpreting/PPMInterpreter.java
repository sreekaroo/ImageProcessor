package controller.fileinterpreting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.imagerepresentation.Channel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Pixel;

/**
 * Represents an PPM image interpreter object that can interpret P3 image data into an Image
 * representation.
 */
public class PPMInterpreter implements ImageFileInterpreter<Image> {


  @Override
  public void export(String name, Image content) throws IOException, IllegalArgumentException {

    if (content == null) {
      throw new IllegalStateException("No image to export");
    }

    File destFile = new File(name);
    FileWriter writer = new FileWriter(destFile);

    writer.write("P3");
    writer.write("\n" + content.getWidth() + " " + content.getHeight());
    writer.write("\n" + content.getMaxVal());

    Appendable pixelData = new StringBuilder();

    for (int i = 0; i < content.getHeight(); i++) {
      for (int j = 0; j < content.getWidth(); j++) {
        Pixel currPixel = content.getPixelAt(i, j);
        int r = currPixel.getChannel(Channel.RED);
        int g = currPixel.getChannel(Channel.GREEN);
        int b = currPixel.getChannel(Channel.BLUE);

        pixelData.append("\n" + r + "\n" + g + "\n" + b);
      }
    }

    writer.write(pixelData.toString());
    writer.close();
  }

  @Override
  public Image convert(String fileName) throws IOException {
    List<List<Pixel>> accPixels = new ArrayList<List<Pixel>>();
    Appendable imageData = new StringBuilder();

    // populates the imageData and accPixels
    OurImageUtil.readPPM(fileName, imageData, accPixels);
    Scanner scan = new Scanner(imageData.toString());

    int width = scan.nextInt();
    int height = scan.nextInt();
    // dont know if we need for clamp
    int maxVal = scan.nextInt();

    return new Image(accPixels, maxVal);

  }


}


