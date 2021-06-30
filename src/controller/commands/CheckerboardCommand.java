package controller.commands;

import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;
import model.LayeredImageProcessingModel;
import model.imagerepresentation.Image;
import model.programmaticimage.Checkerboard;


/**
 * Represents the command object to create a layer that is a Checkerboard.
 */
public class CheckerboardCommand extends MoreInputCommand {

  /**
   * Constructs this command object with the given scanner that contains more input. Checks to see
   * whether scanner has more input and sets nextIn to that input.
   *
   * @param input the scanner with more input
   * @throws IllegalArgumentException if there isn't enough input in the scanner.
   */
  public CheckerboardCommand(Scanner input) throws IllegalArgumentException {
    super(input);
  }

  @Override
  public void execute(LayeredImageProcessingModel model)
      throws IllegalArgumentException, IOException {
    String color1 = input.next();
    String color2 = input.next();

    Image cb = new Checkerboard(Integer.parseInt(nextIn), this.getColor(color1),
        this.getColor(color2)).create();
    model.upload(cb);
  }


  // gets the color from the specified color name
  private Color getColor(String colorName) {

    if ("blue".equals(colorName)) {
      return Color.BLUE;
    }
    if ("black".equals(colorName)) {
      return Color.black;
    }
    if ("green".equals(colorName)) {
      return Color.GREEN;
    }
    if ("grey".equals(colorName)) {
      return Color.GRAY;
    }
    if ("yellow".equals(colorName)) {
      return Color.YELLOW;
    }
    if ("light_grey".equals(colorName)) {
      return Color.LIGHT_GRAY;
    }
    if ("red".equals(colorName)) {
      return Color.RED;
    }

    throw new IllegalArgumentException("Unsupported color");
  }
}

