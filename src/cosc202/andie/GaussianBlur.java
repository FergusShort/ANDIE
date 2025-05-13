package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Gaussian Blur filter.
 * </p>
 * 
 * <p>
 * A Gaussian Blur filter blurs an image by performing a weighted average of surrounded 
 * pixels of the form of a 2D gaussian. It is implemented via convolution.
 * </p>
 * @author Liam Bland / Team Octagon
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {

    /** The radius of the newly constructed GaussianBlur */
    private int radius;

    /**
     * <p>
     * Construct a Gaussian Blur with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussianBlur
     */

    GaussianBlur(int radius){
        this.radius = radius;
    }

    /**
     * <p>
     * Construct a Gaussian blur with the default size.
     * </p>
     * <p>
     * By default, a Gaussian blur has radius 1.
     * </p>
     * 
     * @see GaussianBlur(int)
     */
    GaussianBlur(){
        this(1);
    }
    /**
     * <p>
     * Apply a Gaussian Blur to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gaussian blur is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.  
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Gaussian blur to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        double sigma = 1/3.0 * radius; //ideal sigma value is one-third the kernel radius
        int size = (2*radius+1);
        float[][] array = new float[size][size];
        double total = 0;

        // Change the value depending on its position 
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                array[x + radius][y + radius] = (float)(Math.exp(-(x*x+y*y)/(2*sigma*sigma)) * 1/(2*3.141*sigma*sigma)); //apply gaussian blur
                total+=array[x + radius][y + radius];
            }
        }
        // Divide by total to ensure brightness remains the same
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] /= total;
            }
        }
        // Flatten the array for the kernel
        float[] flatArray = new float[size*size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(array[i], 0, flatArray, i * size, size);
        }

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, flatArray);
        FilterConvolveOp convOp = new FilterConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }
}
