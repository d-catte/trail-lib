package io.github.d_catte.scene;

import io.github.d_catte.TrailApplication;

import java.nio.file.Path;
import java.util.HashMap;

public class ScreenManager {
    private final HashMap<Integer, Screen> SCREENS_REGISTRY;

   public ScreenManager(Path scenesPath) {
        SCREENS_REGISTRY = TrailApplication.SERIALIZER.getScreens(scenesPath);
   }
}
