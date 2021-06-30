package controller.fileinterpreting;

import java.io.IOException;

/**
 * Represents an interface that interprets image files types, by allowing the conversion into wanted
 * image representation or exporting imgae representation to file.
 *
 * @param <K> an image representation.
 */
public interface ImageFileInterpreter<K> {

  /**
   * Converts the given file into an image representation.
   *
   * @param fileName the pathname of the image file to be converted.
   * @return the file as a different image representation.
   * @throws IOException              when reading in from file is invalid.
   * @throws IllegalArgumentException when file extension is not supported.
   */
  K convert(String fileName) throws IOException, IllegalArgumentException;


  /**

   * Exports the current image representation to a file on this system. The file format and type depends on the implementation of this interface.
   *
   * @param name    the wished name for the exported file, may specify location
   * @param content the image representation to be exported to a file.             .
   * @throws IOException when creating or writing to file is not possible.
   */
  void export(String name, K content) throws IOException;
}
