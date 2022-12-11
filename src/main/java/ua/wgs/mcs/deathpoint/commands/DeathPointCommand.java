package ua.wgs.mcs.deathpoint.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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
        if (args.length < 1) {
            return false;
        }

        if (args[0].equals("reload")) {
            if (args.length < 2) {
                return false;
            }

            if (args[1].equals("config")) {
                DeathPoint.plugin().reloadConfiguration();
                sender.sendMessage(getTranslationComponent("DeathPoint.reload.config"));
                return true;
            }
            else if (args[1].equals("translation")) {
                DeathPoint.translation().reload();
                sender.sendMessage(getTranslationComponent("DeathPoint.reload.translation"));
                return true;
            }
        }
        else if (args[0].equals("language")) {
            if (args.length < 3) {
                String translationName = DeathPoint.translation().name();
                sender.sendMessage(getTranslationComponent("DeathPoint.language.current", "language", translationName));
                return true;
            }

            if (args[1].equals("set")) {
                if (TranslationConfig.exists(args[2])) {
                    DeathPoint.plugin().setLanguage(args[2]);
                    sender.sendMessage(getTranslationComponent("DeathPoint.language.change"));
                }
                else {
                    sender.sendMessage(getTranslationComponent("DeathPoint.language.notFound"));
                }

                return true;
            }
        }

        return false;
    }

    private Component getTranslationComponent(String path) {
        TranslationConfig translation = DeathPoint.translation();
        return MiniMessage.miniMessage().deserialize(translation.getString(path));
    }

    @SuppressWarnings("SameParameterValue")
    private Component getTranslationComponent(String path, String placeholder, String value) {
        TranslationConfig translation = DeathPoint.translation();
        return MiniMessage.miniMessage().deserialize(translation.getString(path),
                Placeholder.component(placeholder, Component.text(value)));
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> available = new ArrayList<>();

        if (args.length == 1) {
            available.add("reload");
            available.add("language");
        }

        if (args.length == 2) {
            if (args[0].equals("reload")) {
                available.add("config");
                available.add("translation");
            }
            if (args[0].equals("language")) {
                available.add("set");
            }
        }

        if (args.length == 3) {
            if (args[0].equals("language") && args[1].equals("set")) {
                available.addAll(TranslationConfig.list());
            }
        }

        return available.stream().filter(arg -> arg.contains(args[args.length - 1])).collect(Collectors.toList());
    }
}
