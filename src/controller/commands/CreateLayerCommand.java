package controller.commands;

import java.util.Scanner;
import model.LayeredImageProcessingModel;

/**
 * Represents the command object to create a layer with a given name.
 */
public class CreateLayerCommand extends MoreInputCommand {

  /**
   * Constructs this create layer command object with the given scanner that contains more input.
   *
   * @param scan the scanner with more input
   */
  public CreateLayerCommand(Scanner scan) {
    super(scan);
  }

  @Override
  public void execute(LayeredImageProcessingModel model) throws IllegalArgumentException {
    model.createLayer(nextIn);
    // more input after layer name on this script line
    if (input.hasNext()) {
      throw new IllegalArgumentException("Too much input for this script line");
    }
  }
}
