package controller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.LayeredImageProcessingModel;

/**
 * Represents an abstract command for a layered image processing model that requires additional
 * input than the model to execute.
 */
public abstract class MoreInputCommand implements LayerScriptCommand {

  protected final Scanner input;
  // next input
  protected final String nextIn;


  /**
   * Constructs this command object with the given scanner that contains more input. Checks to see
   * whether scanner has more input and sets nextIn to that input.
   *
   * @param input the scanner with more input
   * @throws IllegalArgumentException if there isn't enough input in the scanner.
   */
  protected MoreInputCommand(Scanner input) throws IllegalArgumentException {
    this.input = input;

    // throws exception if there is no next input
    if (input.hasNext()) {
      this.nextIn = input.next();
    } else {
      throw new IllegalArgumentException("Not enough input for script line.");
    }
  }

  @Override
  public abstract void execute(LayeredImageProcessingModel model) throws IOException;


}
