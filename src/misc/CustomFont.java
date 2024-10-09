package misc;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * A utility class for loading custom fonts from a TrueTypeFont (TTF) file. So that we don't have to rely on system-installed fonts.
 */
public class CustomFont {

    /**
     * Loads a custom font from the specified font file path and returns it with the specified size.
     * @param fontPath The path to the TrueTypeFont (TTF) file within the project.
     * @param size The desired size of the font.
     * @return The loaded font with the specified size, or null if an error occurs during loading.
     */
    public static Font loadFont(String fontPath, float size) {
        try {
            // Get the input stream for the font file
            InputStream is = CustomFont.class.getResourceAsStream(fontPath);
            // Create and return the font with the specified size
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            // Print stack trace for any loading errors
            e.printStackTrace();
            return null;
        }
    }
}