package ua.wgs.mcs.deathpoint.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ua.wgs.mcs.deathpoint.DeathPoint;

public final class OnPlayerDeathEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        Server server = player.getServer();
        Location playerLocation = player.getLocation();

        String pattern = DeathPoint.getPlugin().getConfig().getString(
                "messages.playerDeathAtPoint",
                "<gray><player><gray/> died at <green><location><green/>");

        Component message = MiniMessage.miniMessage().deserialize(pattern,
                Placeholder.component("player", Component
                        .text(player.getName())
                        .hoverEvent(HoverEvent.showText(Component.text(player.getName() + "\n" + player.getUniqueId())))
                        .clickEvent(ClickEvent.suggestCommand("/tell " + player.getName() + " "))),
                Placeholder.component("location", Component
                        .text(LocationToRawString(playerLocation))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to suggest command")))
                        .clickEvent(ClickEvent.suggestCommand("/tp " + LocationToRawString(playerLocation)))));

        server.broadcast(message);
    }

    private static String LocationToRawString(Location playerLocation) {
        return String.valueOf((int)playerLocation.getX()) +
                ' ' +
                (int)playerLocation.getY() +
                ' ' +
                (int)playerLocation.getZ();
    }
}

