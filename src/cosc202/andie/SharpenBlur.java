package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * FilterOperation to sharp blur an image
 * </p>
 * 
 * <p>
 * Sharpen blur Enchances the differences between neighbouring pixels to sharpen image.
 * </p>
 * 
 * @author Yuxin Luo/Team Octagon
 * 
 */
public class SharpenBlur implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * Construct a sharp blur with the given Sharpen blur option.
     * </p>
     * 
     * <p>
     * The sharpen blur option sharp blurs the image
     * </p>
     * 
     */
    SharpenBlur() {

    }

    /**
     * <p>
     * Apply a sharp blur to an image.
     * </p>
     * 
     * 
     * @param input The image to apply the Sharpen Blur filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        // The values for the kernel as a 9-element array
        float[] array = { 0, -1 / 2f, 0,
                -1 / 2.0f, 3f, -1 / 2.0f,
                0, -1 / 2.0f, 0 };

        // Make a 3x3 filter from the array
        Kernel kernel = new Kernel(3, 3, array);

        // Apply this as a convultion - same code as MeanFilter
        FilterConvolveOp convOp = new FilterConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);


        return output;
    }

}
