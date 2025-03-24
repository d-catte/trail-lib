package io.github.d_catte.utils;

import com.google.gson.Gson;
import io.github.d_catte.data.DataPaths;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Config {
    private static final String currentConfigVer = "0.1";
    public String defaultStatus;
    public String configVer;
    public float startingMoney;
    public int teamMemberCount;
    public int miles;
    public String language;

    public static Config getConfig(Gson gson, DataPaths paths) {
        try (FileReader reader = new FileReader(paths.config().toFile())) {
            Config instance = gson.fromJson(reader, Config.class);
            if (!instance.configVer.equals(currentConfigVer)) {
                migrateConfig(instance);
            }
            return instance;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void migrateConfig(Config configInstance) {}
}
