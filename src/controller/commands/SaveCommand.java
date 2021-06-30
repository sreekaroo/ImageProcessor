package controller.commands;


import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.ImageFileInterpreterFactory;
import controller.fileinterpreting.ImageFileInterpreterFactory.FileType;
import java.io.IOException;
import java.util.Scanner;
import model.LayeredImageProcessingModel;
import model.imagerepresentation.Image;

/**
 * Represents the command for "save" which exports the topmost visible layer. Exports with provided
 * name and file type.
 */
public class SaveCommand extends MoreInputCommand {

  public SaveCommand(Scanner input) throws IllegalArgumentException {
    super(input);
  }


  @Override
  public void execute(LayeredImageProcessingModel model)
      throws IllegalArgumentException, IllegalStateException, IOException {

    if (input.hasNext()) {
      String fileType = input.next();
      ImageFileInterpreter<Image> interpreter =
          ImageFileInterpreterFactory.create(FileType.valueOf(fileType.toUpperCase()));
      interpreter.export(nextIn, model.export());
    } else {
      throw new IllegalArgumentException(
          "Expecting file type but could not find. Not enough input.");
    }
  }
}
