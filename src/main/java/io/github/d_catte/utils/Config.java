package io.github.d_catte.utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Config {
    private static final String currentConfigVer = "0.1";
    public boolean webMode;
    public String defaultStatus;
    public String configVer;
    public float startingMoney;
    public int teamMemberCount;
    public int miles;
    public RenderingBackend renderingBackend;
    public String language;


    public Config() {}

    public static Config getConfig(Gson gson, Path path) {
        try (FileReader reader = new FileReader(path.toFile())) {
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

    public enum RenderingBackend {
        Swing,
        ImGUI,
    }
}
