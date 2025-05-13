package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Language menu.
 * </p>
 * 
 * <p>
 * The Language menu contains actions to convert ANDIE's langauge to English or Italian.
 * </p>
 * 
 * @author Jackson Stephens
 * @author Fergus Short
 */
public class LanguageActions {
    
    /** A list of actions for the language menu. */

    protected ArrayList<Action> actions;
    /** getting the Language Preference bundle ANDIE is currently running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Create a set of language menu actions.
     * </p>
     */
    public LanguageActions() {  
        actions = new ArrayList<Action>();
        actions.add(new ConvertLanguageAction(bundle.getString("Language_Options"), null, bundle.getString("Convert_The_Language"), Integer.valueOf(KeyEvent.VK_G)));
    }

    /**
     * <p>
     * Create a menu containing the list of language actions.
     * </p>
     * 
     * @return The Language menu UI element.
     */
    public JMenu createMenu() {
        JMenu languageMenu = new JMenu("\uD83C\uDF10");

        for(Action action: actions) {
            languageMenu.add(new JMenuItem(action));
        }

        return languageMenu;
    }

    /**
     * <p>
     * Action to convert ANDIE to English or Italian.
     * </p>
     * 
     * @see ConvertLanguage
     */
    public class ConvertLanguageAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-english action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertLanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-language action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertLanguageAction is triggered.
         * It changes ANDIE to English or Italian.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the language option - ask the user.
            String langOption = null;

            // Pop-up dialog box to ask user for the language option value.
            String [] options = {"English", "Italian","French","Maori","Samoan","Spanish","Portugese","Mandarin Chinese"};
            JComboBox<String> optionComboBox = new JComboBox<>(options);
            int option = JOptionPane.showOptionDialog(null, optionComboBox, bundle.getString("Select_An_Option"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return string from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                langOption = (String) optionComboBox.getSelectedItem();
            }

            // Create and apply the option
            target.getImage().apply(new ConvertLanguage(langOption));
            target.repaint();
            target.getParent().revalidate();
        }

    }

}
