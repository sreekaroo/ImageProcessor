package controller.commands;

import java.util.Scanner;
import model.LayeredImageProcessingModel;

/**
 * Represents the command object to set the current layer as the layer given. Indicated by
 * "current".
 */
public class SetCurrentCommand extends MoreInputCommand {

  /**
   * Constructs this create command object with the given scanner that contains more input.
   *
   * @param input the scanner with more input
   */
  public SetCurrentCommand(Scanner input) throws IllegalArgumentException {
    super(input);
  }

  @Override
  public void execute(LayeredImageProcessingModel model) throws IllegalArgumentException {
    model.setCurrent(nextIn);
  }
}
