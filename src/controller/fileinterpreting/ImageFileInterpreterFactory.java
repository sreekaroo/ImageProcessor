package controller.fileinterpreting;

import model.imagerepresentation.Image;

/**
 * Represents a factory for producing ImageFileInterpreters.
 */
public class ImageFileInterpreterFactory {

  /**
   * Represents supported file types for image representation.
   */
  public enum FileType {

    // the support file types and their extensions
    // adding support for more file types
    PPM("ppm"), PNG("png"), JPEG("jpg");

    private final String extension;

    FileType(String extension) {
      this.extension = extension;
    }

    public String toString() {
      return this.extension;
    }
  }


  /**
   * Creates an interpreter object depending on the requested file type with the given image.
   *
   * @param type the requested file type
   * @return the interpreter object.
   */
  public static ImageFileInterpreter<Image> create(FileType type) {

    switch (type) {
      case PPM:
        return new PPMInterpreter();
      // adding support for more filetypes
      case PNG:
        return new PNGInterpreter();
      case JPEG:
        return new JPEGInterpreter();
      default:
        throw new IllegalArgumentException("Invalid type");
    }
  }

}
