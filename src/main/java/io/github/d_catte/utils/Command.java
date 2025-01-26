package io.github.d_catte.utils;

import io.github.d_catte.TrailApplication;

import java.util.Arrays;

/**
 * Commands can be embedded into a json file as a string.
 * They are typically used for callbacks.
 * <p>
 * Commands:
 * - nextScreen: Goes to the next Screen in a scene if available
 * - nextScene: Goes to the next Scene if available
 * - goTo {screenIndex}: Goes to the specified index
 * - openStore: Sets the next Scene as the StoreScene
 * - playSound {soundPath}: Plays a sound
 * - loadGame {gameName}: Loads a game file
 */
public class Command {
    private final String[] command;
    public Command(String... command) {
        this.command = command;
    }

    public Runnable parseCommand() {
        Runnable command;
        switch (this.command[0]) {
            case "nextScreen" -> {}
            case "nextScene" -> {}
            case "goTo" -> {}
            case "openStore" -> {}
            case "playSound" -> {}
            case "loadGame" -> {}
            default -> {
                TrailApplication.LOGGER.warn("Invalid Command: {}", Arrays.toString(this.command));
                command = () -> {};
            }
        }
        return command;
    }
}
