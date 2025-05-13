# ANDIE: A Non-Destructive Image Editor

### COSC202 - Team Octagon 

Jasmine Smith, Yuxin Luo, Jackson Stephens, Liam Bland, & Fergus Short

<b><b><b>

## Feature Contribution

**Jasmine:**
* ColourSwap
* ColourInvert
* OffsetConvolveOp - handling negative results / edge detection
* Sobel Filter
* Emboss Filter
* _Extra Feature_: Combined vertical and horizontal Sobel Filter option

**Yuxin:**
* ImageExport
* Sharpen Filter
* Exception Handling
* BrightAdjust
* ContrastAdjust
* Marcro actions

**Jackson:** 
* Rotate
* Resize
* Flip
* MouseActions - Crop, Draw, Selection

**Liam:** 
* Median Blur
* Gaussian Blur
* Block Average
* Random Scatter
* Extended Filters 

**Fergus:** 
* Language Actions
* Toolbar
* Shortcuts
  
*Other error preventions such as using JSpinner are done by everyone in their own code.*


<b><b><b>
## Code Testing

<b><b><b>
Median Blur, Mean blur, Gaussian Blur, Transform actions 
- Have no feasible way of fixing errors as when strings are entered the value automatically gets defaulted to the minimum value specified in the JSpinner. 

- Median Blur: Used sample image from the internet, produced same results. 

- Transform Actions: The image correctly rotated the given angle, grew by a given size and flipped. 

- Gaussian Blur: Unit tested kernel given from lab book, recieved same results. 

- Language options: Pressed the change language to italian option, reload Andie and the Andie is now in italian, and then able to change back to english with another reload.

- ToolBar: The ToolBar shows the Frequently used actions in a bar below the menu, I tested that the ToolBar will pop up and all of the actions on the toolbar work when clicked.

- Shortcuts: On any action which has a shortcut in it's description it has how to use the shortcut, I tested that all the shortcuts will work when pressed.

- Brightness and Contrast Adjust: Unit testing has been done to test the pixel values when increase and decreasing the brightness or contrast. 

- Macro actions: When the user press on the saved ops file that contains that actions recorded, the actions are applied to the image opened. Actions that's needed to be performed inorder: Start -> Stop -> Save -> Apply


<b><b><b>
Sharpen Blur, Soft Blur, Colour Actions
- Compared with sample image given from lab book. 

<b><b><b>
FileActions
- Tested for errors by saving, export image without opening images or entering file types. 
- Tested Open option by opening files that is not an image file. 

<b><b><b>
MouseActions
- Tested for correct crop area by comparison visually with a test image.
- Tested for correct drawing visually with a test image.

<b><b><b>
SobelFilter, EmbossFilter, OffsetConvolveOp
- Tested using a one-colour sample array by recording initial "pixel" values and then comparing it to the result after a convolution was applied (both with and without an offset) to ensure edges were correctly detected, embossing in the direction described, that kernels applied to all pixels within the image, and that the offsetting was appropriate.


## User Guide
ANDIE is a non-destructive image editor. The purpose of the programme is to edit and manipulate images that can be reversed wihtout losing information in the process. It stores the original data of an image and the sequence of operation applied to that image, meaning it can be reversed. 
Image type supported: .png, .jpeg, .jpg

In the menu bar:
- File button contains options to open an image, save an image, save an image as a file type and to export and image. 
<b><b><b>


*The action below can only be done after an image is opened*
<b><b><b>


**Edit**
- Undo: Reverse an operation

- Redo: Redo operation

**View**
- Zoom in: Zoom in the image

- Zoom out: Zoom out of the image

- Zoom full: Fully zoom out an image, go back to default size

**Filter**
- Mean Filter: Blurs the image by replacing each pixel by the the average of the pixels in a surrounding neighbourhood. 

- Soft Blur: Soft blur the image

- Sharpen Blur: Enhances the differences between neighbouring pixels to sharpen image.

- Gaussian Blur: Apply blurring effects similar to out-of-focus camera lense, with distribution determined by user input of the radius. 

- Median Blur: Takes median colour values of surrounding pixels, used for reducing noise, the radius is determing by user input. 

- Sobel Filter:
    Detects edges or boundaries within an image and maximises these areas for contrast. User determines the type of Sobel filter - Horizontal, Vertical, or Combined (both horizontal and vertical), and can apply an option offset which can enhance the filters effect.

- Emboss Filter:
    Applies an embossing effect to images, with the embossing direction determined by the user. An option offset can also be applied to enhance the filters effect.

- Block Average:
    Takes mean value of pixels inside user determined area, result is shaped like a bunch of blocks.

- Random Scatter:
    Takes random pixel values within a user determined radius.

- Extended Filters:
    Implemented new convolution operator to extend filters directly up to the edge of the image.

**Image**
- Resize: Resize the image to the percentage the user enters.

- Rotate: Rotate the image 90 degrees and 180 degrees.
 
- Flip: Flip the image horizontally or vertically


**Colour**
- Greyscale: Compresses an image to its minimum pixel. Appears grey in the human eye. 

- Invert Image: Creates a negative image with a flipped colour scheme. 

- Colour Swap: Swaps the colours of the image depending on the order the user selects. 

**Language** - as world symbol
- Language Options: Option to choose between English and Italian

**Mouse**
- Crop: Crops an image to a user specified size, cannot be larger than the image or else returns the user the defualt image size.
- Draw: Draws rectangles, ovals, and line on the image to a user specified size.

**Macro Action**
- The user are able to select "Start" to start recoridng when they want the next filters that applied to the image to be recorded. Click "Stop" and then "Save" to save the actions recorded into an ops file. The actions will be applied in the ops file when it's selected. 

**Brightness and Contrast Adjust**
- Adjust the brightness and the contrast of the image to the desesired percentage. 


In The ToolBar: Frequently used actions
**ToolBar File Actions**
- Exit, Open, Save, SaveAs.

**ToolBar Edit Actions**
- Undo, Redo.

**ToolBar View Actions**
- Zoom In, Zoom Out, Zoom Full.

**ToolBar Filter Actions**
- Mean Blur, Sharpen Blur.

**ToolBar Image Actions**
- Rotate, Flip.

**ToolBar Colour Actions**
- GreyScale, Brightness, Contrast.

**ToolBar Mouse Actions**
- crop, draw.

**ToolBar Macro Actions**
- Start Recording, Stop Recording.

## Known Issues

<b><b><b>
Export, SaveAs
- When a file type is not entered, it gets defaulted to ".ops". However, we have attempted to change the default to ".png", the image saved or exported cannot be opened as the file type can not be recognised. But it works if the user input ".png" themselves. 

Transform actions, mean blur, gaussian blur, median blur
- When entering a non-integer value such as a string or double, the value is automatically corrected by the spinner to 1. We believe this is an acceptable replacement and there is not much we can do to intercept this change without significantly refactoring our code.

Crop, Draw
- When starting these actions a java.awt.event.MouseEvent.getPoint() is thrown, this has no effect on the cropping or drawing.
Due to the implementation and code incompatibility, crop and draw cannot be captured by Macro operations and be saved. Unfortunately, we noticed this issue far too late to be able to make any changes that isn't re-doing the entire selection part of the feature. 
