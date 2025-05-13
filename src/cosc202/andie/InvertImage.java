package cosc202.andie;

import java.awt.image.*;


/**
 * <p>
 * ColourActions, creates a negative image by inverting the colours.
 * </p>
 *
 * 
 * @author Jasmine Smith/octagon
 * 
 */

public class InvertImage  implements ImageOperation, java.io.Serializable{
    /**
     * <p>
     * Create a new InvertImage operation.
     * </p>
     */
    InvertImage() {

    }

    /**
     * <p>
     * Apply inverting operation to an image.
     * </p>
     * 
     * <p>
     * Iterates over the pixels in the image and updates the red, green,
     * and blue values to 255 minus their original values. So a pixel with 
     * an initial value of (r, g, b) becomes (255 − r, 255 − g, 255 − b). 
     * </p>
     * 
     * @param input The image to be colour inverted.
     * @return The resulting inverted image.
     */
    public BufferedImage apply(BufferedImage input) {
  
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                argb = (a << 24) | (r << 16) | (g << 8) | b;
                input.setRGB(x, y, argb);
            }
        }
        
        return input;
    }
    
}

