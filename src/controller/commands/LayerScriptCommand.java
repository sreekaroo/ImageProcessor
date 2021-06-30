package controller.commands;

import java.io.IOException;
import model.LayeredImageProcessingModel;

/**
 * Part of the command design pattern that represents a high level script command that can be
 * executed for any given LayeredimageProcessingModel.
 */

public interface LayerScriptCommand {

  /**
   * Executes this command according to the operations and state defined by the model.
   *
   * @param model the specific model to understand operations from.
   * @throws IllegalArgumentException if command cannot be executed with provided input.
   * @throws IOException              if command deals with IO and IO is not possible
   */
  void execute(LayeredImageProcessingModel model) throws IllegalArgumentException, IOException;
}
