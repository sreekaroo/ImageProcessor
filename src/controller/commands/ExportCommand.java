package controller.commands;

import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.ImageFileInterpreterFactory;
import controller.fileinterpreting.ImageFileInterpreterFactory.FileType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import model.LayeredImageProcessingModel;
import model.imagerepresentation.Image;
import model.imagerepresentation.Layer;

/**
 * Represents a command object to export all the layers in a multilayer model.
 */
public class ExportCommand extends MoreInputCommand {

  /**
   * Constructs this export command object with the given scanner that contains more input.
   *
   * @param input the scanner with more input.
   */
  public ExportCommand(Scanner input) throws IllegalArgumentException {
    super(input);
  }

  @Override
  public void execute(LayeredImageProcessingModel model)
      throws IllegalArgumentException, IOException {

    if (input.hasNext()) {
      String fileType = input.next();

      // making the project directory
      Files.createDirectories(Paths.get(nextIn));

      String pathForExport = nextIn + "/";
      Appendable textFileContent = new StringBuilder();
      String layerName = "";
      String layerImgPath = "";
      boolean isVisible = true;

      for (Layer l : model.exportAll()) {

        try {
          // adding to text file
          layerName = l.getName();
          layerImgPath = pathForExport + layerName + "." + FileType.valueOf(fileType);
          isVisible = l.isVisible();

          // exporting image content to specified directory path
          ImageFileInterpreter<Image> interpreter =
              ImageFileInterpreterFactory.create(FileType.valueOf(fileType.toUpperCase()));
          interpreter.export(layerImgPath, l.getContent().export());

          textFileContent.append(layerName + "\n");
          textFileContent.append(layerImgPath + "\n");
          textFileContent.append(isVisible + "\n");


        } catch (IllegalStateException e) {
          // don't export blank layer.
        } catch (IllegalArgumentException a) {
          throw new IllegalArgumentException("Unsupported file type");
        }
      }

      // making text file in project folder with layer locations
      File textFile = new File(pathForExport + "LayerData.txt");
      FileWriter writer = new FileWriter(textFile);
      writer.write(textFileContent.toString());
      writer.close();

    } else {
      throw new IllegalArgumentException(
          "Expecting file type but could not find. Not enough input.");
    }

  }
}
