package net.astradal.astradalTPAGUi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.astradal.astradalTPAGUi.AstradalTPAGUi;

public class CommandReload implements Command<CommandSourceStack> {

    private final AstradalTPAGUi plugin;
    public CommandReload(AstradalTPAGUi plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        plugin.reloadConfig();
        context.getSource().getSender().sendMessage("AstradalTPAGui reloaded");
        return Command.SINGLE_SUCCESS;
    }
}
