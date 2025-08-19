package net.astradal.astradalTPAGui.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.services.TPAGuiService;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class TPAGuiTargetsCommand implements Command<CommandSourceStack> {
    private final TPAGuiService service;

    public TPAGuiTargetsCommand(AstradalTPAGui plugin) {
        this.service = new TPAGuiService(plugin);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final CommandSender sender = context.getSource().getSender();
        final Player player = service.requirePlayer(sender);
        if (player == null) return 0;

        final PlayerSelectorArgumentResolver targetResolver = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
        final List<Player> targets = targetResolver.resolve(context.getSource());

        for (final Player target : targets) {
            if (service.openGuiFor(target)) {
                sender.sendRichMessage("Opened TPAGui for <target>!",
                    Placeholder.component("target", target.name()));
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
