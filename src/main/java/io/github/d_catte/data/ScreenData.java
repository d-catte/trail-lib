package io.github.d_catte.data;

import io.github.d_catte.scene.default_screens.InfoScreen;

import java.util.Optional;

/**
 * This allows for the serialization of basic Screens from json files
 */
public class ScreenData {
    private String descTranslationKey;
    private Optional<String[]> additionalData;
    private Optional<String[]> visibleProfessions;
    private Optional<String> imagePath;

    public ScreenData(){}

    public InfoScreen toInfoScreen() {
        final var ref = new Object() {
            InfoScreen screen;
        };
        this.additionalData.ifPresentOrElse(
                data -> ref.screen = new InfoScreen(this.descTranslationKey, this.visibleProfessions.orElse(null), this.imagePath.orElse(null), data),
                () -> ref.screen = new InfoScreen(this.descTranslationKey, this.visibleProfessions.orElse(null), this.imagePath.orElse(null))
        );
        return ref.screen;
    }
}
