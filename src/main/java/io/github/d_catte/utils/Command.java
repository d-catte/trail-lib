package io.github.d_catte.utils;

import io.github.d_catte.TrailApplication;
import io.github.d_catte.data.DataPaths;
import io.github.d_catte.game.Game;

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
 * - playSound {soundKey}: Plays a sound
 * - loadGame {gameName}: Loads a game file
 */
public class Command {
    private final String[] command;
    private Object[] passedObjects;
    public Command(String... command) {
        this.command = command;
    }

    public Command(Object[] passedObjects, String... command) {
        this(command);
        this.passedObjects = passedObjects;
    }

    public Runnable parseCommand() {
        Runnable command;
        switch (this.command[0]) {
            case "nextScreen" -> command = this::nextScreen;
            case "nextScene" -> command = this::nextScene;
            case "goTo" -> command = () -> goTo(Integer.parseInt(this.command[1]));
//            case "openStore" -> command =  this::openStore;
            case "playSound" -> command = () -> playSound(this.command[1]);
            case "loadGame" -> command = () -> loadGame(this.command[1]);
            case "createGame" -> command = () -> {
                createNewGame(
                        this.command[1],
                        this.command,
                        (DataPaths) this.passedObjects[0],
                        (Config) this.passedObjects[1],
                        (short) this.passedObjects[2],
                        (short) this.passedObjects[3]
                );
            };
            default -> {
                TrailApplication.LOGGER.warn("Invalid Command: {}", Arrays.toString(this.command));
                command = () -> {};
            }
        }
        return command;
    }

    private void nextScreen() {
        TrailApplication.gameInstance.nextScreen();
    }

    private void nextScene() {
        TrailApplication.gameInstance.nextScene();
    }

    private void goTo(int index) {
        int size = TrailApplication.gameInstance.currentScene.getScreens().size();
        if (index < size) {
            TrailApplication.gameInstance.currentScene.setScreenIndex(index);
        } else {
            TrailApplication.LOGGER.warn("GoTo index {} out of bounds of {}", index, size);
        }
    }

/*    private void openStore() {
        TrailApplication.gameInstance.renderQueue.add(new StoreScene());
    }*/

    private void playSound(String soundKey) {
        // TODO
    }

    private void loadGame(String gameName) {
        TrailApplication.SERIALIZER.loadGame(gameName);
    }

    /**
     * Creates a new game instance
     * @param gameName The save file name
     * @param args All args. Make sure to offset by 1!
     *             (Specific args will be separated in this function)
     * @param paths DataPaths instance
     * @param config Config instance
     * @param players Number of players.
     *                Ensure that the player's data is always first in membersNames
     *                and appear in the same order as in playerProfessions
     * @param difficulty The selected difficulty
     */
    private void createNewGame(
            String gameName,
            String[] args,
            DataPaths paths,
            Config config,
            short players,
            short difficulty
    ) {
        String[] playerProfessions = new String[players];
        String[] membersNames = new String[args.length - players - 2];
        // TODO See if the efficient arrayCopy implementation works
        System.arraycopy(args, 2, playerProfessions, 0, players);
        System.arraycopy(args, players + 2, membersNames, 0, args.length);

        TrailApplication.gameInstance = new Game(
                gameName,
                paths,
                config,
                membersNames,
                playerProfessions,
                difficulty
        );
    }
}
