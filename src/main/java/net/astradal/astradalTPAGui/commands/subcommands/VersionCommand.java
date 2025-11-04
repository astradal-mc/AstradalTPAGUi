package net.astradal.astradalTPAGui.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.commands.TPAGuiPermissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class VersionCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> build(AstradalTPAGui plugin) {
        return Commands.literal("version")
            .requires(TPAGuiPermissions.requires("version"))
            .executes(ctx -> execute(ctx, plugin));
    }

    public static int execute(CommandContext<CommandSourceStack> context, AstradalTPAGui plugin) {

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