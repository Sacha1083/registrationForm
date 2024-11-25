package util;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class TextData {
    private static ResourceBundle bundle;

    public TextData() {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        String[] options = {"English", "Spanish"};
        int choice = JOptionPane.showOptionDialog(dialog, "Choose a language", "Language Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        Locale locale;
        if (choice == 1) {
            locale = Locale.forLanguageTag("es");
            bundle = ResourceBundle.getBundle("messages", locale);
        } else if (choice == 0) {
            locale = Locale.forLanguageTag("en");
            bundle = ResourceBundle.getBundle("messages", locale);
        } else {
            System.out.println("User canceled language selection");
            System.exit(0);
        }
    }

    public static String getText(String keyValue) {
        return bundle.getString(keyValue);
    }

    public static String readTitleFormPanel() {
        StringBuilder sb = new StringBuilder();

            sb.append("<html>")
                    .append("<center><h1>Register User Form</h1></center>");
            sb.append("</html>");

        return sb.toString();
    }
}