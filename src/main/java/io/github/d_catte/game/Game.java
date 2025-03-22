package io.github.d_catte.game;

import com.google.gson.annotations.Expose;
import io.github.d_catte.TrailApplication;
import io.github.d_catte.data.DataPaths;
import io.github.d_catte.data.Member;
import io.github.d_catte.data.StatusContainer;
import io.github.d_catte.game.event.Event;
import io.github.d_catte.game.event.EventManager;
import io.github.d_catte.scene.Scene;
import io.github.d_catte.utils.Config;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public final List<StatusContainer> statusContainers;
    public final List<Event> events;
    public final Map<Integer, String> towns;
    public final List<Member> members;
    public final Queue<Scene> renderQueue = new ArrayDeque<>(); //TODO Create a memory-efficient way of retrieving all Scenes (don't load them all into RAM)
    public Scene currentScene;
    public final Config config;
    public final DataPaths paths;
    @Expose
    public final Inventory inventory;
    @Expose
    public float money;
    @Expose
    public int currentMile;
    @Expose
    public short difficulty;
    @Expose
    public final String saveName;


    /**
     * This constructor is used when creating a new Game
     * @param saveName The name for the game instance
     * @param paths DataPaths instance
     * @param config Config instance
     * @param memberNames All members' names
     * @param playerProfessions All players' professions
     * @param difficulty The difficulty of the game
     */
    public Game(String saveName, DataPaths paths, Config config, String[] memberNames, String[] playerProfessions, short difficulty) {
        this.money = config.startingMoney;
        this.members = new ArrayList<>(config.teamMemberCount);

        // Add members
        String[] professions = TrailApplication.SERIALIZER.getProfessions();
        StatusContainer defaultStatus = getStatusContainerFromName(config.defaultStatus);
        for (int i = 0; i < config.teamMemberCount; i++) {
            if (i < playerProfessions.length) {
                // Add players
                this.members.add(new Member(memberNames[i], defaultStatus.instanceCopy(), playerProfessions[i], true));
            } else {
                // Add other members
                this.members.add(new Member(memberNames[i], defaultStatus.instanceCopy(), getRandomProfession(professions), false));
            }
        }

        this.statusContainers = TrailApplication.SERIALIZER.getStatuses();
        this.events = TrailApplication.SERIALIZER.getEventData();
        this.towns = TrailApplication.SERIALIZER.getTownData();
        this.inventory = new Inventory();
        this.difficulty = difficulty;
        this.saveName = saveName;
        this.config = config;
        this.paths = paths;
    }

    /**
     * This constructor is used when loading an existing Game
     * @param data SaveData instance
     */
    public Game(DataPaths paths, Game data, List<StatusContainer> statusContainers, List<Event> events, Map<Integer, String> towns, List<Member> memberData, Config config) {
        this.events = events;
        this.statusContainers = statusContainers;
        this.inventory = data.inventory;
        this.money = data.money;
        this.currentMile = data.currentMile;
        this.difficulty = data.difficulty;
        this.saveName = data.saveName;
        this.members = memberData;
        this.towns = towns;
        this.config = config;
        this.paths = paths;
    }

    public String getRandomProfession(String[] professions) {
        int i = ThreadLocalRandom.current().nextInt(0, professions.length);
        return professions[i];
    }

    /**
     * Gets the StatusContainer from the status name
     * @param name Status's name
     * @return StatusContainer instance.
     *         Make sure to call instanceCopy() when using!
     */
    @Nullable
    public StatusContainer getStatusContainerFromName(String name) {
        for (StatusContainer container : this.statusContainers) {
            if (container.name.equals(name)) {
                return container;
            }
        }
        return null;
    }

    public void onGameFinished() {
        // Remove files
        Path gameDir = this.paths.savesDirectoryPath().resolve(this.saveName);
        if (Files.exists(gameDir)) {
            gameDir.toFile().delete();
        }
    }

    public void nextScene() {
        Scene scene = this.renderQueue.poll();
        if (scene != null) {
            this.currentScene = scene;
        } else {
            // Consider all game functions completed for this tick
            tickGame();
        }
    }

    public void nextScreen() {
        this.currentScene.showNextScreen();
        if (this.currentScene.finished()) {
            nextScene();
        }
    }

    /**
     * Ticks all the game's functions
     */
    public void tickGame() {
        // This is meant to be a recursive function
        this.currentMile++;
        if (this.currentMile >= this.config.miles) {
//            this.renderQueue.add(new VictoryScene());
            onGameFinished();
            return;
        }

        if (this.towns.containsKey(this.currentMile)) {
//            this.renderQueue.add(new TownScene());
//            this.renderQueue.add(new StoreScene());
            return;
        }

        Event.selectRandomEvent();

        // Tick events
        if (!EventManager.currentEvents.isEmpty()) {
            // Check twice in case an event adds a second event
            for (int i = 0; i < 2; i++) {
                for (Event event : EventManager.currentEvents) {
//                    this.renderQueue.add(new AbstractEventScene(event));
                }
            }
            events.clear();
        }

        // Check for game over
        if (this.members.isEmpty()) {
            // TODO Game Over
            onGameFinished();
        }
    }
}
