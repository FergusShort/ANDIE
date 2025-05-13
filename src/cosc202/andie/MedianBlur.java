package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median blur filter.
 * </p>
 * 
 * <p>
 * A Median filter blurs an image by replacing each pixel by the mean of the
 * pixels in a surrounding neighbourhood.
 * </p>
 * 
 * @author Liam Bland / Team Octagon
 */
public class MedianBlur implements ImageOperation, java.io.Serializable {
    
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;

    /**
     * <p>
     * Construct a Median blur with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the pixles used for a median.
     * </p>
     * 
     * @param radius The radius of the newly constructed Median Blur
     */
    MedianBlur(int radius) {
        this.radius = radius;    
    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     * 
     * @see MedianBlur(int)
     */
    MedianBlur() {
        this(1);
    }

    /**
     * <p>
     * Apply a Median blur to an image.
     * </p>
     * 
     * 
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2*radius+1)*(2*radius+1);
        int [] aArray = new int[size]; //Make an array for each colour
        int [] rArray = new int[size];
        int [] gArray = new int[size];
        int [] bArray = new int[size];

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());

        int counter;
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                counter = 0;
                for (int j = -radius; j <= radius; j++) {
                    for (int i = -radius; i <= radius; i++) {
                        int currentX = x + i;   
                        int currentY = y + j;
                        if (currentX < 0 || currentX >= input.getWidth() || currentY < 0 || currentY >= input.getHeight()) {
                            continue; // Skip this iteration if out of bounds
                        }
                        int argb = input.getRGB(currentX, currentY);
                        aArray[counter] = (argb & 0xFF000000) >> 24;
                        rArray[counter] = (argb & 0x00FF0000) >> 16;
                        gArray[counter] = (argb & 0x0000FF00) >> 8;
                        bArray[counter] = (argb & 0x000000FF);
                        
                        counter++;
                    }
                }
                Arrays.sort(aArray, 0, counter);
                Arrays.sort(rArray, 0, counter);
                Arrays.sort(gArray, 0, counter);
                Arrays.sort(bArray, 0, counter);
            
                int median = (counter - 1) / 2; 
                int aAvg = aArray[median]; //take the median value of the array
                int rAvg = rArray[median];
                int gAvg = gArray[median];
                int bAvg = bArray[median];
                int argb = (aAvg << 24) | (rAvg << 16) | (gAvg << 8) | bAvg;
                output.setRGB(x,y, argb); 
            
            
            }
        }
        return output;
    }
}