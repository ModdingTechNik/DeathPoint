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
import ua.wgs.mcs.deathpoint.translation.TranslationConfig;

public final class OnPlayerDeathEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        Server server = player.getServer();
        Location playerLocation = player.getLocation();
        TranslationConfig translation = DeathPoint.getTranslation();

        String broadcastTemplate = translation.getString("PlayerDeathAtLocation.broadcast");
        String playerSuggestTemplate = translation.getString("PlayerDeathAtLocation.components.player.suggest");
        String locationTemplate = translation.getString("PlayerDeathAtLocation.components.location.template");
        String locationHoverTemplate = translation.getString("PlayerDeathAtLocation.components.location.hover");
        String locationSuggestTemplate = translation.getString("PlayerDeathAtLocation.components.location.suggest");

        String playerSuggest = playerSuggestTemplate.replace("<name>", player.getName());

        String locationSuggest = locationSuggestTemplate
                .replace("<x>", String.valueOf((int) playerLocation.getX()))
                .replace("<y>", String.valueOf((int) playerLocation.getY()))
                .replace("<z>", String.valueOf((int) playerLocation.getZ()));

        Component playerLocationComponent = MiniMessage.miniMessage().deserialize(locationTemplate,
                Placeholder.parsed("x", String.valueOf((int) playerLocation.getX())),
                Placeholder.parsed("y", String.valueOf((int) playerLocation.getY())),
                Placeholder.parsed("z", String.valueOf((int) playerLocation.getZ())));

        Component locationHoverComponent = MiniMessage.miniMessage().deserialize(locationHoverTemplate);

        Component messageComponent = MiniMessage.miniMessage().deserialize(broadcastTemplate,
                Placeholder.component("player", Component
                        .text(player.getName())
                        .hoverEvent(HoverEvent.showText(Component.text(player.getName() + "\n" + player.getUniqueId())))
                        .clickEvent(ClickEvent.suggestCommand(playerSuggest))),
                Placeholder.component("location", playerLocationComponent
                        .hoverEvent(HoverEvent.showText(locationHoverComponent))
                        .clickEvent(ClickEvent.suggestCommand(locationSuggest))));

        server.broadcast(messageComponent);
    }
}

