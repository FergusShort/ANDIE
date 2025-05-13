package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to flip an image.
 * </p>
 * 
 * <p>
 * Flip works by flipping the current image horizontally, or vertically.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class Flip implements ImageOperation, java.io.Serializable{

    /**
     * The option by which to flip the image.
     */
    private String flipOption;

    /**
     * <p>
     * Construct a Flip with the given flipOption.
     * </p>
     * 
     * <p>
     * The flipOption changes the direction the image flips.
     * </p>
     * 
     * @param flipOption The flip option of the newly constructed Flip
     */
    Flip(String flipOption){
        this.flipOption = flipOption;
    }

    /**
     * <p>
     * Construct a Flip with the default option.
     * </p
     * >
     * <p>
     * By default, a Flip has the option of its original positioning.
     * </p>
     * 
     * @see Flip(String)
     */
    Flip(){
        this(null);
    }

    /**
     * <p>
     * Apply a Flip to an image.
     * </p>
     * 
     * <p>
     * The Flip is implemented via .drawImage() in a negative.
     * The flip option is specified by the {@link flipOption}.  
     * </p>
     * 
     * @param input The image to apply the Flip to.
     * @return The resulting (flipped) image.
     */
    public BufferedImage apply (BufferedImage input){

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = flipped.createGraphics();
        
        //Horizontal
        if(flipOption.equals("Horizontal")){
            graphic.drawImage(input, 0 + width, 0, -width, height, null);
        }
        // Vertical
        else{
            graphic.drawImage(input, 0, 0 + height, width, -height, null);
        }
        graphic.dispose();
        return flipped;

    }
}

