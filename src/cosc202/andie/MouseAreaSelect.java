package cosc202.andie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.*;

/**
 * <p>
 * MouseAreaSelect to select an area to crop in image.
 * </p>
 * 
 * <p>
 * Area select works by selecting an are in the current image
 * by a given user input.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseAreaSelect implements java.io.Serializable, MouseListener {

    /** The end point of the selection */
    private Point startPoint;
    /** The end point of the selection */
    private Point endPoint;
    /** Crop area region*/
    protected static Rectangle selectedRegion;
    /** Image Panel */
    protected ImagePanel imagePanel;
    /** Applied Crop */
    protected boolean appliedCrop = false;
    /** Enabled to crop */
    protected boolean enabled = true;

    /** Language bundle */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Construct a Area Select.
     * </p>
     * 
     * <p>
     * The mouse listener is added to the image panel.
     * </p>
     */
    public MouseAreaSelect() {
        this.imagePanel = ImageAction.getTarget();
        this.imagePanel.addMouseListener(this);
    }

    /**
     * <p>
     * Construct a default cropImage.
     * </p>
     * 
     * <p>
     * The mouse listener is null.
     * </p>
     */

    public void cropImage() {
        mousePressed(null);
        mouseReleased(null);
    }

    /**
     * <p>
     * Find the start point of the crop area.
     * </p>
     * 
     * <p>
     * The area of the crop is found by the mouse event e.
     * </p>
     * 
     * @param e MouseEvent The mouse event to use for the crop area.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (enabled) {
            return;
        } else {
            // Start point of the rectangle
            startPoint = e.getPoint();
        }
    }

    /**
     * <p>
     * Apply a crop to an image.
     * </p>
     * 
     * <p>
     * The area of the crop is found and implemented by calling
     * the MouseCrop().
     * </p>
     * 
     * @param e MouseEvent The mouse event to use for the crop area.
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (enabled) {
            return;
        } else {

            // End point of the rectangle
            endPoint = e.getPoint();

            // Creates rectangle
            selectedRegion = new Rectangle(
                    Math.min(startPoint.x, endPoint.x),
                    Math.min(startPoint.y, endPoint.y),
                    Math.abs(startPoint.x - endPoint.x),
                    Math.abs(startPoint.y - endPoint.y));

            // Creates a new shaded area
            imagePanel.getImage().apply(new MouseRectangleArea(selectedRegion, false));
            imagePanel.repaint();
            imagePanel.getParent().revalidate();

            // starts crop function
            JButton option = new JButton(bundle.getString("Would_you_like_to_crop"));
            int result = JOptionPane.showConfirmDialog(null, option, bundle.getString("Crop_Image"),
                    JOptionPane.YES_NO_OPTION);

            // If user chooses not to crop
            if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {

                // Removes shading if user doesn't crop
                try {
                    imagePanel.getImage().undo();
                } catch (Exception E) {
                    System.out.println("");
                }

                imagePanel.getImage().apply(new MouseRectangleArea(selectedRegion, true));
                imagePanel.repaint();
                imagePanel.getParent().revalidate();

                // If user chooses to crop
            } else if (result == JOptionPane.YES_OPTION) {

                // Removes shading before crop
                try {
                    imagePanel.getImage().undo();
                } catch (Exception E) {
                    System.out.println("");
                }

                // Crops image

                imagePanel.getImage().apply(new MouseRectangleArea(selectedRegion, true));
                imagePanel.repaint();
                imagePanel.getParent().revalidate();

                imagePanel.getImage().apply(new MouseCrop(selectedRegion));
                imagePanel.repaint();
                imagePanel.getParent().revalidate();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println("Mouse Clicked");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // System.out.println("Mouse entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // System.out.println("Mouse exited");
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean input) {
        enabled = input;
    }
}