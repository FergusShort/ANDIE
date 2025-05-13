package cosc202.andie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.*;

/**
 * <p>
 * MouseDrawing to select an area to draw in image
 * </p>
 * 
 * <p>
 * MouseDrawing works by selecting an are in the current image
 * by a given user input.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseDrawing implements java.io.Serializable, MouseListener {

     /** The start point of the selection */
    private Point startPoint;
    /** The end point of the selection */
    private Point endPoint;
    /** The selected region */
    protected static Rectangle selectedRegion;
    /** IMage panel */
    protected ImagePanel imagePanel;
    /** If drawing is enabled */
    protected boolean enabled = true;

    /** Language bundle */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Construct a MouseDrawing.
     * </p>
     * 
     * <p>
     * The mouse listener is added to the image panel.
     * </p>
     */
    public MouseDrawing() {
        this.imagePanel = ImageAction.getTarget();
        this.imagePanel.addMouseListener(this);
    }

    /**
     * <p>
     * Construct a default drawImage.
     * </p>
     * 
     * <p>
     * The mouse listener is null.
     * </p>
     */
    public void drawImage() {
        mousePressed(null);
        mouseReleased(null);
    }

    /**
     * <p>
     * Find the start point of the draw area.
     * </p>
     * 
     * <p>
     * The area of the draw is found by the mouse event e.
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
     * Apply a draw to an image.
     * </p>
     * 
     * <p>
     * The area of the drawing is found and implemented by calling
     * the MouseDraw...().
     * </p>
     * 
     * @param e MouseEvent e The mouse event to use for the crop area..
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (enabled) {
            return;
        } else {

            endPoint = e.getPoint();
            selectedRegion = new Rectangle(
                    Math.min(startPoint.x, endPoint.x),
                    Math.min(startPoint.y, endPoint.y),
                    Math.abs(startPoint.x - endPoint.x),
                    Math.abs(startPoint.y - endPoint.y));

            String drawOptions = null;

            String[] options = { bundle.getString("Rectangle"), bundle.getString("Oval"), bundle.getString("Line") };
            JComboBox<String> optionComboBox = new JComboBox<>(options);
            int option = JOptionPane.showOptionDialog(null, optionComboBox, bundle.getString("Select_An_Option"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                drawOptions = (String) optionComboBox.getSelectedItem();
            }

            if (drawOptions.equals(bundle.getString("Rectangle"))) {
                boolean draw = true;
                imagePanel.getImage().apply(new MouseDrawRectangle(selectedRegion, draw));
                imagePanel.repaint();
                imagePanel.getParent().revalidate();

            } else if (drawOptions.equals(bundle.getString("Oval"))) {
                boolean draw = true;
                imagePanel.getImage().apply(new MouseDrawOval(selectedRegion, draw));
                imagePanel.repaint();
                imagePanel.getParent().revalidate();
            } else {
                boolean draw = true;
                imagePanel.getImage()
                        .apply(new MouseDrawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, draw));
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