package io.github.d_catte.game;

import com.google.gson.annotations.Expose;
import io.github.d_catte.data.Member;
import io.github.d_catte.data.StatusContainer;
import io.github.d_catte.game.event.Event;
import io.github.d_catte.game.event.EventManager;
import io.github.d_catte.scene.Scene;
import io.github.d_catte.scene.default_scenes.StoreScene;
import io.github.d_catte.utils.Config;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public final List<StatusContainer> statusContainers;
    public final List<Event> events;
    public final Map<Integer, String> towns;
    public final List<Member> members;
    public final Queue<Scene> renderQueue = new ArrayDeque<>();
    public final Config config;
    @Expose
    public final Inventory inventory;
    @Expose
    public float money;
    @Expose
    public int currentMile;
    @Expose
    public short difficulty;


    /**
     * This constructor is used when creating a new Game
     * @param config Config instance
     * @param statusContainers Statuses obtained from the json file
     * @param memberNames All members' names
     * @param playerProfessions All players' professions
     * @param difficulty The difficulty of the game
     * @param professions All professions loaded in the game
     */
    public Game(Config config, List<StatusContainer> statusContainers, List<Event> events, String[] memberNames,
                String[] playerProfessions, Map<Integer, String> towns, short difficulty, String[] professions) {
        this.money = config.startingMoney;
        this.members = new ArrayList<>(config.teamMemberCount);

        // Add members
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

        this.statusContainers = statusContainers;
        this.events = events;
        this.towns = towns;
        this.inventory = new Inventory();
        this.difficulty = difficulty;
        this.config = config;
    }

    /**
     * This constructor is used when loading an existing Game
     * @param data SaveData instance
     */
    public Game(Game data, List<StatusContainer> statusContainers, List<Event> events, Map<Integer, String> towns, List<Member> memberData, Config config) {
        this.events = events;
        this.statusContainers = statusContainers;
        this.inventory = data.inventory;
        this.money = data.money;
        this.currentMile = data.currentMile;
        this.difficulty = data.difficulty;
        this.members = memberData;
        this.towns = towns;
        this.config = config;
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

    public void onGameFinished(Path gameData, Path memberData) {
        // Remove files
        gameData.toFile().delete();
        memberData.toFile().delete();
    }

    /**
     * Ticks all the game's functions
     */
    public void tickGame() {
        // This is meant to be a recursive function
        this.currentMile++;
        if (this.currentMile >= this.config.miles) {
            // TODO Queue victory screen
            return;
        }

        if (this.towns.containsKey(this.currentMile)) {
            // TODO Queue town screen
            // TODO Queue Shop screen
            this.renderQueue.add(new StoreScene());
            return;
        }

        Event.selectRandomEvent();

        // Tick events
        if (!EventManager.currentEvents.isEmpty()) {
            // Check twice in case an event adds a second event
            for (int i = 0; i < 2; i++) {
                for (Event event : EventManager.currentEvents) {
                    // TODO Queue Event Screen
                }
            }
            events.clear();
        }

        // Check for game over
        if (this.members.isEmpty()) {
            // TODO Game Over
            //onGameFinished();
        }
    }
}
