package util;

import javax.swing.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class is used to get the text from the properties file.
 * It will ask the user to select the language and then it will
 * load the properties file based on the language selected.
 * @see ResourceBundle
 * @see Locale
 * @see MissingResourceException
 * @see JDialog
 * @see JOptionPane
 * @see ResourceBundle#getBundle(String, Locale)
 * @see ResourceBundle#getString(String)
 * @see Locale#forLanguageTag(String)
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class TextData {
    private static ResourceBundle bundle;
    private static Locale locale;

    /**
     * Constructor that asks the user to select the language and loads the properties file.
     * @see ResourceBundle
     * @see Locale
     * @see ResourceBundle#getBundle(String, Locale)
     * @see ResourceBundle#getString(String)
     * @see Locale#forLanguageTag(String)
     * @see JDialog
     * @see JOptionPane
     * @since JDK21.0.5
     */
    public TextData() {
        Log.info("Selecting language");
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        String[] options = {"English", "Spanish"};
        int choice = JOptionPane.showOptionDialog(dialog, "Choose a language", "Language Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        Log.info("Language selected: " + choice);
        if (choice == 1) {
            Log.info("Spanish language selected");
            locale = Locale.forLanguageTag("es");
            bundle = ResourceBundle.getBundle("messages", locale);
        } else if (choice == 0) {
            Log.info("English language selected");
            locale = Locale.forLanguageTag("en");
            bundle = ResourceBundle.getBundle("messages", locale);
        } else {
            Log.info("User canceled theme selection");
            System.exit(0);
        }
    }

    /**
     * Get the text from the properties file based on the key value.
     * @param keyValue The key value to get the text from the properties file
     * @return The text from the properties file
     * @see ResourceBundle
     * @see ResourceBundle#getString(String)
     * @see MissingResourceException
     * @since JDK21.0.5
     */
    public static String getText(String keyValue) {
        bundle = ResourceBundle.getBundle("messages", locale);
        try {
            Log.info("Getting text: " + keyValue);
            String text = bundle.getString(keyValue);
            Log.info("Text found: " + text);
            return text;
        } catch (MissingResourceException e) {
            Log.error("Key not found: " + keyValue);
            return "Key not found: " + keyValue;
        }
    }
}