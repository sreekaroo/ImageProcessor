import static org.junit.Assert.assertEquals;

import controller.LayeredImageScriptController;
import controller.SimpleScriptController;
import java.io.IOException;
import java.io.StringReader;
import model.ComplexProcessingModel;
import model.LayeredImageProcessingModel;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Tests for SimpleScripController class.
 */
public class SimpleScriptControllerTest {

  Appendable out;
  Readable in;
  ImageProcessingView view;
  LayeredImageProcessingModel mock;

  LayeredImageProcessingModel model;
  LayeredImageScriptController controller;


  @Before
  public void setUp() {
    out = new StringBuilder();
    view = new ImageProcessingTextView(out);
    model = new ComplexProcessingModel();


  }

  // test constructor throws exception
  @Test(expected = IllegalArgumentException.class)
  public void testControllerException() throws IOException {
    controller = new SimpleScriptController(null, in, view);
    this.controller.start();
  }


  // test controller reads line by line
  @Test
  public void testReadLineByLine() throws IOException {
    in = new StringReader("create Cherries" + "\n" + "set Cherries" + "\n" + "blur");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Cherries^ ", model.toString());
  }


  // test q quits if begins a line
  @Test
  public void testQuitting() throws IOException {
    in = new StringReader("q");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("Program quit.");

    controller = new SimpleScriptController(model, in, view);
    controller.start();

    assertEquals(expectedOut.toString(), out.toString());
  }

  // test Q quits if begins a line
  @Test
  public void testQuitWithQUpper() throws IOException {
    in = new StringReader("Q");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("Program quit.");

    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals(expectedOut.toString(), out.toString());
  }

  // test unsupported command
  @Test
  public void testUnsupportedCommand() throws IOException {
    in = new StringReader("saturate");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again." + "\n");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals(expectedOut.toString(), out.toString());
  }

  // test create command for valid input
  @Test
  public void testCreateLayerValidInput() throws IOException {
    in = new StringReader("create Cherries");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("Cherries^ ", model.toString());
  }

  // test create command for too short input renders correct error message and allows for retry
  @Test
  public void testTooShortInput() throws IOException {
    in = new StringReader("create");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("Not enough input for script line. try command again" + "\n");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals(expectedOut.toString(), out.toString());
  }


