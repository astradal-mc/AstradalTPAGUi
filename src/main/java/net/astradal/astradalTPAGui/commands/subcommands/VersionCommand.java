package net.astradal.astradalTPAGui.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class VersionCommand implements Command<CommandSourceStack> {

    private final AstradalTPAGui plugin;
    public VersionCommand(AstradalTPAGui plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        // get version
        String version = plugin.getPluginMeta().getVersion();
        // get plugin name to make this more future-proof
        String name = plugin.getPluginMeta().getName();

        // send text to command sender
        context.getSource().getSender().sendMessage(
            Component.text(name, NamedTextColor.GOLD)
            .append(Component.text(" - ", NamedTextColor.WHITE))
            .append(Component.text(version, NamedTextColor.YELLOW)));

        return Command.SINGLE_SUCCESS;
    }
}