package io.github.d_catte.scene.default_scenes;

import io.github.d_catte.TrailApplication;
import io.github.d_catte.game.event.Event;
import io.github.d_catte.scene.Scene;
import io.github.d_catte.scene.Screen;

import java.util.List;

public class AbstractEventScene implements Scene {
    private int index;
    private boolean finished;
    private final List<Screen> screens;

    /**
     * This is a basic even scene that displays screens with an image, text, and a continue button.
     * @param screenDescTranslations All translations for screens
     * @param viewableProfessions What professions are allowed to see this screen.
     *                            Make sure that a zero length array is created for allowing all professions.
     * @param imagePath The path to the image
     * @param additionalScreenData All extra data for each screen.
     *                             Make sure that if a screen doesn't have data, a zero length array is created
     */
    public AbstractEventScene(Event event) {
        this.screens = TrailApplication.SERIALIZER.getScreens(event);
    }

    @Override
    public List<Screen> getScreens() {
        return this.screens;
    }

    @Override
    public int getScreenIndex() {
        return this.index;
    }

    @Override
    public void setScreenIndex(int i) {
        this.index = i;
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void setFinished() {
        this.finished = true;
    }
}
