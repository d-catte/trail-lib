package io.github.d_catte.data;

import java.nio.file.Path;

/**
 * Paths to game data.
 * @param scenePath Path to files containing game scene information.
 * @param statusPath Path to member status effect files (i.e. disease).
 * @param savesDirectoryPath Path to game save files.
 * @param itemsPath Path to game item files (ammunition, foodstuffs, etc.)
 * @param landmarksPath Path to landmark description files.
 * @param eventsPath Path to game event files.
 * @param professionPath Path to member profession files.
 * @param config Path to game config file.
 */
public record DataPaths(Path scenePath, Path statusPath, Path savesDirectoryPath, Path itemsPath, Path landmarksPath, Path eventsPath, Path professionPath, Path config) {
}
