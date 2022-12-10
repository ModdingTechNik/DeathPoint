package ua.wgs.mcs.deathpoint;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ua.wgs.mcs.deathpoint.events.OnPlayerDeathEventHandler;
import ua.wgs.mcs.deathpoint.translation.TranslationConfig;

@SuppressWarnings("unused")
public final class DeathPoint extends JavaPlugin {
    private static PluginEnvironment environment;
    private static TranslationConfig translation;

    @Override
    public void onEnable() {
        saveResources();

        PluginManager pluginManager = getServer().getPluginManager();
        FileConfiguration configuration = getConfig();

        environment = new PluginEnvironment(getDataFolder());
        translation = new TranslationConfig(configuration.getString("lang", "en_us.yml"));

        pluginManager.registerEvents(new OnPlayerDeathEventHandler(), this);
    }

    private void saveResources() {
        saveDefaultConfig();
        saveResource("lang/en_us.yml", false);
        saveResource("lang/ru_ru.yml", false);
    }

    public static PluginEnvironment getEnvironment() {
        return environment;
    }

    public static TranslationConfig getTranslation() {
        return translation;
    }
}
