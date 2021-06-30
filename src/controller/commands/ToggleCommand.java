package controller.commands;


import java.util.Scanner;
import model.LayeredImageProcessingModel;

/**
 * Represent the command for toggling a layer's visibility, indicated by "toggle".
 */
public class ToggleCommand extends MoreInputCommand {

  /**
   * Constructs this command object with the given scanner that contains more input.
   *
   * @param input the scanner with more input
   */
  public ToggleCommand(Scanner input) throws IllegalArgumentException {
    super(input);
  }

  @Override
  public void execute(LayeredImageProcessingModel model) {
    model.toggle(nextIn);
  }
}
