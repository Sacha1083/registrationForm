package util;

import java.awt.*;

/**
 * TextFont class to set the font of the text
 * @see Font
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class TextFont {

    /**
     * titleFont method to set the font of the title
     * @see Font
     * @return Font
     */
    public static Font titleFont() {
        Log.info("Title font set");
        return new Font("Segoe UI", Font.BOLD, 24);
    }

    /**
     * textFont method to set the font of the text
     * @see Font
     * @return Font
     */
    public static Font textFont() {
        Log.info("Text font set");
        return new Font("Segoe UI", Font.BOLD, 12);
    }

    /**
     * textFormFont method to set the font of the text form
     * @see Font
     * @return Font
     */
    public static Font textFormFont() {
        Log.info("Text form font set");
        return new Font("Segoe UI", Font.BOLD, 16);
    }
}
