package net.astradal.astradalTPAGui.commands;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.commands.subcommands.*;
import net.astradal.astradalTPAGui.services.TPAScrollService;

public final class TPAGuiCommand {
    //define command tree here
    public static LiteralCommandNode<CommandSourceStack> create(AstradalTPAGui plugin, TPAScrollService scrollService) {
        return Commands.literal("tpagui")
            .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.tpagui"))

            // targets specified, opens gui for them
            .then(Commands.argument("targets", ArgumentTypes.players())
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.tpagui"))
                .executes(new TPAGuiTargetsCommand(plugin)))

            // reload command
            .then(Commands.literal("reload")
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.reload"))
                .executes(new ReloadCommand(plugin)))

            // version command
            .then(Commands.literal("version")
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.version"))
                .executes(new VersionCommand(plugin)))

            // getscroll command
            .then(Commands.literal("getscroll")
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.getscroll"))
                .executes(new GetScrollCommand(scrollService)))

            //self, default case
            .executes(new TPAGuiSelfCommand(plugin))
            .build();
    }
}
