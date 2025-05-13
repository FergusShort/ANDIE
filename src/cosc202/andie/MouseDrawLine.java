package cosc202.andie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import javax.swing.*;
import java.awt.BasicStroke;

/**
 * <p>
 * ImageOperation to Draw a line on an image.
 * </p>
 * 
 * <p>
 * Draw Line works by drawing a line on the current image.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseDrawLine implements java.io.Serializable, ImageOperation {

    /** Image panel */
    protected ImagePanel imagePanel;
    /** Draw */
    protected boolean draw = false;
    /** Color */
    protected Color color;
    /** First X */
    protected int firstX;
    /** Second X */
    protected int secondX;
    /** First Y */
    protected int firstY;
    /** Second Y */
    protected int secondY;

    /** Language bundle */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Construct a Draw Line with the given coordinates.
     * </p>
     * 
     * <p>
     * The coordinates change the area of the line.
     * </p>
     * 
     * @param firstX  The first X coordinate of the newly constructed MouseDrawLine
     * @param firstY  The first Y coordinate of the newly constructed MouseDrawLine
     * @param secondX The second X coordinate of the newly constructed MouseDrawLine
     * @param secondY The second Y coordinate of the newly constructed MouseDrawLine
     * @param draw    The draw is there indicating whether or not to draw.
     */
    MouseDrawLine(int firstX, int firstY, int secondX, int secondY, boolean draw) {
        this.draw = draw;
        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
    }

    /**
     * <p>
     * Construct a default Draw Line.
     * </p>
     * 
     * <p>
     * The coordinates are set to their default.
     * </p>
     * 
     * @param firstX  The first X coordinate of the newly constructed MouseDrawLine
     * @param firstY  The first Y coordinate of the newly constructed MouseDrawLine
     * @param secondX The second X coordinate of the newly constructed MouseDrawLine
     * @param secondY The second Y coordinate of the newly constructed MouseDrawLine
     * @param draw    The draw is there indicating whether or not to draw.
     */
    MouseDrawLine() {
        this.imagePanel = null;
        this.firstX = 0;
        this.secondX = 0;
        this.firstY = 0;
        this.secondY = 0;
    }

    /**
     * <p>
     * Apply a Draw to an image.
     * </p>
     * 
     * <p>
     * The Draw is implemented via .drawLine().
     * </p>
     * 
     * @param input The image to apply the Rotate to.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {

        if (draw) {
            color = JColorChooser.showDialog(null, bundle.getString("Pick_a_colour"), Color.white);
            draw = false;
        }
        if (color != null) {
            BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = output.createGraphics();
            g2d.drawImage(input, 0, 0, null);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawLine(firstX, firstY, secondX, secondY);
            g2d.dispose();
            return output;
        } else {
            return input;
        }
    }
}