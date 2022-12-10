package ua.wgs.mcs.deathpoint.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ua.wgs.mcs.deathpoint.DeathPoint;
import ua.wgs.mcs.deathpoint.translation.TranslationConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class DeathPointCommand extends AbstractCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || !args[0].equals("reload") || args.length < 2) {
            return false;
        }

        if (args[1].equals("config")) {
            DeathPoint.plugin().reloadConfiguration();
            TranslationConfig translation = DeathPoint.translation();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(
                    translation.getString("DeathPoint.reload.config")));
            return true;
        }
        else if (args[1].equals("translation")) {
            TranslationConfig translation = DeathPoint.translation();
            translation.reload();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(
                    translation.getString("DeathPoint.reload.translation")));
            return true;
        }

        return false;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> available = new ArrayList<>();

        if (args.length == 1) {
            available.add("reload");
        }

        if (args.length == 2) {
            available.add("config");
            available.add("translation");
        }

        return available.stream().filter(str -> str.contains(args[args.length - 1])).collect(Collectors.toList());
    }
}
