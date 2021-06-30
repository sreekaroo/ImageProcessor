import controller.GUIController;
import controller.GUIControllerImpl;
import controller.LayeredImageScriptController;
import controller.SimpleScriptController;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.PremiumProcessingModel;
import model.UpgradedProcessingModel;
import view.GraphicalView;
import view.ImageProcessingTextView;
import view.ImageProcessingView;
import view.JFrameImageProcessingGUI;

/**
 * The entry point of the Image Processor.
 */
public class Main {

  /**
   * Constructor for the main class: the entry point of the Image Processor.
   *
   * @param args a String.
   * @throws IOException when appendable is null.
   */
  public static void main(String[] args) throws IOException {

    // figuring out the input source
    Appendable destination = System.out;
    InputStream input = System.in;

    if (args.length < 1) {
      destination.append("No command specified");
      System.exit(0);
    }

    String cmd = args[0];
    boolean wantsGUI = false;

    // command line jar file commands
    switch (cmd) {
      case "-text":
        input = System.in;
        break;
      case "-script":
        if (args.length < 2) {
          destination.append("Missing filepath after script");
          System.exit(0);
        }
        input = new FileInputStream(args[1]);
        destination.append("\n");
        break;
      case "-interactive":
        wantsGUI = true;
        break;
      default:
        destination.append("invalid choice, quitting program");
        System.exit(0);
    }

    PremiumProcessingModel model = new UpgradedProcessingModel();
    if (wantsGUI) {
      // view and controller for GUI
      GraphicalView view = new JFrameImageProcessingGUI();
      GUIController controller = new GUIControllerImpl(model);
      controller.setView(view);
    } else {
      // view and controller for scripting
      ImageProcessingView view = new ImageProcessingTextView(destination);
      // creating controller with desired input source
      LayeredImageScriptController controller = new SimpleScriptController(model,
          new InputStreamReader(input), view);
      controller.start();
    }

  }
}
