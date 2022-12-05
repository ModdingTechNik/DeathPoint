package ua.wgs.mcs.deathpoint;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ua.wgs.mcs.deathpoint.events.OnPlayerDeathEventHandler;

@SuppressWarnings("unused")
public final class DeathPoint extends JavaPlugin {

    private static DeathPoint plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();

        saveDefaultConfig();

        pluginManager.registerEvents(new OnPlayerDeathEventHandler(), this);
    }

    public static DeathPoint getPlugin() {
        return plugin;
    }
}
