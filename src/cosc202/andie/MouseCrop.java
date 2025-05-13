package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * <p>
 * ImageOperation to crop an image.
 * </p>
 * 
 * <p>
 * Crop works by cropping the current image by a given area.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseCrop implements java.io.Serializable, ImageOperation {
  
    /** Crop area */
    protected Rectangle cropArea;

    /** An ImagePanel */
    private ImagePanel imagePanel;
    
    /**
     * <p>
     * Construct a Crop with the given area.
     * </p>
     * 
     * <p>
     * The area changes the size of the image.
     * </p>
     * 
     * @param cropArea The area of the newly constructed Crop
     */
    MouseCrop(Rectangle cropArea){
        this.cropArea = cropArea;
        this.imagePanel = ImageAction.getTarget();
    }
    
    /**
     * <p>
     * Construct a Crop with the default area.
     * </p
     * >
     * <p>
     * By default, a Crop has the area of null.
     * </p>
     * 
     * @see MouseCrop(Rectangle)
     */
    MouseCrop(){
        this(null);
    }
    
    /**
     * <p>
     * Apply a Crop to an image.
     * </p>
     * 
     * <p>
     * The Crop is implemented via .getSubimage(int).
     * The area of the crop is specified by the user.  
     * </p>
     * 
     * @param input The image to apply the Rotate to.
     */
    public BufferedImage apply (BufferedImage input){
        imagePanel.getImage().undo();
        imagePanel.repaint();
        imagePanel.getParent().revalidate();
        BufferedImage output = input.getSubimage((int) cropArea.getX(), (int) cropArea.getY(), (int) cropArea.getWidth(), (int) cropArea.getHeight()); 
        return output;
    }
}