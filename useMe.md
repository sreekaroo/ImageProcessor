# GUI Use Me

Common mistakes:

- Must create a layer before any operations are possible.
- Must set a current layer before any effect or file operations are possible.
- the top most _visible_ layer is the image you see, not necessarily the current layer.

### Layer operations

- Create a layer button prompts for a layer name and creates the layer
- The layer log is updated after every layer operation, where each layer name is displayed in order
  from top to bottom. * represents invisible and ^ represents current.
- toggle visibility with toggle layer button
- set current layer with set current layer button
- remove current layer with remove current layer button

### Effects

- all effects are applied to current layer
- blur, sharpen, monochrome, and sepia apply the manipulation to current layer
- mosaic prompts for input. The number of seeds specified correspond to the number of points to "
  break down" image.

### File Operations

- Open a file will upload the chosen image file to current layer. The file chosen must be a JPG,
  PPM, or PNG file.
- Save as file will save the topmost visible layer's image to a file, with the name you provide.
  Must be a JPG, PPM, or PNG image file.
- Export a project will export the current project to the directory name of your choosing.
- Import a project will allow you to select a directory that represents a multilayered project and
  reset the entire state of the current project to the one specified
- Read from script file allows you to choose a .txt file that contains supported script commands.

### Programmatic Images

- Checkerboard prompts for length of square checkerboard in pixels, then the color for the first
  tile, then the second color. Finally, it uploads the created checkerboard image to the current
  layer.

### quit

- exits the program entirely

