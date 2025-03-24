package io.github.d_catte.utils;

/**
 * Gets the text to translate
 * @author Dylan Catte, Ben Westover, Noah Sumerauer, Micah Lee
 * @version 1.0
 */
public class TranslatableText extends Text {

    /**
     * Sends the aspects of hte text to the Transaltions class
     * @param translationKey What key should be used for translation
     * @param textRGB What color the text is
     * @param fontSize What font size the text is
     */
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
