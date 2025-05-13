package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;

/**
 * <p>
 * LanguageAction that sets the default language preference for ANDIE.
 * </p>
 * 
 * <p>
 * Classes using this language preferences constructor and are using
 * bundle.getString() are changing
 * their visible, translatable aspects from one language to the language
 * selected by the user, this langauge
 * preferences file constructs the LanguagePreference constructor which will
 * then be used for ANDIE
 * </p>
 *
 * @author Fergus Short
 */

public class LanguagePreferences {

    static ResourceBundle bundle;

    /** constructor for LanguagePreferences. */
    static {
        Preferences prefs = Preferences.userNodeForPackage(LanguagePreferences.class);
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        bundle = ResourceBundle.getBundle("MessageBundle", Locale.getDefault());
    }

    /**
     * <p>
     * getter method for LanguagePreferences.
     * </p>
     * 
     * @return bundle
     */
    public static ResourceBundle getBundle() {

        return bundle;
    }

}
