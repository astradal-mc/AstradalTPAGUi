package net.astradal.astradalTPAGui.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.services.TPAGuiService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TPAGuiForSelf implements Command<CommandSourceStack> {
    private final TPAGuiService service;

    public TPAGuiForSelf(AstradalTPAGui plugin) {
        this.service = new TPAGuiService(plugin);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        final CommandSender sender = context.getSource().getSender();
        final Player player = service.requirePlayer(sender);
        if (player == null) return 0;

        return service.openGuiFor(player) ? Command.SINGLE_SUCCESS : 0;
    }
}
