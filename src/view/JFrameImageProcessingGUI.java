package view;


import controller.MultiLayeredFeatures;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * to represent the graphical view of the Image Processor using java swing components.
 */
public class JFrameImageProcessingGUI extends JFrame implements GraphicalView {


  private JLabel imgLabel;

  private JPanel filePanel;
  private JPanel checkerboardPanel;
  private final JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton importProjectButton;
  private JButton exportProjectButton;
  private JButton scriptButton;

  private JPanel effectsPanel;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton sepiaButton;
  private JButton monochromeButton;
  private JButton mosaicButton;
  private JButton downscaleButton;

  private JButton createLayerButton;

  private JButton setCurrentButton;
  private JButton toggleButton;
  private JButton removeLayerButton;


  private JButton createImageButton;
  private JLabel colorChooserDisplay;


  private JLabel layerLogDisplay;

  private JButton quitButton;


  /**
   * to represent the graphical view of the Image Processor using java swing components.
   */
  public JFrameImageProcessingGUI() {
    super();
    JPanel mainPanel;
    JScrollPane mainScrollPane;
    JPanel imagePanel;
    JPanel layerAndEffectsPanel;
    ImageIcon layerImg;
    JScrollPane imageScrollPane;
    JPanel layerOperationPanel;
    JPanel leftPanel;
    JLabel filePathDisplay;
    JLabel saveImageDisplay;

    this.setTitle("Multilayered Image Processor");
    setSize(500, 500);
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(100, 100));

    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    // filePanel.add(fileOpenButton);
    filePathDisplay = new JLabel("File path will appear here");

    importProjectButton = new JButton("Import Project");
    exportProjectButton = new JButton("Export Project");

    //file saving
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    fileSaveButton = new JButton("Save as file");
    fileSaveButton.setActionCommand("Save file");
    saveImageDisplay = new JLabel("File path will appear here");
    // reading in script commands support
    scriptButton = new JButton("Read Script File");

    // MENU FOR ALL THE FILE STUFF
    JMenuBar menuBarOptions;
    menuBarOptions = new JMenuBar();
    menuBarOptions.setLayout(new GridLayout(0, 1));
    menuBarOptions.add(fileOpenButton);
    menuBarOptions.add(importProjectButton);
    menuBarOptions.add(exportProjectButton);
    menuBarOptions.add(fileSaveButton);
    menuBarOptions.add(scriptButton);
    menuBarOptions.setBorder(BorderFactory.createTitledBorder("File Options"));

    leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.add(menuBarOptions, BorderLayout.NORTH);

    mainPanel.add(filePathDisplay, BorderLayout.PAGE_END);

