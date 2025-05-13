package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import java.awt.Rectangle;
import javax.swing.*;

/**
 * <p>
 * ImageOperation to Draw a rectangle on an image.
 * </p>
 * 
 * <p>
 * Draw rectangle works by drawing a rectangle on the current image.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseDrawRectangle implements java.io.Serializable, ImageOperation {
  
    /** Draw Area */
    protected Rectangle drawArea;
    /** Image panel */
    protected ImagePanel imagePanel;
    /** Draw */
    protected boolean draw = false;
    /** Colour */
    protected Color color;

    /** Language bundle */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Construct a Draw Rectangle with the given coordinates.
     * </p>
     * 
     * <p>
     * The coordinates change the area of the Rectangle.
     * </p>
     * 
     * @param firstX The first X coordinate of the newly constructed MouseDrawRectangle
     * @param firstY The first Y coordinate of the newly constructed MouseDrawRectangle
     * @param secondX The second X coordinate of the newly constructed MouseDrawRectangle
     * @param secondY The second Y coordinate of the newly constructed MouseDrawRectangle
     * @param draw The draw is there indicating whether or not to draw.
     */
    MouseDrawRectangle(Rectangle drawArea, boolean draw){
        this.drawArea = drawArea;
        this.draw = draw;
    }
    
    /**
     * <p>
     * Construct a default Draw Rectangle.
     * </p>
     * 
     * <p>
     * The coordinates are set to their default.
     * </p>
     * 
     * @param firstX The first X coordinate of the newly constructed MouseDrawRectangle
     * @param firstY The first Y coordinate of the newly constructed MouseDrawRectangle
     * @param secondX The second X coordinate of the newly constructed MouseDrawRectangle
     * @param secondY The second Y coordinate of the newly constructed MouseDrawRectangle
     * @param draw The draw is there indicating whether or not to draw.
     */
    MouseDrawRectangle(){
        this.imagePanel = null;
        this.drawArea = null;
    }

    @Override
    public BufferedImage apply(BufferedImage input) {
        
        if(draw){
            color = JColorChooser.showDialog(null, bundle.getString("Pick_a_colour"), Color.white);
            draw = false;
        }
        if(color != null){
            BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = output.createGraphics();
            g2d.drawImage(input, 0, 0, null);
            if (drawArea != null) {
                g2d.setColor(color); 
                g2d.fill(drawArea);
            }
            g2d.dispose();
            return output;
        }
        else{
            return input;
        }
    }
}