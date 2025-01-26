package io.github.d_catte.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class Translations {
    private static Map<String, String> translations;
    private static final String UNKNOWN_TRANSLATION = "This translation is missing";

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

    public static final String
            TITLE = "main.title",
            CONTINUE = "main.continue",
            NEW_GAME = "main.new_game",
            TITLE_1 = "game_config.title",
            TITLE_2 = "load.title"
    ;
}
