package io.github.d_catte.utils;

import java.awt.*;

public class Text {
    public String textString;
    public int[] textRGB;
    public int fontSize;

    public Text(String textString, int[] textRGB, int fontSize) {
        this.textString = textString;
        this.textRGB = textRGB;
        this.fontSize = fontSize;
    }

    public Text(String textString, Color color, int fontSize) {
        this(textString, new int[] {color.getRed(), color.getGreen(), color.getBlue()}, fontSize);
    }

    public Text(String textString) {
        this(textString, Color.BLACK, 12); //TODO Determine a good default font size
    }
}
