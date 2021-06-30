package controller;

import view.GraphicalView;

/**
 * Represents the controller of a layered image processing application with a graphical view.
 */
public interface GUIController extends MultiLayeredFeatures {

  /**
   * Sets the view of this controller, starting the application's visual aspect.
   *
   * @param view the graphical view.
   * @throws IllegalArgumentException if view is null
   */
  void setView(GraphicalView view) throws IllegalArgumentException;
}
