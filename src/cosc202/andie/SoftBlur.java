package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * FilterOperation to sharp blur an image
 * </p>
 * 
 * <p>
 * Soft blur the image
 * </p>
 * 
 * @author Team Octagon
 * 
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Construct a soft blur with the given soft blur option.
     * </p>
     * 
     * <p>
     * The soft blur option weak blurs the image
     * </p>
     * 
     */

    SoftBlur() {
        
    }

    /**
     * <p>
     * Apply a soft blur to an image.
     * </p>
     * 
     * 
     * @param input The image to apply the Soft Blur filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // The values for the kernel as a 9-element array
        float[] array = { 0, 1 / 8.0f, 0,
                1 / 8.0f, 1 / 2.0f, 1 / 8.0f,
                0, 1 / 8.0f, 0 };

        // Make a 3x3 filter from the array
        Kernel kernel = new Kernel(3, 3, array);

        // Apply this as a convultion - same code as MeanFilter
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }
}
