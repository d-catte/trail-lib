package io.github.d_catte.utils;

import java.awt.*;

public class TranslatableText extends Text {

    public TranslatableText(String translationKey, int[] textRGB, int fontSize) {
        super(Translations.getTranslatedText(translationKey), textRGB, fontSize);
    }

    /**
     * Gets the transaltion key for the text
     * @param translationKey What translation key is being used
     */
    public TranslatableText(String translationKey) {
        super(Translations.getTranslatedText(translationKey));
    }
}
