package ua.wgs.mcs.deathpoint.translation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ua.wgs.mcs.deathpoint.DeathPoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TranslationConfig {
    private final File file;
    private FileConfiguration configuration;

    public TranslationConfig(String name) {
       this.file = new File(DeathPoint.plugin().getDataFolder(), "lang/" + name);
       reload();
    }

    public void reload() {
        DeathPoint.plugin().saveTranslations();

        try {
            if (!file.exists() && !file.createNewFile()) throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create translation configuration");
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public @NotNull String getString(@NotNull String path) {
        return configuration.getString(path, path);
    }

    public String name() {
        return file.getName();
    }

    public static boolean exists(String name) {
        return new File(DeathPoint.plugin().getDataFolder(), "lang/" + name).exists();
    }

    public static List<String> list() {
        File langDirectory = new File(DeathPoint.plugin().getDataFolder(), "lang");
        List<String> names = new ArrayList<>();
        for (File file : Objects.requireNonNull(langDirectory.listFiles())) {
            if (file.exists() && file.isFile()) {
                names.add(file.getName());
            }
        }
        return names;
    }
}
