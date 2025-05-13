package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ColourAction to adjust brightness. 
 * </p>
 * 
 * <p>
 * BraightnessAdjust brightness of the image.
 * </p>
 * 
 * @author Yuxin Luo/Octagon
 * 
 */
public class BrightnessAdjust implements ImageOperation, java.io.Serializable {

    /**
     * Options of brightness to choose the adjustment for the image
     */
    private int brightnessPercent;

    /**
     * <p>
     * Construct BrightnessAdjust with the given adjust options.
     * </p>
     * 
     * <p>
     * Red, green and blue channel values of an image are adjusted by given percentage.
     * </p>
     * 
     */
    BrightnessAdjust(int brightnessPercent) {
        this.brightnessPercent = brightnessPercent;
    }

    /**
     * <p>
     * Create a default BrightnessAdjust operation.
     * </p>
     */
    BrightnessAdjust() {
        this(0);
    }

    /**
     * <p>
     * Apply BrightnessAdjust operation to an image.
     * </p>
     * 
     * <p>
     * 
     * Changed the pixel value to by given percentage in the formula
     * 
     * </p>
     * 
     * @param input The image to be adjusted.
     * @return The resulting adjusted image.
     */
    public BufferedImage apply(BufferedImage input) {

        try {

            for (int y = 0; y < input.getHeight(); ++y) {
                for (int x = 0; x < input.getWidth(); ++x) {
                    int argb = input.getRGB(x, y);
                    int a = (argb & 0xFF000000) >> 24;
                    int r = (argb & 0x00FF0000) >> 16;
                    int g = (argb & 0x0000FF00) >> 8;
                    int b = (argb & 0x000000FF);

                    r = (int) ((float) ((float)r - 127.5) + 127.5 * (1.0 + (float) (brightnessPercent /
                    100.0)));
                    g = (int) ((float) ((float)g - 127.5) + 127.5 * (1.0 + (float) (brightnessPercent /
                    100.0)));
                    b = (int) ((float) ((float)b - 127.5) + 127.5 * (1.0 + (float) (brightnessPercent /
                    100.0)));

                    // Ensure the adjusted values are within the valid range
                    r = Math.max(0, Math.min(255, r));
                    g = Math.max(0, Math.min(255, g));
                    b = Math.max(0, Math.min(255, b));

                    argb = (a << 24) | (r << 16) | (g << 8) | b;
                    input.setRGB(x, y, argb);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return input;
    }
}
