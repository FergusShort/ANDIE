package cosc202.andie;


import java.awt.image.*;


/**
 * <p>
 * ImageOperation to apply a block average filter
 * </p>
 *
 * <p>
 * Block average works by taking the average value for a radius and setting all pixels within the range to be the average.
 * </p>
 *
 * @author Liam Bland / Team Octagon
 */
public class BlockAverage implements ImageOperation, java.io.Serializable {
   
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a 5x5 filter, and so forth.
     */
    private int xVal, yVal;


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
    BlockAverage(int xVal, int yVal) {
        this.xVal = xVal;
        this.yVal = yVal;  
    }


    /**
     * <p>
     * Construct a block average with the default size.
     * </p
     * >
     * <p>
     * By default, a block average has radius 1.
     * </p>
     *
     * @see BlockAverage(int)
     */
    BlockAverage() {
        this(1, 1);
    }


    /**
     * <p>
     * Apply a block scatter to an image.
     * </p>
     *
     *
     * @param input The image to apply the block average to.
     * @return The resulting (scattered) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int blockSizeX = 2 * xVal + 1;
        int blockSizeY = 2 * yVal + 1;
        int blockArea = blockSizeX * blockSizeY;
    
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
    
        for (int y = 0; y < input.getHeight(); y += blockSizeY) {
            for (int x = 0; x < input.getWidth(); x += blockSizeX) {
                int aSum = 0, rSum = 0, gSum = 0, bSum = 0;
    
                for (int j = 0; j < blockSizeY; j++) {
                    for (int i = 0; i < blockSizeX; i++) {
                        int currentX = x + i;
                        int currentY = y + j;
    
                        if (currentX >= 0 && currentX < input.getWidth() && currentY >= 0 && currentY < input.getHeight()) {
                            int argb = input.getRGB(currentX, currentY);
                            aSum += (argb & 0xFF000000) >> 24;
                            rSum += (argb & 0x00FF0000) >> 16;
                            gSum += (argb & 0x0000FF00) >> 8;
                            bSum += (argb & 0x000000FF);
                        }
                    }
                }
      
                int argb = (aSum / blockArea << 24) | (rSum / blockArea << 16) | (gSum / blockArea << 8) | bSum / blockArea;
    
                for (int j = 0; j < blockSizeY; j++) {
                    for (int i = 0; i < blockSizeX; i++) {
                        int currentX = x + i;
                        int currentY = y + j;
    
                        if (currentX >= 0 && currentX < input.getWidth() && currentY >= 0 && currentY < input.getHeight()) {
                            output.setRGB(currentX, currentY, argb);
                        }
                    }
                }
            }
        }
    
        return output;
    }
}

