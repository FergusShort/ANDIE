package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * <p>
 * Actions provided by the Macro menu.
 * </p>
 * 
 * <p>
 * The Macro menu provide easy access for users to ccess alredy done operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Yuxin Luo/Octagon
 * @version 1.0
 */

public class MacroActions {
    /** A list of actions for the Macro menu. */
    protected ArrayList<Action> actions;

    /** Flag to indicate whether recording is in progress */
    private static boolean recording = false;

    /** getting the Language Preference bundle ANDIE is currently running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     * 
     * @return The Macro menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(bundle.getString("Macro"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Create a set of Macro menu actions.
     * </p>
     */
    public MacroActions() {
        actions = new ArrayList<Action>();
        actions.add(
                new MacroStartAction(bundle.getString("Start"), null, bundle.getString("Start_Recording") + " (Alt D)",
                        Integer.valueOf(KeyEvent.VK_D)));
        actions.add(new MacroStopAction(bundle.getString("Stop"), null, bundle.getString("Stop_Recording") + " (Alt E)",
                Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new MacroSaveAction(bundle.getString("Save"), null, bundle.getString("Save_Recording"),
                Integer.valueOf(KeyEvent.VK_H)));
        actions.add(
                new MacroApplyAction(bundle.getString("Apply"), null, bundle.getString("Apply_Recording"),
                        Integer.valueOf(KeyEvent.VK_I)));
    }


    /**
     * <p>
     * Action to start macro recording
     * 
     */
    public class MacroStartAction extends ImageAction {

        /**
         * <p>
         * Create a new Macro start recording action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroStartAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditableImage.recorded.clear();
            startRecording();

        }

    }

    /**
     * <p>
     * Action to stop macro recording
     * 
     */
    public class MacroStopAction extends ImageAction {

        /**
         * <p>
         * Create a new Macro stop recording action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroStopAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            stopRecording();

        }

    }

    /**
     * <p>
     * Action to save macro recording
     * 
     */
    public class MacroSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new Macro save recording action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);

                    String opsFilepath = imageFilepath + ".ops";
                    FileOutputStream fileOut = new FileOutputStream(opsFilepath);
                    ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                    objectOut.writeObject(EditableImage.recorded);
                    objectOut.close();
                    fileOut.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(target, "There's nothing to save", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

    }

    /**
     * <p>
     * 
     * Action to apply the saved macro operations. 
     * 
     */
    public class MacroApplyAction extends ImageAction {

        /**
         * Create a new Macro apply recorded actions action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MacroApplyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select ops file");
            int result = fileChooser.showOpenDialog(null);
            String filename = "";
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    filename = selectedFile.getCanonicalPath();
                } catch (Exception ex) {
                    System.out.println("Error: " + e);
                    JOptionPane.showMessageDialog(null, "Please select a valid ops file", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
            target.getImage().macroApply(filename);
            target.repaint();
            target.getParent().revalidate();

        }

    }

    /**
     * method that updates the recording variable
     */

    public void startRecording() {
        recording = true;
    }

    /**
     * method that updates the recording variable
     */
    public void stopRecording() {
        recording = false;
    }

    /**
     * 
     * method that return whether it is currently recording
     *
     * @return If it is recording or not
     * 
     */
    public static boolean isRecording() {
        return recording;
    }

}
