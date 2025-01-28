package io.github.d_catte.scene.default_screens;

import io.github.d_catte.data.UIComponent;
import io.github.d_catte.scene.Screen;
import io.github.d_catte.utils.Command;
import io.github.d_catte.utils.Text;
import io.github.d_catte.utils.TranslatableText;
import io.github.d_catte.utils.Translations;
import io.github.d_catte.utils.rendering.ComponentType;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a basic screen with the following:
 * - Image that coves the top and middle
 * - Text at the bottom
 * - "Next" Button to continue to the next screen
 */
public class InfoScreen implements Screen {
    private final List<String> professionsShown;
    private final String description;
    private final String imagePath;
    private final int[] size = new int[2];

    public InfoScreen(String descTranslationKey, String[] viewableProfessions, String imagePath) {
        this.professionsShown = Arrays.stream(viewableProfessions).toList();
        this.description = Translations.getTranslatedText(descTranslationKey);
        this.imagePath = imagePath;
    }

    public InfoScreen(String descTranslationKey, String[] viewableProfessions, String imagePath, String... additionalInfo) {
        this(Translations.getTranslatedText(descTranslationKey, additionalInfo), viewableProfessions, imagePath);
    }

    @Override
    public List<String> getProfessionShownList() {
        return this.professionsShown;
    }

    @Override
    public List<UIComponent> getUIComponents() {
        return createUIComponents();
    }

    @Override
    public int[] getSize() {
        return size;
    }

    @Override
    public void setSize(int width, int height) {
        this.size[0] = width;
        this.size[1] = height;
    }

    private List<UIComponent> createUIComponents() {
        List<UIComponent> components = new ArrayList<>();
        // Image
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Image)
                .setPosition(0.5, 0.3, this.size)
                .setSize(new int[] {this.size[0], (int) Math.round(this.size[1] * 0.6)})
                .setImageUri(URI.create(this.imagePath))
                .build()
        );
        // Text
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Text)
                .setPosition(0.5, 0.75, this.size)
                .setText(new Text(
                        this.description
                ))
                .build()
        );
        // Button
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Button)
                .setPosition(0.5, 0.9, this.size)
                .setText(new TranslatableText(
                        Translations.SCREEN_NEXT
                ))
                .setCommand(new Command("nextScreen"))
                .build()
        );
        return components;
    }
}
