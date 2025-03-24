package io.github.d_catte;

import com.google.gson.Gson;
import io.github.d_catte.data.DataPaths;
import io.github.d_catte.data.Serializer;
import io.github.d_catte.game.Game;
import io.github.d_catte.utils.Config;
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
    public static String[] SAVES;
    public static Serializer SERIALIZER;

    public static void main(String[] args) {
    }

    public TrailApplication() {
        LOGGER.info("Initializing Trail Lib");
        Gson gson = new Gson();
        LOGGER.info("Gathering Data");
        DataPaths dataPaths = new DataPaths(
                sceneData(),
                statusData(),
                gameData(),
                itemData(),
                townData(),
                eventData(),
                professionData(),
                config()
        );
        LOGGER.info("Loading Config");
        Config config = Config.getConfig(gson, dataPaths);
        LOGGER.info("Creating Rendering Context");
        LOGGER.info("Serializing Data");
        SERIALIZER = new Serializer(dataPaths, gson);
        LOGGER.info("Reading Saves");
        SAVES = this.loadSaves();
        LOGGER.info("Forming Main GUI");
    }

    public Path sceneData() {
        return null;
    }

    public Path statusData() {
        return null;
    }

    /**
     * This is the path to the saves directory.
     * Inside of this directory will be all folders labelled with the name of the game save.
     * The directory hierarchy looks like this:
     * saves
     * - "save1"
     *      - game.json
     *      - members.json
     * - "save2"
     *      - game.json
     *      - members.json
     * @return Path to the saves directory
     */
    public Path gameData() {
        return null;
    }

    public Path itemData() {
        return null;
    }

    public Path shopItemData() {
        return null;
    }

    public Path townData() {
        return null;
    }

    public Path eventData() {
        return null;
    }

    public Path professionData() {
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