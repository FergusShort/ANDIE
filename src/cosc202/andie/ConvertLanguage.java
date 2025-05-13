package cosc202.andie;

import java.awt.image.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * <p>
 * LanguageAction that changes language of ANDIE.
 * </p>
 * 
 * <p>
 * ConvertLanguage converts ANDIE to English or Italian as specified by the
 * user.
 * </p>
 * 
 * @author Jackson Stephens
 * @author Fergus Short
 * 
 */
public class ConvertLanguage implements ImageOperation, java.io.Serializable {

    /**
     * The default language option.
     */
    private String langOption;

    /** getting the Language Preference bundle ANDIE is currently running on. */
    ResourceBundle bundle = LanguagePreferences.getBundle();

    /**
     * <p>
     * Convert the language with the given langOption.
     * </p>
     * 
     * <p>
     * The langOption changes the language that ANDIE is being changed to.
     * </p>
     * 
     * @param langOption The language option of the newly constructed
     *                   ConvertLanguage
     */
    ConvertLanguage(String langOption) {
        this.langOption = langOption;
    }

    /**
     * <p>
     * Construct a ConvertLanguage with the default option.
     * </p
     * >
     * <p>
     * By default, the ConvertLanguage option language is english.
     * </p>
     * 
     */
    ConvertLanguage() {
        this(null);
    }

    /**
     * <p>
     * Apply the ConvertLanguage to ANDIE.
     * </p>
     * 
     * <p>
     * 
     * The language option is specified by the {@link langOption}.
     * </p>
     * 
     * @param input The image to apply the language change to.
     * @return The image with the langOption changed.
     */
    public BufferedImage apply(BufferedImage input) {

        Preferences prefs = Preferences.userNodeForPackage(ConvertLanguage.class);

        // English
        if (langOption.equals("English")) {

            prefs.put("language", "en");
            prefs.put("country", "NZ");
        }
        // Italian
        else if (langOption.equals("Italian")) {
            prefs.put("language", "it");
            prefs.put("country", "IT");

        }
        // French
        if (langOption.equals("French")) {

            prefs.put("language", "fr");
            prefs.put("country", "FR");
        }
        // Maori
        if (langOption.equals("Maori")) {

            prefs.put("language", "ma");
            prefs.put("country", "NZ");
        }
        // Samoan
        if (langOption.equals("Samoan")) {

            prefs.put("language", "sa");
            prefs.put("country", "SA");
        }
        // Spanish
        if (langOption.equals("Spanish")) {

            prefs.put("language", "sp");
            prefs.put("country", "SP");
        }
        // Mandarin Chinese
        if (langOption.equals("Mandarin Chinese")) {

            prefs.put("language", "ma");
            prefs.put("country", "CH");
        }
        // Portugese
        if (langOption.equals("Portugese")) {

            prefs.put("language", "po");
            prefs.put("country", "PO");
        }

        JFrame parent = new JFrame();
        JButton button = new JButton();

        button.setText(bundle.getString("Click_Me"));
        parent.add(button);
        parent.pack();
        parent.setVisible(true);

        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
        return input;
    }
}