package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Image menu.
 * </p>
 * 
 * <p>
 * The Image menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * </p>
 * 
 * @author Jackson Stephens
 * 
 */
public class TransformActions {

    /** A list of actions for the Image menu. */
    protected ArrayList<ImageAction> transformActions;
    /** getting the Language Preference bundle ANDIE is current;y running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();
    /**
     * <p>
     * Create a set of Image menu actions.
     * </p>
     */
    public TransformActions() {
        transformActions = new ArrayList<ImageAction>();
        // Resize
        transformActions.add(new ResizeAction(bundle.getString("Resize"), null, bundle.getString("Apply_A_Resize"), Integer.valueOf(KeyEvent.VK_R)));
        // Rotate
        transformActions.add(new RotateAction(bundle.getString("Rotate"), null, bundle.getString("Apply_A_Rotation")+ " (Alt R)", Integer.valueOf(KeyEvent.VK_P)));
        // Rotate
        transformActions.add(new FlipAction(bundle.getString("Flip"), null, bundle.getString("Apply_A_Flip")+ " (Alt F)", Integer.valueOf(KeyEvent.VK_F)));
    }

    /**
     * <p>
     * Create a menu containing the list of Image actions.
     * </p>
     * 
     * @return The image menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("Image"));

        for (ImageAction action : transformActions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }
    
    /**
     * <p>
     * Action to resize an image.
     * </p>
     * 
     * @see Resize
     */
    public class ResizeAction extends ImageAction {

        /**
         * <p>
         * Create a new Resize action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the Resize is triggered.
         * It prompts the user for a filter percentage, then applies an appropriate size
         * {@link Resize}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the percentage - ask the user.
            int percentage = 100;

            // Pop-up dialog box to ask for the percentage value.
            SpinnerNumberModel percentageModel = new SpinnerNumberModel(100, 1, 500, 50);
            JSpinner percentageSpinner = new JSpinner(percentageModel);
            int option = JOptionPane.showOptionDialog(null, percentageSpinner, bundle.getString("Enter_Filter_Percentage"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                percentage = percentageModel.getNumber().intValue();
            }

            // Create and apply the resize
            target.getImage().apply(new Resize(percentage));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to rotate an image.
     * </p>
     * 
     * @see Rotate
     */
    public class RotateAction extends ImageAction {

        /**
         * <p>
         * Create a new Rotate action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RotateAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the Rotate is triggered.
         * It prompts the user for a filter angle, then applies it appropriately
         * {@link Rotate}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the angle - ask the user.
            String angleOptions = null;

            String [] options = {bundle.getString("90_Right"), "180", bundle.getString("90_Left")};
            JComboBox<String> angleComboBox = new JComboBox<>(options);
            int angleOption = JOptionPane.showOptionDialog(null, angleComboBox, bundle.getString("Select_An_Option"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (angleOption == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (angleOption == JOptionPane.OK_OPTION) {
                angleOptions = (String) angleComboBox.getSelectedItem();
            }

            // Create and apply the rotate
            target.getImage().apply(new Rotate(angleOptions));
            target.repaint();
            target.getParent().revalidate();
        }
    }
    
    /**
     * <p>
     * Action to flip an image.
     * </p>
     * 
     * @see Flip
     */
    public class FlipAction extends ImageAction {

        /**
         * <p>
         * Create a new Flip action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FlipAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the Flip is triggered.
         * It prompts the user for a flip option, then applies it appropriately
         * {@link Rotate}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the flip option - ask the user.
            String flipOption = null;

            // Pop-up dialog box to ask for the flip option value.
            String [] options = {bundle.getString("Horizontal"), bundle.getString("Vertical")};
            JComboBox<String> optionComboBox = new JComboBox<>(options);
            int option = JOptionPane.showOptionDialog(null, optionComboBox, bundle.getString("Select_An_Option"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return string from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                flipOption = (String) optionComboBox.getSelectedItem();
            }

            // Create and apply the option
            target.getImage().apply(new Flip(flipOption));
            target.repaint();
            target.getParent().revalidate();
        }
    }
}
