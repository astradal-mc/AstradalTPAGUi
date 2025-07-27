package net.astradal.astradalTPAGui.commands;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import net.astradal.astradalTPAGui.AstradalTPAGui;

public final class Root {
    //define command tree here
    public static LiteralCommandNode<CommandSourceStack> create(AstradalTPAGui plugin) {
        return Commands.literal("tpagui")
            .requires(sender -> sender.getSender().hasPermission("astradal.command.tpagui"))

            //if targets specified
            .then(Commands.argument("targets", ArgumentTypes.players())
                .executes(new TPAGuiForTargets(plugin)))

            // reload command
            .then(Commands.literal("reload")
                .requires(sender -> sender.getSender().hasPermission("astradal.command.reload"))
                .executes(new Reload(plugin)))

            // version command
            .then(Commands.literal("version")
                .requires(sender -> sender.getSender().hasPermission("astradal.command.version"))
                .executes(new Version(plugin)))

            //self, default case
            .executes(new TPAGuiForSelf(plugin))
            .build();
    }
}
