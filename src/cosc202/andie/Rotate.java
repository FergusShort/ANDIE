package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to rotate an image.
 * </p>
 * 
 * <p>
 * Rotate works by rotating the current image by a given angle.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class Rotate implements ImageOperation, java.io.Serializable{

    /**
     * The angle by which to rotate the image.
     */
    private String angleOptions;

    /**
     * <p>
     * Construct a Rotate with the given percentage.
     * </p>
     * 
     * <p>
     * The angle changes the direction of the image.
     * </p>
     * 
     * @param angle The angle of the newly constructed Rotate
     */
    Rotate(String angleOptions){
        this.angleOptions = angleOptions;
    }

    /**
     * <p>
     * Construct a Rotate with the default angle.
     * </p
     * >
     * <p>
     * By default, a Rotate has the angle of 0 degrees.
     * </p>
     * 
     * @see Rotate(int)
     */
    Rotate(){
        this(null);
    }

    /**
     * <p>
     * Apply a Rotate to an image.
     * </p>
     * 
     * <p>
     * The Rotate is implemented via .rotate().
     * The angle of the rotation is specified by the user.  
     * </p>
     * 
     * @param input The image to apply the Rotate to.
     * @return The resulting (rotated) image.
     */
    public BufferedImage apply (BufferedImage input){

        int width = input.getWidth();
        int height = input.getHeight();

        if(angleOptions.equals("90 Right")){
            BufferedImage rotated = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    rotated.setRGB(height - 1 - j, i, input.getRGB(i, j));
                }
            }
            return rotated;
        }
        else if(angleOptions.equals("180")){
            BufferedImage rotated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    rotated.setRGB(width - 1 - i, height - 1 - j, input.getRGB(i, j));
                }
            }
            return rotated;
        }
        else{
            BufferedImage rotated = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    rotated.setRGB(j, width - 1 - i, input.getRGB(i, j));
                }
            }
            return rotated;
        }
    }
}
