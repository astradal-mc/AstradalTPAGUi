package net.astradal.astradalTPAGUi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.astradal.astradalTPAGUi.AstradalTPAGUi;
import net.astradal.astradalTPAGUi.gui.GUI;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandRootTPAGui implements Command<CommandSourceStack> {

    //plugin instance constructor injection
    private final AstradalTPAGUi plugin;
    public CommandRootTPAGui(AstradalTPAGUi plugin) {
        this.plugin = plugin;
    }

    //define command tree here
    public static LiteralCommandNode<CommandSourceStack> create(AstradalTPAGUi plugin) {
        return
            //this command
            Commands.literal("tpagui")
                .requires(sender -> sender.getSender().hasPermission("astradalTPAGui.use"))
                .then(Commands.argument("targets", ArgumentTypes.players())
                    .executes(new CommandRootTPAGui(plugin)))

                //reload command
            .then(Commands.literal("reload")
                .requires(sender -> sender.getSender().hasPermission("astradalTAPGui.reload"))
                .executes(new CommandReload(plugin)))
            .build();
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final PlayerSelectorArgumentResolver targetResolver = context.getArgument("targets", PlayerSelectorArgumentResolver.class);
        final List<Player> targets = targetResolver.resolve(context.getSource());
        final CommandSender sender = context.getSource().getSender();

        for (final Player target : targets) {
            target.sendRichMessage("TPAGui");
            GUI gui = new GUI(plugin);
            target.openInventory(gui.getInventory());


            sender.sendRichMessage("Opened TPAGui for <target>!",
                Placeholder.component("target", target.name())
            );
        }
        return Command.SINGLE_SUCCESS;
    }
}
