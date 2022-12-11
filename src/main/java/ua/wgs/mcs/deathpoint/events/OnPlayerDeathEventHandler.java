package ua.wgs.mcs.deathpoint.events;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ua.wgs.mcs.deathpoint.DeathPoint;
import ua.wgs.mcs.deathpoint.translation.TranslationConfig;

public final class OnPlayerDeathEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        Server server = player.getServer();
        Location playerLocation = player.getLocation();
        World playerWorld = playerLocation.getWorld();
        TranslationConfig translation = DeathPoint.translation();

        String broadcastTemplate = translation.getString("PlayerDeathAtLocation.broadcast");
        String playerSuggestTemplate = translation.getString("PlayerDeathAtLocation.components.player.suggest");
        String locationTemplate = translation.getString("PlayerDeathAtLocation.components.location.template");
        String locationHoverTemplate = translation.getString("PlayerDeathAtLocation.components.location.hover");
        String locationSuggestTemplate = translation.getString("PlayerDeathAtLocation.components.location.suggest");
        String worldTemplate = translation.getString("PlayerDeathAtLocation.components.world." +
                nameOfEnvironment(playerWorld.getEnvironment()));

        String playerSuggest = playerSuggestTemplate.replace("<name>", player.getName());

        String locationSuggest = locationSuggestTemplate
                .replace("<x>", String.valueOf((int) playerLocation.getX()))
                .replace("<y>", String.valueOf((int) playerLocation.getY()))
                .replace("<z>", String.valueOf((int) playerLocation.getZ()))
                .replace("<world>", keyOfEnvironment(playerWorld.getEnvironment()));

        Component playerLocationComponent = MiniMessage.miniMessage().deserialize(locationTemplate,
                Placeholder.parsed("x", String.valueOf((int) playerLocation.getX())),
                Placeholder.parsed("y", String.valueOf((int) playerLocation.getY())),
                Placeholder.parsed("z", String.valueOf((int) playerLocation.getZ())));

        Component locationHoverComponent = MiniMessage.miniMessage().deserialize(locationHoverTemplate);

        Component worldComponent = MiniMessage.miniMessage().deserialize(worldTemplate);

        Component messageComponent = MiniMessage.miniMessage().deserialize(broadcastTemplate,
                Placeholder.component("player", Component
                        .text(player.getName())
                        .hoverEvent(HoverEvent.showEntity(Key.key("minecraft:player"),
                                player.getUniqueId(), Component.text(player.getName())))
                        .clickEvent(ClickEvent.suggestCommand(playerSuggest))),
                Placeholder.component("location", playerLocationComponent
                        .hoverEvent(HoverEvent.showText(locationHoverComponent))
                        .clickEvent(ClickEvent.suggestCommand(locationSuggest))),
                Placeholder.component("world", worldComponent));

        server.broadcast(messageComponent);
    }

    private String nameOfEnvironment(World.Environment environment) {
        return switch (environment) {
            case NORMAL -> "overworld";
            case NETHER -> "the_nether";
            case THE_END -> "the_end";
            default -> environment.name();
        };
    }

    private String keyOfEnvironment(World.Environment environment) {
        return switch (environment) {
            case NORMAL -> "minecraft:overworld";
            case NETHER -> "minecraft:the_nether";
            case THE_END -> "minecraft:the_end";
            default -> environment.name();
        };
    }
}

