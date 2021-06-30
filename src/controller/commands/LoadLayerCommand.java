package controller.commands;

import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.ImageFileInterpreterFactory;
import controller.fileinterpreting.ImageFileInterpreterFactory.FileType;
import controller.fileinterpreting.OurImageUtil;
import java.io.IOException;
import java.util.Scanner;
import model.LayeredImageProcessingModel;
import model.imagerepresentation.Image;

/**
 * Represents a command object to load in an image file with a given path name into the current
 * layer.
 */
public class LoadLayerCommand extends MoreInputCommand {

  /**
   * Constructs this load layer command object with the given scanner that contains more input.
   *
   * @param scan the scanner with more input.
   */
  public LoadLayerCommand(Scanner scan) {
    super(scan);
  }


  @Override
  public void execute(LayeredImageProcessingModel model)
      throws IllegalArgumentException, IOException {

    // finding the file type from the given fileName
    FileType fileType = OurImageUtil.findFileExtension(nextIn);
    ImageFileInterpreter<Image> interpreter =
        ImageFileInterpreterFactory.create(fileType);

    //uploading the converted image
    model.upload(interpreter.convert(nextIn));

  }
}