  // test create command for layer name that already exists
  @Test
  public void testLayerExists() throws IOException {
    in = new StringReader("create Cherries current Cherries create Cherries");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");

    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());

  }


  // test current command for valid input
  @Test
  public void testValidInput() throws IOException {
    in = new StringReader("create Birds current Birds blur");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Birds^ ", model.toString());
  }


  // test current command for short input renders correct error message and allows for retry
  @Test
  public void testCurrentShortInput() throws IOException {
    in = new StringReader("create");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Not enough input for script line. try command again" + "\n",
        out.toString());
  }


  //TODO
  // test current command for layer name that doesnt exist
  @Test
  public void testCurrentLayerNonexistent() throws IOException {
    in = new StringReader("create Cherries current Birds");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());

  }

  // test load command calls the upload file method with indicated file name for valid input
  @Test
  public void testUploadFile() throws IOException {
    in = new StringReader("create Birds current birds upload res/Birds.ppm");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Birds^ ", model.toString());
  }

  // test load command for short input renders correct error message and allows for retry
  @Test
  public void testLoadShortInput() throws IOException {
    in = new StringReader("create Cherries load ");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());
  }

  // test IO exception thrown by controller when rendering to view is impossible
  @Test(expected = IOException.class)
  public void testLoadIOException() throws IOException {
    in = new StringReader("create Cherries "
        + "wrong");
    Appendable mockAppendable = new AppendableMock();
    ImageProcessingView fakeView = new ImageProcessingTextView(mockAppendable);
    controller = new SimpleScriptController(model, in, fakeView);
    this.controller.start();

  }

  //TODO
  // test save command calls the export as method with indicated file name for valid input
  @Test
  public void testSaveCallsExport() throws IOException {
    in = new StringReader(
        "create Cherries "
            + "current Cherries "
            + "load res/Cherries.ppm"
            + " blur"
            + " save res/CherriesBlur.ppm PPM");

    Appendable actualOut = new StringBuilder();
    mock = new MockComplex(actualOut);
    controller = new SimpleScriptController(mock, in, view);
    this.controller.start();
    assertEquals(
        "created CherriesSetCherries as current Upload was called Blur was"
            + " called Image was exported",
        actualOut.toString());
  }

  // test save command for short input renders correct error message and allows for retry
  @Test
  public void testSaveShortInput() throws IOException {
    in = new StringReader("save");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Not enough input for script line. try command again\n", out.toString());
  }


  // test toggle for correct input calls toggle method
  @Test
  public void testToggle() throws IOException {
    in = new StringReader("create Cherries current Cherries toggle");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Cherries^ ", model.toString());

  }

  // test export for correct input calls export all method with correct argument
  @Test
  public void testExportCorrectInput() throws IOException {
    in = new StringReader("create Cherries current Cherries export res/CherriesProject PPM ");
    Appendable actualOutput = new StringBuilder();
    mock = new MockComplex(actualOutput);
    controller = new SimpleScriptController(mock, in, view);
    this.controller.start();
    assertEquals("created CherriesSetCherries as current Exported all layers ",
        actualOutput.toString());
  }

  // test export for invalid file type allows for retry and indicates error
  @Test
  public void testExportInvalidFileType() throws IOException {
    in = new StringReader("create Cherries"
        + " current Cherries "
        + "export res/CherriesProject txt q");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Too much input for this script line try command again\n"
        + "Unsupported file type try command again\n"
        + "Program quit.", out.toString());

  }


  // test import for correct input calls upload multi layer method with correct arguments
  @Test
  public void testImportCorrectInput() throws IOException {
    in = new StringReader("create Cherries"
        + " current Cherries"
        + " create Birds "
        + " export res/Project1 PPM"
        + " import res/Project1");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("", model.toString());

  }


  // test remove command calls remove method on valid input
  @Test
  public void testRemoveValid() throws IOException {
    in = new StringReader("create Cherries current Cherries remove");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("", model.toString());
  }


  // test remove command for no current layer writes error message and allows for retry
  @Test
  public void testRemoveNoCurrent() throws IOException {
    in = new StringReader("create Cherries remove");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());
  }


  // test blur command calls blur method on valid input
  @Test
  public void testBlurValid() throws IOException {
    in = new StringReader("create Birds current Birds blur");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("Birds^ ", model.toString());
  }


  // test blur command for no current layer writes error message and allows for retry
  @Test
  public void testBlurNoCurrentLayer() throws IOException {
    in = new StringReader("create Cherries blur");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    this.controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());

  }

  // test sharpen command calls sharpen method on valid input
  @Test
  public void testSharpenValid() throws IOException {
    in = new StringReader("create Cherries current Cherries sharpen");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("Cherries^ ", model.toString());
  }

  @Test
  public void testSharpenNoCurrent() throws IOException {
    in = new StringReader("create Cherries sharpen");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());
  }

  @Test
  public void testSepiaValid() throws IOException {
    in = new StringReader("create Birds current Birds sepia");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("Birds^ ", model.toString());
  }


  // test sepia command for no current layer writes error message and allows for retry
  @Test
  public void testSepiaNoCurrent() throws IOException {
    in = new StringReader("create Cherries"
        + " sepia");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());
  }

  //test monochrome command calls monochrome method on valid input
  @Test
  public void testMonochromeValid() throws IOException {
    in = new StringReader("create Cherries current Cherries monochrome");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("Cherries^ ", model.toString());
  }

  // test monochrome command for no current layer writes error message and allows for retry
  @Test
  public void testMonochromeNoCurrent() throws IOException {
    in = new StringReader("create Cherries monochrome");
    Appendable expectedOut = new StringBuilder();
    expectedOut.append("unsupported command, try again.");
    controller = new SimpleScriptController(model, in, view);
    controller.start();
    assertEquals("unsupported command, try again.", expectedOut.toString());

  }

}