    // Checkerboard stuff
    JMenuBar checkerboardMenu;
    checkerboardMenu = new JMenuBar();
    checkerboardMenu.setLayout(new GridLayout(0, 1));
    createImageButton = new JButton("Create checkerboard");
    colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true); //so that background color shows up
    colorChooserDisplay.setBackground(Color.WHITE);
    checkerboardMenu.add(createImageButton);
    checkerboardMenu.add(colorChooserDisplay);
    checkerboardMenu.setBorder(BorderFactory.createTitledBorder("Checkerboard Options"));

    leftPanel.add(checkerboardMenu, BorderLayout.NORTH);
    mainPanel.add(leftPanel, BorderLayout.LINE_START);

    //show an image with a scrollbar
    imagePanel = new JPanel();
    layerImg = new ImageIcon();
    mainPanel.add(imagePanel, BorderLayout.CENTER);
    imgLabel = new JLabel(layerImg, JLabel.CENTER);
    JScrollPane imgScrollPane = new JScrollPane(imgLabel);
    imagePanel.setPreferredSize(new Dimension(700, 700));

    imagePanel.add(imgScrollPane);
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    imagePanel.setLayout(new GridLayout(1, 0, 100, 100));
    // keeping track of the layer image to display

    imgLabel.setIcon(layerImg);

    // scrollable image pane for layer image
    imageScrollPane = new JScrollPane();

    // layer operations

    layerAndEffectsPanel = new JPanel();
    layerAndEffectsPanel.setLayout(new BoxLayout(layerAndEffectsPanel, BoxLayout.Y_AXIS));
    layerOperationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    createLayerButton = new JButton("Create layer");
    createLayerButton.setActionCommand("create");

    toggleButton = new JButton("Toggle layer");
    toggleButton.setActionCommand("toggle");
    //   layerOperationPanel.add(toggleButton);

    setCurrentButton = new JButton("Set current layer");
    setCurrentButton.setActionCommand("current");
    layerOperationPanel.add(setCurrentButton);
    //  layerOperationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    removeLayerButton = new JButton("Remove current layer");
    removeLayerButton.setActionCommand("remove");
    //   layerOperationPanel.add(removeLayerButton);
    // keeping track of input for layer names
    //   layerInput = new JTextField(10);
    //  layerOperationPanel.add(layerInput);

    JMenuBar menuBarLayer;
    menuBarLayer = new JMenuBar();
    menuBarLayer.setLayout(new GridLayout(0, 1));
    menuBarLayer.add(createLayerButton);
    menuBarLayer.add(setCurrentButton);
    menuBarLayer.add(removeLayerButton);
    menuBarLayer.add(toggleButton);
    //  menuBarLayer.add(layerInput);
    menuBarLayer.setBorder(BorderFactory.createTitledBorder("Layer Operations"));
    layerAndEffectsPanel.add(menuBarLayer);

    JMenuBar menuBarLayersList;
    menuBarLayersList = new JMenuBar();
    menuBarLayersList.setLayout(new GridLayout(0, 1));
    menuBarLayersList.setBorder(BorderFactory.createTitledBorder("<html>"
        + "Layer Log \n current = ^, invisible = *".replaceAll(
        "<", "&lt;").replaceAll(
        ">", "&gt;").replaceAll(
        "\n", "<br/>") + "</html>"));

    layerAndEffectsPanel.add(menuBarLayersList);

    // image manipulation

    // mainPanel.add(effectsPanel);

    JMenuBar menuBarEffects;
    menuBarEffects = new JMenuBar();
    menuBarEffects.setLayout(new GridLayout(0, 1));
    blurButton = new JButton("blur");
    blurButton.setActionCommand("blur");
    blurButton.setBounds(20, 30, 50, 30);
    //  effectsPanel.add(blurButton);
    sepiaButton = new JButton("sepia");
    sepiaButton.setActionCommand("sepia");
    sepiaButton.setPreferredSize(new Dimension(50, 50));
    //  effectsPanel.add(sepiaButton);
    sharpenButton = new JButton("sharpen");
    sharpenButton.setActionCommand("sharpen");
    sharpenButton.setPreferredSize(new Dimension(50, 50));
    //  effectsPanel.add(sharpenButton);
    monochromeButton = new JButton("monochrome");
    monochromeButton.setActionCommand("monochrome");
    monochromeButton.setPreferredSize(new Dimension(50, 50));
    mosaicButton = new JButton("mosaic");
    downscaleButton = new JButton("downscale");

    menuBarEffects.add(blurButton);
    menuBarEffects.add(sharpenButton);
    menuBarEffects.add(monochromeButton);
    menuBarEffects.add(sepiaButton);
    menuBarEffects.add(mosaicButton);
    menuBarEffects.add(downscaleButton);
    menuBarEffects.setBorder(BorderFactory.createTitledBorder("Effects"));
    layerAndEffectsPanel.add(menuBarEffects);

    // Layer log
    layerLogDisplay = new JLabel();
    JScrollPane layerLogScroller = new JScrollPane(layerLogDisplay,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    layerLogDisplay.setVerticalAlignment(JLabel.TOP);
    // layerLogDisplay.setPreferredSize((new Dimension(20, 10)));
    menuBarLayersList.add(layerLogScroller);

    mainPanel.add(layerAndEffectsPanel, BorderLayout.LINE_END);

    // Quitting
    quitButton = new JButton("Quit");
    quitButton.setForeground(Color.RED);
    layerAndEffectsPanel.add(quitButton);
    this.pack();
    this.setVisible(true);

  }


  @Override
  public void addFeatures(MultiLayeredFeatures multiLayeredFeatures) {

    // layer operation buttons
    createLayerButton
        .addActionListener(evt -> multiLayeredFeatures.createLayer(
            this.getInput("Please enter a layer name")));
    setCurrentButton
        .addActionListener(evt -> multiLayeredFeatures.setCurrent(this.getInput("Enter layer"
            + " to set as current")));
    toggleButton.addActionListener(evt -> multiLayeredFeatures.toggle(this.getInput(
        "Enter layer to toggle visibility")));
    removeLayerButton.addActionListener(evt -> multiLayeredFeatures.remove());

    // file opening and loading in
    fileOpenButton.addActionListener(evt -> multiLayeredFeatures.load());
    fileSaveButton.addActionListener(evt -> multiLayeredFeatures.save());

    // importing and exporting project
    importProjectButton.addActionListener(evt -> multiLayeredFeatures.importProject());
    exportProjectButton.addActionListener(evt -> multiLayeredFeatures.exportProject());

    // reading script button
    scriptButton.addActionListener(evt -> multiLayeredFeatures.readScript());

    // quitting
    quitButton.addActionListener(evt -> multiLayeredFeatures.quit());

    // effects features
    blurButton.addActionListener(evt -> multiLayeredFeatures.blur());
    sharpenButton.addActionListener(evt -> multiLayeredFeatures.sharpen());
    sepiaButton.addActionListener(evt -> multiLayeredFeatures.sepia());
    monochromeButton.addActionListener(evt -> multiLayeredFeatures.monochrome());
    mosaicButton.addActionListener(evt -> multiLayeredFeatures.mosaic());
    downscaleButton.addActionListener(evt -> multiLayeredFeatures.downscale());

    // checkerboard
    createImageButton.addActionListener(evt -> multiLayeredFeatures.createCheckerboard());
  }


  @Override
  public void renderImage(BufferedImage img) {
    imgLabel.setIcon(new ImageIcon(img));
  }

  @Override
  public void resetImage() {
    imgLabel.setIcon(new ImageIcon());
  }


  @Override
  public void updateLayerLog(String newLayerName) {

    layerLogDisplay.setText("<html>" + newLayerName.replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(JFrameImageProcessingGUI.this, message, "Message",
        JOptionPane.PLAIN_MESSAGE);

  }

  @Override
  public String openFileManager() {
    final JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Supported files", "jpg", "png", "ppm", "txt");
    fileChooser.setFileFilter(filter);
    int retvalue = fileChooser.showOpenDialog(JFrameImageProcessingGUI.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      // filePathDisplay.setText(f.getAbsolutePath());
      return f.getAbsolutePath();
    }

    // no file path because quit out early
    return null;
  }

  @Override
  public String openFolderManager() {
    final JFileChooser folderChooser = new JFileChooser(".");
    folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retvalue = folderChooser.showOpenDialog(JFrameImageProcessingGUI.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = folderChooser.getSelectedFile();
      // filePathDisplay.setText(f.getAbsolutePath());
      return f.getAbsolutePath();
    }

    // no file path because quit out early
    return null;
  }

  @Override
  public String saveFileManager() {
    final JFileChooser fileChooser = new JFileChooser(".");
    int retvalue = fileChooser.showSaveDialog(JFrameImageProcessingGUI.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      //filePathDisplay.setText(f.getAbsolutePath());
      return f.getAbsolutePath();
    }

    // no file path because quit out early
    return null;


  }

  @Override
  public String getInput(String prompt) {
    return JOptionPane.showInputDialog(prompt);
  }

  @Override
  public Color getColor() {

    Color col = JColorChooser
        .showDialog(JFrameImageProcessingGUI.this, "Choose a color",
            colorChooserDisplay.getBackground());

    return col;
  }
}
