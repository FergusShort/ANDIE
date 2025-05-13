package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import java.awt.Rectangle;
import javax.swing.*;

/**
 * <p>
 * ImageOperation to Draw an oval on an image.
 * </p>
 * 
 * <p>
 * Draw Oval works by drawing a oval on the current image.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseDrawOval implements java.io.Serializable, ImageOperation {
  
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
     * Construct a Draw Oval with the given coordinates.
     * </p>
     * 
     * <p>
     * The coordinates change the area of the oval.
     * </p>
     * 
     * @param firstX The first X coordinate of the newly constructed MouseDrawOval
     * @param firstY The first Y coordinate of the newly constructed MouseDrawOval
     * @param secondX The second X coordinate of the newly constructed MouseDrawOval
     * @param secondY The second Y coordinate of the newly constructed MouseDrawOval
     * @param draw The draw is there indicating whether or not to draw.
     */
    MouseDrawOval(Rectangle drawArea, boolean draw){
        this.drawArea = drawArea;
        this.draw = draw;
    }
    
    /**
     * <p>
     * Construct a default Draw Oval.
     * </p>
     * 
     * <p>
     * The coordinates are set to their default.
     * </p>
     * 
     * @param firstX The first X coordinate of the newly constructed MouseDrawOval
     * @param firstY The first Y coordinate of the newly constructed MouseDrawOval
     * @param secondX The second X coordinate of the newly constructed MouseDrawOval
     * @param secondY The second Y coordinate of the newly constructed MouseDrawOval
     * @param draw The draw is there indicating whether or not to draw.
     */
    MouseDrawOval(){
        this.imagePanel = null;
        this.drawArea = null;
    }
    
    /**
     * <p>
     * Apply a Draw to an image.
     * </p>
     * 
     * <p>
     * The Draw is implemented via .drawOval().
     * </p>
     * 
     * @param input The image to apply the Rotate to.
     */
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
                g2d.fillOval((int)drawArea.getX(), (int)drawArea.getY(), (int)drawArea.getWidth(), (int)drawArea.getHeight());
            }
            g2d.dispose();
            return output;
        }
        else{
            return input;
        }
    }
}