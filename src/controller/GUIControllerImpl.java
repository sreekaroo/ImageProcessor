package controller;

import controller.commands.CreateLayerCommand;
import controller.commands.ExportCommand;
import controller.commands.ImportCommand;
import controller.commands.LayerScriptCommand;
import controller.commands.LoadLayerCommand;
import controller.commands.SetCurrentCommand;
import controller.commands.ToggleCommand;
import controller.fileinterpreting.ImageFileInterpreter;
import controller.fileinterpreting.ImageFileInterpreterFactory;
import controller.fileinterpreting.ImageFileInterpreterFactory.FileType;
import controller.fileinterpreting.OurImageUtil;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import model.PremiumProcessingModel;
import model.imagerepresentation.Image;
import model.programmaticimage.Checkerboard;
import view.GraphicalView;

/**
 * to represent the controller for the graphical view and model.
 */
public class GUIControllerImpl implements GUIController, MultiLayeredFeatures {

  private final PremiumProcessingModel model;
  private GraphicalView view;

  /**
   * Constructs this  controller with provided arguments.
   *
   * @param model the layered image model to delegate operations to.
   * @throws IllegalArgumentException if any arguments are null.
   */
  public GUIControllerImpl(PremiumProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Null argument");
    }
    this.model = model;
  }


  @Override
  public void setView(GraphicalView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("Null view");
    }

    this.view = view;
    view.addFeatures(this);
  }


  @Override
  public void createLayer(String name) {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    if (name != null && name.length() > 0) {
      layerOp(new CreateLayerCommand(new Scanner(name)));
    } else {
      view.renderMessage("Invalid name, try again");
    }
  }

  @Override
  public void load() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    String filePath = view.openFileManager();
    if (filePath == null) {
      return;
    }

    try {
      new LoadLayerCommand(new Scanner(filePath)).execute(model);
      updateTopImage();
    } catch (IllegalArgumentException | IOException e) {
      view.renderMessage(e.getMessage());
    }

  }

  @Override
  public void save() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    String filePath = view.saveFileManager();
    if (filePath == null) {
      return;
    }
    try {

      FileType type = OurImageUtil.findFileExtension(filePath);
      ImageFileInterpreter<Image> interpreter =
          ImageFileInterpreterFactory.create(type);
      interpreter.export(filePath, model.export());
      updateTopImage();
    } catch (IllegalArgumentException | IllegalStateException | IOException e) {
      view.renderMessage(e.getMessage());
    }

  }

  @Override
  public void importProject() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    String filePath = view.openFolderManager();
    if (filePath == null) {
      return;
    }

    try {
      new ImportCommand(new Scanner(filePath)).execute(model);
      updateTopImage();
      view.updateLayerLog(model.toString());
    } catch (IllegalArgumentException | IOException e) {
      view.renderMessage(e.getMessage());
    }

  }

  @Override
  public void exportProject() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    String filePath = view.saveFileManager();
    if (filePath == null) {
      return;
    }
    // auto exports as png
    try {
      new ExportCommand(new Scanner(filePath + " " + FileType.PNG.name())).execute(model);
    } catch (IllegalArgumentException | IllegalStateException | IOException e) {
      view.renderMessage(e.getMessage());
    }
  }

  @Override
  public void readScript() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    String filePath = view.openFileManager();

    if (filePath == null) {
      return;
    }
    try {
      // reading from a script, mutating the model
      InputStream script = new FileInputStream(filePath);
      LayeredImageScriptController delegate = new SimpleScriptController(model,
          new InputStreamReader(script), this.view);
      delegate.start();
      // updating top image after reading from script
      this.updateTopImage();
      view.updateLayerLog(model.toString());
    } catch (FileNotFoundException e) {
      view.renderMessage(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void setCurrent(String layer) {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    try {
      layerOp(new SetCurrentCommand(new Scanner(layer)));
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage());
    }

  }

  @Override
  public void toggle(String layer) {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    try {
      layerOp(new ToggleCommand(new Scanner(layer)));
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage());
    }
    updateTopImage();

  }

  @Override
  public void remove() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    LayerScriptCommand remove = (m -> m.remove());
    layerOp(remove);
    updateTopImage();
  }

  @Override
  public void blur() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    LayerScriptCommand cmd = (m -> m.blur());
    affectImage(cmd);
  }


  @Override
  public void sharpen() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    LayerScriptCommand cmd = (m -> m.sharpen());
    affectImage(cmd);

  }

  @Override
  public void sepia() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    LayerScriptCommand cmd = (m -> m.sepia());
    affectImage(cmd);
  }

  @Override
  public void monochrome() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    LayerScriptCommand cmd = (m -> m.monochrome());
    affectImage(cmd);

  }

  @Override
  public void mosaic() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    try {
      String seedNumber = view.getInput("Enter number of seeds for mosaic operation");
      model.mosaic(Integer.parseInt(seedNumber), 0);
      this.updateTopImage();
    } catch (NumberFormatException e) {
      view.renderMessage("Not a valid number");
    }

  }

  @Override
  public void downscale() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    try {
      String widthInput = view.getInput("Enter width");
      String heightInput = view.getInput("Enter height");
      model.downscale(Integer.parseInt(widthInput), Integer.parseInt(heightInput));
      this.updateTopImage();
    } catch (NumberFormatException e) {
      view.renderMessage("Not a valid number");
    }

  }

  @Override
  public void quit() {
    System.exit(0);
  }

  /**
   * creates a checkerboard based on input of the numebr of tiles, color 1 and color 2.
   */
  public void createCheckerboard() {
    if (view == null) {
      throw new IllegalStateException("View hasn't been set yet.");
    }

    try {
      String tileSizeStr = view.getInput("Please enter number of tiles");
      Color col1 = view.getColor();
      Color col2 = view.getColor();
      int tileSizeNum = Integer.parseInt(tileSizeStr);
      // uploading created checkerboard into current layer
      Image checkerboard = model.createImage(new Checkerboard(tileSizeNum, col1, col2));
      model.upload(checkerboard);
      this.updateTopImage();
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage());
    } catch (IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }

  }


  // executes commands associated with layer operations
  private void layerOp(LayerScriptCommand cmd) {
    try {
      cmd.execute(model);
      view.updateLayerLog(model.toString());
    } catch (IllegalArgumentException | IllegalStateException | IOException e) {
      view.renderMessage(e.getMessage());
    }
  }


  // rerendering the topmost visible layer
  private void updateTopImage() {
    try {
      BufferedImage topImage = OurImageUtil.convertToBufferedImage(model.export());
      view.renderImage(topImage);
    } catch (IllegalStateException e) {
      view.resetImage();
      // ignoring when model doesnt have top most image
    }

  }

  // affects the image in a image processing applicatoin with the given command
  private void affectImage(LayerScriptCommand cmd) {
    try {
      cmd.execute(model);
      updateTopImage();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }
  }

}
