package controller;

import controller.commands.CheckerboardCommand;
import controller.commands.CreateLayerCommand;
import controller.commands.ExportCommand;
import controller.commands.ImportCommand;
import controller.commands.LayerScriptCommand;
import controller.commands.LoadLayerCommand;
import controller.commands.SaveCommand;
import controller.commands.SetCurrentCommand;
import controller.commands.ToggleCommand;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.LayeredImageProcessingModel;
import view.ImageProcessingView;

/**
 * Represents a controller that reads in input as command scripts for a layered image processing
 * controller. Where each line represents its own command.
 */
public class SimpleScriptController implements LayeredImageScriptController {

  private final LayeredImageProcessingModel model;
  private final Readable input;
  private final ImageProcessingView view;

  /**
   * Constructs this script controller with provided arguments.
   *
   * @param model the layered image model to delegate operations to.
   * @param input the desired input source.
   * @param view  the desired view to delegate display features to.
   * @throws IllegalArgumentException if any arguments are null.
   */
  public SimpleScriptController(LayeredImageProcessingModel model, Readable input,
      ImageProcessingView view) throws IllegalArgumentException {
    if (model == null || input == null || view == null) {
      throw new IllegalArgumentException("Null argument");
    }
    this.model = model;
    this.input = input;
    this.view = view;
  }


  @Override
  public void start() throws IOException {

    // getting known commands
    Map<String, Function<Scanner, LayerScriptCommand>> knownCommands = this.getKnownCommands();

    Scanner scan = new Scanner(input);
    while (scan.hasNext()) {
      // reading script line by line
      Scanner scanLine = new Scanner(scan.nextLine());
      while (scanLine.hasNext()) {
        String in = scanLine.next();
        // quitting on q or Q
        if (in.toLowerCase().equals("q")) {
          view.renderMessage("Program quit.");
          return;
        }

        LayerScriptCommand cmd;
        if (knownCommands.containsKey(in)) {
          try {
            // getting the command and creating the command object
            cmd = knownCommands.get(in).apply(scanLine);
            // executing the command
            cmd.execute(model);
          } catch (IllegalArgumentException | IllegalStateException e) {
            String errorMessage = e.getMessage();
            // render to destination with helpful info
            view.renderMessage(errorMessage + " " + "try command again" + "\n");

          }
        } else {
          // render to destination
          view.renderMessage("unsupported command, try again." + "\n");
        }

      }
    }

  }

  // returns a map of all the known commands for this controller and model
  private Map<String, Function<Scanner, LayerScriptCommand>> getKnownCommands() {
    Map<String, Function<Scanner, LayerScriptCommand>> knownCommands = new HashMap<>();

    /*
    Generating the map of known commands
     */
    knownCommands.put("create", s -> new CreateLayerCommand(s));
    knownCommands.put("load", s -> new LoadLayerCommand(s));
    knownCommands.put("current", s -> new SetCurrentCommand(s));
    knownCommands.put("toggle", (s -> new ToggleCommand(s)));
    knownCommands.put("export", (s -> new ExportCommand(s)));
    knownCommands.put("import", (s -> new ImportCommand(s)));
    knownCommands.put("save", (s -> new SaveCommand(s)));
    knownCommands.put("checkerboard", (s -> new CheckerboardCommand(s)));
    // no additional input needed
    knownCommands.put("remove", (s -> (m -> m.remove())));
    knownCommands.put("blur", (s -> (m -> m.blur())));
    knownCommands.put("sharpen", (s -> (m -> m.sharpen())));
    knownCommands.put("monochrome", (s -> (m -> m.monochrome())));
    knownCommands.put("sepia", (s -> (m -> m.sepia())));

    return knownCommands;
  }

}

