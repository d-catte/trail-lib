package io.github.d_catte;

import com.google.gson.Gson;
import io.github.d_catte.game.Game;
import io.github.d_catte.scene.Scene;
import io.github.d_catte.scene.default_scenes.MainMenu;
import io.github.d_catte.utils.Config;
import io.github.d_catte.utils.rendering.ImGUIRenderer;
import io.github.d_catte.utils.rendering.SwingRenderer;
import io.github.d_catte.utils.rendering.TrailRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TrailApplication {
    public static final Logger LOGGER = LoggerFactory.getLogger("Trail Lib");
    public static Game gameInstance;
    public static final String VERSION = "v1.0.0-dev.1";
    public static final String[] SAVES;

    public static void main(String[] args) {
    }

    public TrailApplication() {
        LOGGER.info("Initializing Trail Lib");
        Gson gson = new Gson();
        LOGGER.info("Loading Saves");
        SAVES = this.loadSaves();
        LOGGER.info("Loading Config");
        Config config = Config.getConfig(gson, config());
        LOGGER.info("Creating Rendering Context");
        TrailRenderer renderer;
        if (config.renderingBackend == Config.RenderingBackend.Swing) {
            renderer = new SwingRenderer();
        } else {
            renderer = new ImGUIRenderer();
        }
        LOGGER.info("Forming Main GUI");
        Scene mainMenu = new MainMenu();

    }

    public Path sceneData() {
        return null;
    }

    public Path statusData() {
        return null;
    }

    public Path gameData() {
        return null;
    }

    public Path memberData() {
        return null;
    }

    public Path itemData() {
        return null;
    }

    public Path shopItemData() {
        return null;
    }

    public Path config() {
        return null;
    }

    private String[] loadSaves() {
        List<String> filenames = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(gameData())) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    filenames.add(path.getFileName().toString());
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load save data. Does the path exist?", e);
        }

        return filenames.toArray(new String[0]);
    }
}