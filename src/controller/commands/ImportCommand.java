package controller.commands;

import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.ImageFileInterpreterFactory;
import controller.fileinterpreting.ImageFileInterpreterFactory.FileType;
import controller.fileinterpreting.OurImageUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.LayeredImageProcessingModel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Layer;

/**
 * Represents a command object to upload an entire multilayer project.
 */
public class ImportCommand extends MoreInputCommand {

  /**
   * Constructs this command object with the given scanner that contains more input.
   *
   * @param input the scanner with more input
   */
  public ImportCommand(Scanner input) throws IllegalArgumentException {
    super(input);
  }

  @Override
  public void execute(LayeredImageProcessingModel model)
      throws IllegalArgumentException, IOException {

    List<Layer> acc = new ArrayList<>();

    File textFile = new File(nextIn + "/" + "LayerData.txt");
    Scanner scan = new Scanner(textFile);

    // reading the text file
    while (scan.hasNext()) {
      String layerName = scan.next();
      Layer current = new Layer(layerName);

      String imgPath = scan.next();

      // finding the file type from the given fileName
      FileType fileType = OurImageUtil.findFileExtension(imgPath);
      ImageFileInterpreter<Image> interpreter =
          ImageFileInterpreterFactory.create(fileType);
      //uploading the image data from text file path to each layer
      current.getContent().upload(interpreter.convert(imgPath));

      // updating the layer based on text file saying to make layer visible or not
      boolean isVisible = scan.nextBoolean();
      if (!isVisible) {
        current.toggleVisibility();
      }

      acc.add(current);
    }

    // uploading the layers to multilayer model
    model.uploadMultiLayer(acc);
  }
}
