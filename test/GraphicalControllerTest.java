import static org.junit.Assert.assertEquals;

import controller.GUIController;
import controller.GUIControllerImpl;
import model.PremiumProcessingModel;
import model.UpgradedProcessingModel;
import org.junit.Before;
import org.junit.Test;
import view.GraphicalView;

/**
 * Tests and examples for graphical controller.
 */
public class GraphicalControllerTest {

  PremiumProcessingModel model;
  PremiumProcessingModel mockModel;

  GraphicalView mockView;

  GUIController controller;

  @Before
  public void setUp() throws Exception {
    model = new UpgradedProcessingModel();
  }

  // testing create layer method delegates to correct classes and methods
  @Test
  public void createLayer() {
    StringBuilder actualModelOut = new StringBuilder();
    StringBuilder actualViewOut = new StringBuilder();

    mockModel = new MockComplex(actualModelOut);
    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(mockModel);

    controller.setView(mockView);

    controller.createLayer("Birds");
    assertEquals("created Birds", actualModelOut.toString());
    assertEquals("addFeatures\n"
        + "updateLayerLog\n", actualViewOut.toString());

  }

  // testing exception handling
  @Test
  public void createLayerInvalid() {
    StringBuilder actualViewOut = new StringBuilder();

    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(model);

    controller.setView(mockView);

    controller.createLayer("Birds");
    controller.createLayer("Birds");
    assertEquals("addFeatures\n"
        + "updateLayerLog\n"
        + "renderMessage\n", actualViewOut.toString());

  }


  @Test
  public void load() {
    StringBuilder actualModelOut = new StringBuilder();
    StringBuilder actualViewOut = new StringBuilder();

    mockModel = new MockComplex(actualModelOut);
    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(mockModel);

    controller.setView(mockView);

    controller.load();
    assertEquals("addFeatures\n"
        + "openFileManager\n", actualViewOut.toString());

  }

  @Test
  public void save() {
    StringBuilder actualModelOut = new StringBuilder();
    StringBuilder actualViewOut = new StringBuilder();

    mockModel = new MockComplex(actualModelOut);
    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(mockModel);

    controller.setView(mockView);

    controller.save();
    assertEquals("addFeatures\n"
        + "saveFileManager\n", actualViewOut.toString());

  }

  @Test
  public void importProject() {
    StringBuilder actualModelOut = new StringBuilder();
    StringBuilder actualViewOut = new StringBuilder();

    mockModel = new MockComplex(actualModelOut);
    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(mockModel);

    controller.setView(mockView);

    controller.importProject();
    assertEquals("addFeatures\n"
        + "openFolderManager\n", actualViewOut.toString());

  }

  @Test
  public void exportProject() {
    StringBuilder actualModelOut = new StringBuilder();
    StringBuilder actualViewOut = new StringBuilder();

    mockModel = new MockComplex(actualModelOut);
    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(mockModel);

    controller.setView(mockView);

    controller.exportProject();
    assertEquals("addFeatures\n"
        + "saveFileManager\n", actualViewOut.toString());

  }

  @Test
  public void readScript() {
    StringBuilder actualModelOut = new StringBuilder();
    StringBuilder actualViewOut = new StringBuilder();

    mockModel = new MockComplex(actualModelOut);
    mockView = new MockGUIView(actualViewOut);

    controller = new GUIControllerImpl(mockModel);

    controller.setView(mockView);

    controller.readScript();
    assertEquals("addFeatures\n"
        + "openFileManager\n", actualViewOut.toString());

  }
}