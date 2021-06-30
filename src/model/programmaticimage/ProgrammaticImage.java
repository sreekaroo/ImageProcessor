package model.programmaticimage;

import model.imagerepresentation.Image;

/**
 * interface for different programmtic image types.
 */
public interface ProgrammaticImage {

  /**
   * creates an programmatic image depending on type.
   *
   * @return the image created.
   */
  Image create();

}
