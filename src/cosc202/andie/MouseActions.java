package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ResourceBundle;

/**
 * <p>
 * Actions provided by the Mouse menu.
 * </p>
 * 
 * <p>
 * The Mouse menu contains actions that allow for the usage of the mouse for
 * user input actions.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class MouseActions {

    /** A list of actions for the Mouse menu. */
    protected ArrayList<ImageAction> mouseActions;
    /** Getting the Language Preference bundle ANDIE is current;y running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();
    /** Creating mouse crop */
    MouseAreaSelect mouseCrop = new MouseAreaSelect();
    /** Creating mouse draw */
    MouseDrawing mouseDraw = new MouseDrawing();

    /**
     *
     */
    public MouseActions() {
        mouseActions = new ArrayList<ImageAction>();
        // Mouse Crop
        mouseActions.add(new MouseAreaSelectAction(bundle.getString("Crop"), null, bundle.getString("Crop_Action"), Integer.valueOf(KeyEvent.VK_R)));
        // Mouse Drawing
        mouseActions.add(new MouseDrawAction(bundle.getString("Draw"), null, bundle.getString("Draw_Action"), Integer.valueOf(KeyEvent.VK_R)));
    }

    /**
     * <p>
     * Create a menu containing the list of Mouse actions.
     * </p>
     * 
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("Mouse"));

        for (ImageAction action : mouseActions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to crop an image.
     * </p>
     */
    public class MouseAreaSelectAction extends ImageAction {
        
        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MouseAreaSelectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the crop action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MouseareaSelectAction is triggered.
         * It crops the image to a user selected size, no greater than the image size.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            mouseCrop.setEnabled(false);
            mouseDraw.setEnabled(true);
            
            JFrame parent = new JFrame();
            JButton button = new JButton();
            button.setText("Crop Selected");
            parent.add(button);
            parent.pack();
            parent.setVisible(true);
            
            mouseCrop.cropImage();
        }
    }

    /**
     * <p>
     * Action to draw on an image.
     * </p>
     */
    public class MouseDrawAction extends ImageAction {
        
        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MouseDrawAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        
        /**
         * <p>
         * Callback for when the draw action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MouseDrawAction is triggered.
         * It draws on the image to a user selected area, no greater than the image size.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            mouseCrop.setEnabled(true);
            mouseDraw.setEnabled(false);
            
            JFrame parent = new JFrame();
            JButton button = new JButton();
            button.setText("Draw Selected");
            parent.add(button);        
            parent.pack();
            parent.setVisible(true);
            
            mouseDraw.drawImage();
        }
    }
}
