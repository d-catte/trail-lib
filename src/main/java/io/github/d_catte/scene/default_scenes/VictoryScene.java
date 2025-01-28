package io.github.d_catte.scene.default_scenes;

import io.github.d_catte.scene.Scene;
import io.github.d_catte.scene.Screen;

import java.util.List;

public class VictoryScene implements Scene {
    @Override
    public List<Screen> getScreens() {
        return List.of();
    }

    @Override
    public int getScreenIndex() {
        return 0;
    }

    @Override
    public void setScreenIndex(int i) {

    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public void setFinished() {

    }
}
