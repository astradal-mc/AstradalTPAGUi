package net.astradal.astradalTPAGui.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.commands.TPAGuiPermissions;

public final class ReloadCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> build(AstradalTPAGui plugin) {
        return Commands.literal("reload")
            .requires(TPAGuiPermissions.requires("reload"))
            .executes(ctx -> execute(ctx, plugin));
    }

    public static int execute(CommandContext<CommandSourceStack> context, AstradalTPAGui plugin) {
        plugin.reloadConfig();
        context.getSource().getSender().sendMessage("AstradalTPAGui reloaded");
        return Command.SINGLE_SUCCESS;
    }
}
