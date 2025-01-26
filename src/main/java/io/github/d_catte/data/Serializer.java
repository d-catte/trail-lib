package io.github.d_catte.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.d_catte.game.Game;
import io.github.d_catte.game.PurchasableItem;
import io.github.d_catte.game.event.Event;
import io.github.d_catte.utils.Config;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public final class Serializer {
    /**
     * Gets the statuses for the game with their base probability
     * @param path Path containing the statuses json
     * @param gson Gson instance
     * @return All available statuses for the game with their base probabilities
     */
    public static List<StatusContainer> getStatuses(Path path, Gson gson) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, new TypeToken<List<StatusContainer>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets data about the previous Game session
     * @param path Path containing the game json
     * @param gson Gson instance
     * @return Game with minimal data
     */
    public static Game getGameData(Path path, Gson gson) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, Game.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the members' data from the previous Game session
     * @param path Path containing the member json
     * @param gson Gson instance
     * @return Members' data
     */
    public static List<Member> getMemberData(Path path, Gson gson) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, new TypeToken<List<Member>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the Events that can occur on the journey
     * @param path Path containing the Events json
     * @param gson Gson instance
     * @return Events
     */
    public static List<Event> getEventData(Path path, Gson gson) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, new TypeToken<List<Event>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the ItemStacks being sold in the shop with their prices
     * @param path Path containing the purchasable items json
     * @param gson Gson instance
     * @return All PurchasableItems
     */
    public static List<PurchasableItem> getShopItemsData(Path path, Gson gson) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, new TypeToken<List<PurchasableItem>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the towns and at what mile marker they are at
     * @param path Path containing the towns json
     * @param gson Gson instance
     * @return All towns
     */
    public static HashMap<Integer, String> getTownData(Path path, Gson gson) {
        try (FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, new TypeToken<HashMap<Integer, String>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the game data for the game instance
     * @param gamePath Path to the game data json file
     * @param memberPath Path to the member data json file
     * @param statusPath Path to the statuses json file
     * @param eventsPath Path to the events json file
     * @param townsPath Path to the towns json file
     * @param configPath Path to the config json file
     * @param gson Gson instance
     * @return game data as a Game instance
     */
    public static Game loadGame(Path gamePath, Path memberPath, Path statusPath, Path eventsPath, Path townsPath, Path configPath, Gson gson) {
        Game priorGame = getGameData(gamePath, gson);
        List<Member> members = getMemberData(memberPath, gson);
        return new Game(priorGame, getStatuses(statusPath, gson), getEventData(eventsPath, gson), getTownData(townsPath, gson), members, Config.getConfig(gson, configPath));
    }

    /**
     * Saves new or existing game data
     * @param data Existing SaveData instance if present
     * @param gson Gson instance
     * @param gamePath The path where the game data is saved
     * @param memberPath The path where the member data is saved
     */
    public static void saveGame(Game data, Gson gson, Path gamePath, Path memberPath) {
        if (Files.notExists(gamePath)) {
            try {
                Files.createFile(gamePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter writer = new FileWriter(gamePath.toFile())) {
            writer.write(gson.toJson(data));
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
            writer.write(gson.toJson(data.members));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
