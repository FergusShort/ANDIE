package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ColourAction to reorder colour channels in an image.
 * </p>
 * 
 * <p>
 * ColourSwap reorders image colour channels by user selection.
 * </p>
 * 
 * @author Jasmine Smith/octagon
 * 
 */
public class ColourSwap implements ImageOperation, java.io.Serializable{
    
    /**
     * The order by which the colour channels will be swapped in image.
     */    
    private String colourOptions;
    
    /**
     * <p>
     * Construct ColourSwap with the given colour order option.
     * </p>
     * 
     * <p>
     * Red, green and blue channel values of an image are swapped by given order.
     * </p>
     * 
     */
    ColourSwap(String colourOptions) {
        this.colourOptions = colourOptions;
    }  

    /**
     * <p>
     * Construct a ColourSwap with the default RGB (red, blue, green) order.
     * </p
     *
     */

    ColourSwap(){
        this(null);
    }

    /**
     * <p>
     * Apply colour swap operation to an image.
     * </p>
     * 
     * <p>
     * Iterates over the pixels in the image and swaps the red, green,
     * and blue channel values to match the user given order. 
     * 
     * </p>
     * 
     * @param input The image to be colour swapped.
     * @return The resulting colour swapped image.
     */
    public BufferedImage apply(BufferedImage input) {
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                if (colourOptions.equals("RedGreenBlue")){
                    argb = (a << 24) | (r << 16) | (g << 8) | b;
                    input.setRGB(x, y, argb);
                
                } else if (colourOptions.equals("RedBlueGreen")){
                    argb = (a << 24) | (r << 16) | (b << 8) | g;
                    input.setRGB(x, y, argb);
                
                } else if (colourOptions.equals("GreenRedBlue")) {
                    argb = (a << 24) | (g << 16) | (r << 8) | b;
                    input.setRGB(x, y, argb);
                
                } else if (colourOptions.equals("GreenBlueRed")) {
                    argb = (a << 24) | (g << 16) | (b << 8) | r;
                    input.setRGB(x, y, argb);
                
                } else if (colourOptions.equals("BlueGreenRed")) {
                    argb = (a << 24) | (b << 16) | (g << 8) | r;
                    input.setRGB(x, y, argb);
                
                } else {
                    argb = (a << 24) | (b << 16) | (r << 8) | g;
                    input.setRGB(x, y, argb);

                } 
            }
        }
        return input;       
    }
 }
        

