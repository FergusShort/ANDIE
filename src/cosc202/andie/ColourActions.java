package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;
    /** getting the Language Preference bundle ANDIE is currently running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions(){

       
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction(bundle.getString("Greyscale"), null,
                bundle.getString("Convert_To_Greyscale") + " (Alt G)", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new InvertImageAction(bundle.getString("Invert_Image"), null,
                bundle.getString("Invert_Image_Colours"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ColourSwapAction(bundle.getString("Colour_Swap"), null, bundle.getString("Swap_Image_Colours"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAdjustAction(bundle.getString("Brightness_Adjust"), null,
                bundle.getString("Adjust_The_Brightness") + " (Alt B)", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new ContrastAdjustAction(bundle.getString("Contrast_Adjust"), null,
                bundle.getString("Adjust_The_Contrast") + " (Alt C)", Integer.valueOf(KeyEvent.VK_G)));

    }


    

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("Colour"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
            
        }

    }

    /**
     * <p>
     * Action to invert the colour of an image.
     * </p>
     * 
     * @see InvertImage
     */
    public class InvertImageAction extends ImageAction {

        /**
         * <p>
         * Create a new invert-colour action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        InvertImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the invert-image action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the InvertImageAction is triggered.
         * It changes the image to inverted colour.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new InvertImage());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to swap the colours of an image.
     * </p>
     * 
     * @see ColourSwap
     */
    public class ColourSwapAction extends ImageAction {

        /**
         * <p>
         * Create a new swap-colour action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ColourSwapAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the colour-swap action is triggered.
         * It prompts the user for a colour channel order, then applies it
         * appropriately.
         * {@link ColourSwap}.
         * </p>
         * 
         * <p>
         * This method is called whenever the ColourSwapAction is triggered.
         * It changes the colour channels to reflect the given order.
         * </p>
         * 
         * @param e The event triggering this callback.
         */

        public void actionPerformed(ActionEvent e) {

            // Determine the colour channel order by asking user
            String colourOptions = null;

            String[] options = { "RedGreenBlue", "RedBlueGreen", "GreenRedBlue", "GreenBlueRed", "BlueGreenRed",
                    "BlueRedGreen" };
            JComboBox<String> colourComboBox = new JComboBox<>(options);
            int colourOption = JOptionPane.showOptionDialog(null, colourComboBox, "Select a colour order", // Fix
                                                                                                           // message
                                                                                                           // later
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box
            if (colourOption == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (colourOption == JOptionPane.OK_OPTION) {
                colourOptions = (String) colourComboBox.getSelectedItem();
            }

            // Create and apply the colourswap
            target.getImage().apply(new ColourSwap(colourOptions));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to adjust the brightness of an image.
     * </p>
     * 
     * @see BrightnessAdjust
     */
    public class BrightnessAdjustAction extends ImageAction {

        /**
         * <p>
         * Create a new brightness-adjust action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessAdjustAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Brightness Adjust action is triggered.
         * It prompts the user for a percentage to adjust the brightness, then applies
         * it
         * appropriately.
         * {@link BrightnessAdjust}.
         * </p>
         * 
         * <p>
         * This method is called whenever the BrightnessAdjust is triggered.
         * It adjust the brightness with given percentage the user enters, with a
         * default at 0.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the percentage - ask the user.
            int percentage = 0;

            // Pop-up dialog box to ask for the percentage value.
            SpinnerNumberModel percentageModel = new SpinnerNumberModel(0, -100, 100, 1);
            JSpinner percentageSpinner = new JSpinner(percentageModel);
            int option = JOptionPane.showOptionDialog(null, percentageSpinner,
                    bundle.getString("Enter_Brightness_Percentage"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                percentage = percentageModel.getNumber().intValue();
            }

            // Create and apply the brightness adjustment
            target.getImage().apply(new BrightnessAdjust(percentage));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to adjust the contrast of an image.
     * </p>
     * 
     * @see ContrastAdjust
     */
    public class ContrastAdjustAction extends ImageAction {

        /**
         * <p>
         * Create a new brightness-adjust action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ContrastAdjustAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the contrast adjust action is triggered.
         * It prompts the user for a percentage to adjust the contrast, then applies it
         * appropriately.
         * {@link ContrastAdjust}.
         * </p>
         * 
         * <p>
         * This method is called whenever the ContrastAdjust is triggered.
         * It adjust the contrast with given percentage the user enters, with a
         * default at 0.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the percentage - ask the user.
            int percentage = 0;

            // Pop-up dialog box to ask for the percentage value.
            SpinnerNumberModel percentageModel = new SpinnerNumberModel(0, -100, 100, 1);
            JSpinner percentageSpinner = new JSpinner(percentageModel);
            int option = JOptionPane.showOptionDialog(null, percentageSpinner,
                    bundle.getString("Enter_Contrast_Percentage"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                percentage = percentageModel.getNumber().intValue();
            }

            // Create and apply the adjustment
            target.getImage().apply(new ContrastAdjust(percentage));
            target.repaint();
            target.getParent().revalidate();
        }
    }

}
