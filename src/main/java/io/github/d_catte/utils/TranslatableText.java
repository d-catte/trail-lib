package io.github.d_catte.utils;

import java.awt.*;

public class TranslatableText extends Text {

    public TranslatableText(String translationKey, int[] textRGB, int fontSize) {
        super(Translations.getTranslatedText(translationKey), textRGB, fontSize);
    }

    public TranslatableText(String translationKey, Color color, int fontSize) {
        super(Translations.getTranslatedText(translationKey), color, fontSize);
    }

    public TranslatableText(String translationKey) {
        super(Translations.getTranslatedText(translationKey));
    }
}
