package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * <p>
 * MouseRectangleArea to select an area to shade in an image.
 * </p>
 * 
 * <p>
 * Area select works by selecting an area in the current image 
 * by a given user input.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseRectangleArea implements java.io.Serializable, ImageOperation {
  
    /** Crop area */
    protected Rectangle cropArea;
    /** Crop */
    protected boolean crop;

    /**
     * <p>
     * Construct a MouseRectangleArea with the given area.
     * </p>
     * 
     * <p>
     * The cropArea change the area of the shaded area.
     * </p>
     * 
     * @param crop The crop is there indicating whether or not to draw.
     * @param cropArea The crop are is there indicating the area to draw on.
     */
    MouseRectangleArea(Rectangle cropArea, boolean crop){
        this.cropArea = cropArea;
        this.crop = crop;
    }

    /**
     * <p>
     * Construct a default MouseRectangleArea.
     * </p>
     * 
     * @param crop The crop is there indicating whether or not to draw.
     * @param cropArea The crop are is there indicating the area to draw on.
     */
    MouseRectangleArea(){
        this.cropArea = null;
        this.crop = false;
    }

    /**
     * <p>
     * Apply a shaded area to an image.
     * </p>
     * 
     * <p>
     * The shaded area is implemented via .fill().
     * </p>
     * 
     * @param input The image to apply the Rotate to.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        if(!crop){
            BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = output.createGraphics();
            g2d.drawImage(input, 0, 0, null);
            g2d.setColor(new Color(0, 0, 0, 85)); 
            Rectangle imageBounds = new Rectangle(0, 0, input.getWidth(), input.getHeight());
            g2d.fill(imageBounds);
            if (cropArea != null) {
                g2d.setColor(new Color(255, 255, 255, 120));
                g2d.fill(cropArea);
            }
            g2d.dispose();
            return output;
        } else {
            return input;
        }
    }
}