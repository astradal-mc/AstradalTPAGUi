package net.astradal.astradalTPAGui.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.astradal.astradalTPAGui.AstradalTPAGui;

public final class Reload implements Command<CommandSourceStack> {

    private final AstradalTPAGui plugin;
    public Reload(AstradalTPAGui plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        plugin.reloadConfig();
        context.getSource().getSender().sendMessage("AstradalTPAGui reloaded");
        return Command.SINGLE_SUCCESS;
    }
}
