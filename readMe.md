# ImageProcessing Project Design

By Srikar Ananthoju and Sarah Zhang

An application to process images, similar to photoshop or Gimp.

## The Model Design

### Related Model Interfaces :

`ImageProcessingModelState`
The highest level interface for the model, that represents the minimal functionality that an image
processing application should have. This functionality includes, uploading and exporting images,
saving and undoing operations, generating programmatic images, and some observer methods -- like
getting the pixel at a location, getting height of image, and getting width of image.

`ImageProcessingModel` The specific interface to support some basic image manipulation operations,
which are currently filtering by blurring or sharpening, and color transforming by monochrome and
sepia, which also supports the higher level interface operations.

### Model Implementation :

`SimpleImageProcessingModel` Our implementation of the model interface.

#### Image Representation:

Representation supports 8 bit RGB channels. `Image` class represents images as a list of list of
pixels. `Pixel` represents a single pixel in an image, with RGB channels and a position in the
image.`Channel` enum represents teh RGB channels.

##### Image Operations :

Supports filtering and color manipulation, using `Kernel` as an invariant class for matrices.

#### Programmatic Images :

`ProgrammaticImage` interface creates objects of specific supported programmatic images. Model
generates different Images at runtime based on ProgrammaticImage object passed in to `createImage`
method.

#### Interpreting files :

The `ImageFileInterpreter`interface represents objects that interpret specific file types, through
uploading and exporting. Each implementation of this interface supports a different file type. The
model identifies the file type wished to be exported, enumerated by `FileType`, and through
the `ImageFileInterpreterFactory`, generates the corresponding ImageFileInterpreter
object. `OurImageUtil` provides the utility methods to read in PPM files and identify file
extension. Recently added more `ImageFileInterpter` classes to support JPEG and PNG conversions.

### Resources :

Birds.ppm and Cherries.ppm are images from the commercial use image site
Pixabay [https://pixabay.com/]

[]: https://pixabay.com/

## Changes:

## The New Controller and View

The `GUIController` and its corresponding implementation `GraphicalController` manage the input and
timing of the new View and relate data from model to view. The controller consists
of `MultiLayerdFeatures` methods which are high level features that an interactive image processing
application must support. The GUI exposes all the features of the program as menus with buttons, for
the most part. In order to properly read in script files, the image file paths mentioned in the
script file must match up correctly to placement of jar.

### Extra Credit related changed:

A new image processing model was born, known as the `PremiumProcessingModel`, only for premium
users. Premium users have access to a mosaic operation. The new interface also has its own
implementation. The premium users also can downscale images by inputting the specified new size of
image they want

## Jar file :

Jar file must be in res folder. Script2 can only be run after Script1 because it needs the files
created by Script1. Script3 highlights our controllers ability to handle. faulty input. Jar can be
run via command line: navigate to res directory and then type java -jar ImageProcessing.jar,
followed by either -interactive( for GUI) , -script(and then the path for a .txt file with scripting
commands) , or -text for interactive scripting.

