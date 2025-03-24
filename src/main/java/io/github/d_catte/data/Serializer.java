package io.github.d_catte.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.d_catte.TrailApplication;
import io.github.d_catte.game.Game;
import io.github.d_catte.game.PurchasableItem;
import io.github.d_catte.game.event.Event;
import io.github.d_catte.scene.Screen;
import io.github.d_catte.utils.Config;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serializes JSON files
 * @author Dylan Catte, Ben Westover, Noah Sumerauer, Micah Lee
 * @version 1.0
 */
public class Serializer {
    private final DataPaths paths;
    private final Gson gson;

    /**
     * Constructor that sets the local variables to the inputed variables.
     * @param paths The path of the file
     * @param gson The GSON file
     */
    public Serializer(DataPaths paths, Gson gson) {
        this.paths = paths;
        this.gson = gson;
    }

    /**
     * Gets the statuses for the game with their base probability
     * @return All available statuses for the game with their base probabilities
     */
    public List<StatusContainer> getStatuses() {
        try (FileReader reader = new FileReader(this.paths.statusPath().toFile())) {
            return this.gson.fromJson(reader, new TypeToken<List<StatusContainer>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets data about the previous Game session
     * @param path Path to the game directory
     * @return Game with minimal data
     */
    public Game getGameData(Path path) {
        try (FileReader reader = new FileReader(path.resolve("game.json").toFile())) {
            return this.gson.fromJson(reader, Game.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the members' data from the previous Game session
     * @param path Path to the game directory
     * @return Members' data
     */
    public List<Member> getMemberData(Path path) {
        try (FileReader reader = new FileReader(path.resolve("members.json").toFile())) {
            return this.gson.fromJson(reader, new TypeToken<List<Member>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all professions that can be used
     * @return Array of all professions
     */
    public String[] getProfessions() {
        try (FileReader reader = new FileReader(this.paths.professionPath().toFile())) {
            return this.gson.fromJson(reader, String[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the Events that can occur on the journey
     * @return Events
     */
    public List<Event> getEventData() {
        try (FileReader reader = new FileReader(this.paths.eventsPath().toFile())) {
            return this.gson.fromJson(reader, new TypeToken<List<Event>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all the Screens from scenes json
     * @param path The path to scenes json
     * @return All Screens for the Event
     */
    public HashMap<Integer, Screen> getScreens(Path path) {
        try (FileReader reader = new FileReader(path.toFile())) {
            List<SerializedScreen> serializedScreenData = this.gson.fromJson(reader, new TypeToken<List<SerializedScreen>>(){}.getType());
            HashMap<Integer, Screen> hash = new HashMap<>();
            for (SerializedScreen serializedScreen : serializedScreenData) {
                Screen screen = serializedScreen.getScreen();
                hash.put(screen.getId(), screen);
            }
            return hash;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the ItemStacks being sold in the shop with their prices
     * @return All PurchasableItems
     */
    public List<PurchasableItem> getShopItemsData() {
        try (FileReader reader = new FileReader(this.paths.itemsPath().toFile())) {
            return this.gson.fromJson(reader, new TypeToken<List<PurchasableItem>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the towns and at what mile marker they are at
     * @return All towns
     */
    public HashMap<Integer, String> getTownData() {
        try (FileReader reader = new FileReader(this.paths.landmarksPath().toFile())) {
            return this.gson.fromJson(reader, new TypeToken<HashMap<Integer, String>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the game data for the game instance
     * @param saveName The name of the save to load
     * @return game data as a Game instance
     */
    public Game loadGame(String saveName) {
        Path savePath = this.paths.savesDirectoryPath().resolve(saveName);
        Game priorGame = getGameData(savePath);
        List<Member> members = getMemberData(savePath);
        return new Game(paths, priorGame, getStatuses(), getEventData(), getTownData(), members, Config.getConfig(this.gson, this.paths));
    }

    /**
     * Saves new or existing game data
     */
    public void saveGame() {
        Path saveDir = this.paths.savesDirectoryPath().resolve(TrailApplication.gameInstance.saveName);
        Path gamePath = saveDir.resolve("game.json");
        Path memberPath = saveDir.resolve("member.json");
        if (Files.notExists(gamePath)) {
            try {
                Files.createFile(gamePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter writer = new FileWriter(gamePath.toFile())) {
            writer.write(this.gson.toJson(TrailApplication.gameInstance));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Files.notExists(memberPath)) {
            try {
                Files.createFile(memberPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter writer = new FileWriter(memberPath.toFile())) {
            writer.write(this.gson.toJson(TrailApplication.gameInstance.members));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
