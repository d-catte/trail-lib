package io.github.d_catte.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * For changing text in the game.
 * @author Dylan Catte, Ben Westover, Noah Sumerauer, Micah Lee
 * @version 1.0
 */
public class Translations {
    private static Map<String, String> translations;
    private static final String UNKNOWN_TRANSLATION = "This translation is missing";
    private static final String PROFESSION_PREFIX = "profession.desc.";

    /**
     * Loads the translations from the language provided in config
     * @param config Config instance
     * @param path Path to the language directory
     * @param gson Gson instance
     */
    public static void loadTranslations(Config config, Path path, Gson gson) {
        path = path.resolve(config.language + ".json");

        try (FileReader reader = new FileReader(path.toFile())) {
            translations = gson.fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the translation for the specified translation key
     * @param translationKey The key for specifying the translation
     * @return The translated text in the language chosen
     */
    public static String getTranslatedText(String translationKey) {
        return translations.getOrDefault(translationKey, UNKNOWN_TRANSLATION);
    }

    /**
     * Gets the translation for the specified translation key
     * @param translationKey The key for specifying the translation
     * @param additionalData Data that will be filled in for %s patterns
     * @return The translated text in the language chosen
     */
    public static String getTranslatedText(String translationKey, String... additionalData) {
        String unformattedString = getTranslatedText(translationKey);
        int index = 0;
        while (unformattedString.contains("%s")) {
            if (index < additionalData.length) {
                unformattedString = unformattedString.replaceFirst("%s", additionalData[index]);
                index++;
            } else {
                break;
            }
        }
        return unformattedString;
    }

    /**
     * Gets the translation for the profession's description
     * @param profession Profession identifier
     * @return The translated profession description in the language chosen
     */
    public static String getTranslatedProfessionDescription(String profession) {
        return translations.getOrDefault(PROFESSION_PREFIX + profession, UNKNOWN_TRANSLATION);
    }

    public static final String
            TITLE = "main.title",
            CONTINUE = "main.continue",
            NEW_GAME = "main.new_game",
            TITLE_1 = "game_config.title",
            TITLE_2 = "load.title",
            SCREEN_NEXT = "screen.continue"
    ;
}
