package cosc202.andie;

import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;

import cosc202.andie.ColourActions.*;
import cosc202.andie.EditActions.*;
import cosc202.andie.FileActions.*;
import cosc202.andie.TransformActions.*;
import cosc202.andie.ViewActions.*;

/**
 * <p>
 * Class for the Toolbar for ANDIE project.
 * </p>
 * 
 * <p>
 * This Toolbar menu will have easy access to the most used operations from our
 * project so that users won't have to always select from
 * the drop down menus, the operations deemed to be necessary for easy access
 * include, Open, Save, Save as, Exit, Undo, Redo, Flip, Rotate, Invert and Zoom
 * Full
 * It adds to the ANDIE menu with a toolbar just below the action menus
 * </p>
 * 
 * 
 * @author Fergus Short
 */
public class Toolbar {

    /** A list of actions for the Toolbar menu. */
    protected ArrayList<Action> tBarActions;

    /** getting the Language Preference bundle ANDIE is currently running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Create a set of toolbar menu actions.
     * </p>
     */
    public Toolbar() {
        tBarActions = new ArrayList<Action>();

  

    }


   /**
     * <p>
     * Create a toolbar containing the list of common actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JToolBar createToolbar() {

        JToolBar toolBar = new JToolBar();

        for (Action action : tBarActions) {
            toolBar.add(new JMenuItem(action));
        }

        return toolBar;
    }








    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class tBarFileOpenAction extends FileOpenAction {
        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param fileActions where all of the name, icon,desc and mnemonic come from
         * @param name        The name of the action (ignored if null).
         * @param icon        An icon to use to represent the action (ignored if null).
         * @param desc        A brief description of the action (ignored if null).
         * @param mnemonic    A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarFileOpenAction(FileActions fileActions, String name, ImageIcon icon, String desc, Integer mnemonic) {
            fileActions.super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {

                try {
                    String imageFilename = fileChooser.getSelectedFile().getName();
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();

                    if (!(imageFilename.toLowerCase().endsWith(".png"))
                            && !(imageFilename.toLowerCase().endsWith(".jpg"))
                            && !(imageFilename.toLowerCase().endsWith(".jpeg"))) {

                        JOptionPane.showMessageDialog(target, "This is not an image", "Error",
                                JOptionPane.INFORMATION_MESSAGE);

                        throw new IllegalArgumentException("This is not an image.");

                    }
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {
                    System.out.println();
                }
            }

            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class tBarFileSaveAction extends FileSaveAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param fileActions where all of the name, icon,desc and mnemonic come from
         * @param name        The name of the action (ignored if null).
         * @param icon        An icon to use to represent the action (ignored if null).
         * @param desc        A brief description of the action (ignored if null).
         * @param mnemonic    A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarFileSaveAction(FileActions fileActions, String name, ImageIcon icon, String desc, Integer mnemonic) {
            fileActions.super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().save();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(target, "There's nothing to save", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class tBarSaveAsAction extends FileSaveAsAction {
        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param fileActions where all of the name, icon,desc and mnemonic come from
         * @param name        The name of the action (ignored if null).
         * @param icon        An icon to use to represent the action (ignored if null).
         * @param desc        A brief description of the action (ignored if null).
         * @param mnemonic    A mnemonic key to use as a shortcut (ignored if null).
         */

        tBarSaveAsAction(FileActions fileActions, String name, ImageIcon icon, String desc, Integer mnemonic) {
            fileActions.super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */

        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(target, "There's nothing to save", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class tBarFileExitAction extends FileExitAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param fileActions where all of the name, icon,desc and mnemonic come from
         * @param name        The name of the action (ignored if null).
         * @param icon        An icon to use to represent the action (ignored if null).
         * @param desc        A brief description of the action (ignored if null).
         * @param mnemonic    A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarFileExitAction(FileActions fileActions, String name, ImageIcon icon, String desc, Integer mnemonic) {
            fileActions.super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class tBarUndoAction extends UndoAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param editActions where all of the name, icon,desc and mnemonic come from
         * @param name        The name of the action (ignored if null).
         * @param icon        An icon to use to represent the action (ignored if null).
         * @param desc        A brief description of the action (ignored if null).
         * @param mnemonic    A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarUndoAction(EditActions editActions, String name, ImageIcon icon, String desc, Integer mnemonic) {
            editActions.super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */
    public class tBarRedoAction extends RedoAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param editActions where all of the name, icon,desc and mnemonic come from
         * @param name        The name of the action (ignored if null).
         * @param icon        An icon to use to represent the action (ignored if null).
         * @param desc        A brief description of the action (ignored if null).
         * @param mnemonic    A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarRedoAction(EditActions editActions, String name, ImageIcon icon, String desc, Integer mnemonic) {
            editActions.super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().redo();
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
    public class tBarBrightnessAdjustAction extends BrightnessAdjustAction {

        /**
         * <p>
         * Create a new brightness-adjust action.
         * </p>
         * 
         * @param colourActions where all of the name, icon,desc and mnemonic come from
         * @param name          The name of the action (ignored if null).
         * @param icon          An icon to use to represent the action (ignored if
         *                      null).
         * @param desc          A brief description of the action (ignored if null).
         * @param mnemonic      A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarBrightnessAdjustAction(ColourActions colourActions, String name, ImageIcon icon, String desc,
                Integer mnemonic) {
            colourActions.super(name, icon, desc, mnemonic);
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
     * Action to rotate an image.
     * </p>
     * 
     * @see Rotate
     */
    public class tBarRotateAction extends RotateAction {

        /**
         * <p>
         * Create a new Rotate action.
         * </p>
         * 
         * @param transformActions where all of the name, icon,desc and mnemonic come from
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarRotateAction(TransformActions transformActions,String name, ImageIcon icon, String desc, Integer mnemonic) {
            transformActions.super(name, icon, desc, mnemonic);
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
    public class tBarFlipAction extends FlipAction {

        /**
         * <p>
         * Create a new Flip action.
         * </p>
         * 
         * @param transformActions where all of the name, icon,desc and mnemonic come from
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        tBarFlipAction(TransformActions transformActions,String name, ImageIcon icon, String desc, Integer mnemonic) {
            transformActions.super(name, icon, desc, mnemonic);
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
    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class tBarZoomFullAction extends ZoomFullAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param viewActions where all of the name, icon,desc and mnemonic come from
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        tBarZoomFullAction(ViewActions viewActions,String name, ImageIcon icon, String desc, Integer mnemonic) {
            viewActions.super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }

    }

}
