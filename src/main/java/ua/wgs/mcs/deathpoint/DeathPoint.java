package ua.wgs.mcs.deathpoint;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ua.wgs.mcs.deathpoint.commands.DeathPointCommand;
import ua.wgs.mcs.deathpoint.events.OnPlayerDeathEventHandler;
import ua.wgs.mcs.deathpoint.translation.TranslationConfig;

import java.io.File;

public final class DeathPoint extends JavaPlugin {
    private static DeathPoint instance;
    private static TranslationConfig translation;

    @Override
    public void onEnable() {
        instance = this;

        reloadConfiguration();

        registerEvents(new OnPlayerDeathEventHandler());

        registerCommand("deathpoint", new DeathPointCommand());
    }

    @SuppressWarnings("SameParameterValue")
    private void registerCommand(String command, DeathPointCommand commandImplementation) {
        PluginCommand pluginCommand = getCommand(command);
        assert pluginCommand != null;
        pluginCommand.setExecutor(commandImplementation);
        pluginCommand.setTabCompleter(commandImplementation);
    }

    @SuppressWarnings("SameParameterValue")
    private void registerEvents(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    private void saveFile(String path) {
        File file = new File(getDataFolder(), path);
        if (!file.exists()) {
            saveResource(path, false);
        }
    }

    public void saveTranslations() {
        saveFile("lang/en_us.yml");
        saveFile("lang/ru_ru.yml");
    }

    public void reloadConfiguration() {
        saveFile("config.yml");
        reloadConfig();

        FileConfiguration configuration = getConfig();
        translation = new TranslationConfig(configuration.getString("lang", "en_us.yml"));
    }

    public static TranslationConfig translation() {
        return translation;
    }

    public static DeathPoint plugin() {
        return instance;
    }
}
