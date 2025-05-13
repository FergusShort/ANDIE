package cosc202.andie;


import java.awt.image.*;
import java.util.*;


/**
 * <p>
 * ImageOperation to apply a random scatter filter
 * </p>
 *
 * <p>
 * Random scatter works by taking the random pixel values in a radius around the current pixel
 * </p>
 *
 * @author Liam Bland / Team Octagon
 */
public class RandomScatter implements ImageOperation, java.io.Serializable {
   
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int radius;


    /**
     * <p>
     * Construct a random scatter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius' of the pixles used for a scatter.
     * </p>
     *
     * @param radius The radius of the newly constructed Random Scatter
     */
    RandomScatter(int radius) {
        this.radius = radius;    
    }


    /**
     * <p>
     * Construct a Random Scatter with the default size.
     * </p
     * >
     * <p>
     * By default, a Random Scatter has radius 1.
     * </p>
     *
     * @see RandomScatter(int)
     */
    RandomScatter() {
        this(1);
    }


    /**
     * <p>
     * Apply a Random scatter to an image.
     * </p>
     *
     *
     * @param input The image to apply the random scatter to.
     * @return The resulting (scattered) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int size = (2*radius+1)*(2*radius+1);
        int [] argbArray = new int[size]; //Make an array for each colour
       


        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());


        int counter;
        Random r = new Random();
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
                        argbArray[counter] = argb;
                        counter++;
                    }
                }
                int rIdx = r.nextInt(size);
                int aAvg = argbArray[rIdx]; 
                output.setRGB(x,y, aAvg);
           
           
            }
        }
        return output;
    }
}

