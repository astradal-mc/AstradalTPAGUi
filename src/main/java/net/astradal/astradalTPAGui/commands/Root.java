package net.astradal.astradalTPAGui.commands;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.services.TPAScrollService;

public final class Root {
    //define command tree here
    public static LiteralCommandNode<CommandSourceStack> create(AstradalTPAGui plugin, TPAScrollService scrollService) {
        return Commands.literal("tpagui")
            .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.tpagui"))

            // targets specified, opens gui for them
            .then(Commands.argument("targets", ArgumentTypes.players())
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.tpagui"))
                .executes(new TPAGuiForTargets(plugin)))

            // reload command
            .then(Commands.literal("reload")
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.reload"))
                .executes(new Reload(plugin)))

            // version command
            .then(Commands.literal("version")
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.version"))
                .executes(new Version(plugin)))

            // getscroll command
            .then(Commands.literal("getscroll")
                .requires(sender -> sender.getSender().hasPermission("astradal.tpagui.command.getscroll"))
                .executes(new GetScroll(scrollService)))

            //self, default case
            .executes(new TPAGuiForSelf(plugin))
            .build();
    }
}
