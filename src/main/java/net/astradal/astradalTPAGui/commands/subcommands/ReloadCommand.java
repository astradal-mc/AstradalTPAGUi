package net.astradal.astradalTPAGui.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.astradal.astradalTPAGui.AstradalTPAGui;

public final class ReloadCommand implements Command<CommandSourceStack> {

    private final AstradalTPAGui plugin;
    public ReloadCommand(AstradalTPAGui plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        plugin.reloadConfig();
        context.getSource().getSender().sendMessage("AstradalTPAGui reloaded");
        return Command.SINGLE_SUCCESS;
    }
}
