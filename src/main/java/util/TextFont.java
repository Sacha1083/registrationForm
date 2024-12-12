package util;

import java.awt.*;

/**
 * TextFont class to set the font of the text
 * @return Font
 * @see Font
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class TextFont {
    public static Font titleFont() {
        return new Font("Segoe UI", Font.BOLD, 24);
    }

    public static Font textFont() {
        return new Font("Segoe UI", Font.BOLD, 12);
    }

    public static Font textFormFont() {
        return new Font("Segoe UI", Font.BOLD, 16);
    }
}
