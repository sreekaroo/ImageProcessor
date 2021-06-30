package controller;

import java.io.IOException;

/**
 * Represents the controller of a script based layered image processing application, controlling the sequence of
 * the program and input handling.
 */
public interface LayeredImageScriptController {

  /**
   * Starts the application, reading and executing input scripts line by line. Each line represents
   * its own command. beginning a line with 'q' or "Q" as a single word will kill the application.
   */
  void start() throws IOException;

}
