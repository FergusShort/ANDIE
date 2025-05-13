package cosc202.andie;

import java.awt.Image;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to resize an image.
 * </p>
 * 
 * <p>
 * A resize works by scaling the image to a percentage of the current image
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class Resize implements ImageOperation, java.io.Serializable{

    /**
     * The percentage by which to resize the image.
     */
    private int percentage;

    /**
     * <p>
     * Construct a Resize with the given percent.
     * </p>
     * 
     * <p>
     * The percentage changes the size, with a smaller percentage 
     * making the image scale smaller, and a larger percentage
     * increasing the image size.
     * </p>
     * 
     * @param percentage The percentage of the newly constructed Resize
     */
    Resize(int percentage){
        this.percentage = percentage;
    }

    /**
     * <p>
     * Construct a Resize with the default size.
     * </p
     * >
     * <p>
     * By default, a Resize has the percentage size of 100% (100).
     * </p>
     * 
     * @see Resize(int)
     */
    Resize(){
        this(100);
    }

    /**
     * <p>
     * Apply a Resize to an image.
     * </p>
     * 
     * <p>
     * The Resize is implemented via getScaledInstance().
     * The size of the scaling is specified by the {@link percentage}.  
     * A larger percenatge leads to a larger image and vice versa.
     * </p>
     * 
     * @param input The image to apply the Resize filter to.
     * @return The resulting (scaled) image.
     */
    public BufferedImage apply (BufferedImage input){
        
        int width = input.getWidth();
        int height = input.getHeight();

        double scaleFactor = (percentage / 100.0);

        double tempW = scaleFactor * width;
        double tempH = scaleFactor * height;

        int newW = (int) (tempW);
        int newH = (int) (tempH);

        Image temp = input.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        
        result.getGraphics().drawImage(temp, 0, 0, null);

        return result;
    }
}
