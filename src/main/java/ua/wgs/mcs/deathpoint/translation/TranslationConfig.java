package ua.wgs.mcs.deathpoint.translation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ua.wgs.mcs.deathpoint.DeathPoint;

import java.io.File;
import java.io.IOException;

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
}
