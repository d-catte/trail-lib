package io.github.d_catte.scene;

import io.github.d_catte.utils.rendering.TrailRenderer;

import java.util.List;

public interface Scene {
    List<Screen> getScreens();
    default void showNextScreen(TrailRenderer renderer) {
        // -1 represents a Scene that has just been made
        if (getScreenIndex() == -1) {
            getScreens().getFirst().renderUIComponents(renderer);
            setScreenIndex(0);
        } else {
            int index = getScreenIndex() + 1;
            if (index < getScreens().size()) {
                getScreens().get(index).renderUIComponents(renderer);
                setScreenIndex(index);
            } else {
                setFinished();
            }
        }
    }
    int getScreenIndex();
    void setScreenIndex(int i);
    boolean finished();
    void setFinished();
}
