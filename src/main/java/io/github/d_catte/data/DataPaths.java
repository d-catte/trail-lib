package io.github.d_catte.data;

import java.nio.file.Path;

public record DataPaths(Path scenePath, Path statusPath, Path savesDirectoryPath, Path itemsPath, Path shopItemsPath, Path townsPath, Path eventsPath, Path professionPath, Path config) {
}
