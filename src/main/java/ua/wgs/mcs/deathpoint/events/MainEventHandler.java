package ua.wgs.mcs.deathpoint.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ua.wgs.mcs.deathpoint.DeathPoint;

public final class MainEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        FileConfiguration config = DeathPoint.getPlugin().getConfig();
        Player player = e.getPlayer();
        Server server = player.getServer();
        Location playerLocation = player.getLocation();

        String pattern = config.getString("messages.playerDeathPoint", "Player {PLAYER} died at coordinates {POINT}");
        String message = pattern
                .replace("{PLAYER}", player.getName())
                .replace("{X}", Integer.toString(playerLocation.getBlockX()))
                .replace("{Y}", Integer.toString(playerLocation.getBlockY()))
                .replace("{Z}", Integer.toString(playerLocation.getBlockZ()));

        server.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
