package io.github.d_catte.utils;

/**
 * Stores the color and size of a piece of text in the game.
 */
public class Text {
    public String textString;
    public int[] textRGB;
    public int fontSize;

    /**
     * Text constructor
     * @param textString The string that makes up the Text
     * @param textRGB Array of RGB values for the text color (e.g. {255, 255, 255} for black)
     * @param fontSize Size of text in the Cowboy Pixel font
     */
    public Text(String textString, int[] textRGB, int fontSize) {
        this.textString = textString;
        this.textRGB = textRGB;
        this.fontSize = fontSize;
    }

    /**
     * Default Text constructor, assumes black text of size 12
     * @param textString The string that makes up the Text
     */
    public Text(String textString) {
        this(textString, new int[] {255, 255, 255}, 12);
    }
}
