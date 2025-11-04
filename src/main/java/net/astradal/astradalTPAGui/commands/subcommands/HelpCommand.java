package net.astradal.astradalTPAGui.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.astradal.astradalTPAGui.commands.TPAGuiPermissions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashMap;
import java.util.Map;

public final class HelpCommand {

    // Updated map with all current subcommands
    private static final Map<String, String> DESCRIPTIONS = new LinkedHashMap<>() {{
        put("getscroll", "Gives you a TPA scroll.");
        put("reload", "Reloads the plugin configuration.");
        put("version", "Displays the plugin version.");
        put("help", "Shows this help message.");
    }};

    /**
     * Builds the '/tpagui help' command node.
     */
    public static LiteralArgumentBuilder<CommandSourceStack> build(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("help")
            .requires(TPAGuiPermissions.requires("help"))
            .executes(ctx -> execute(ctx, dispatcher));
    }

    /**
     * Executes the help command logic.
     */
    public static int execute(CommandContext<CommandSourceStack> ctx, CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandSender sender = ctx.getSource().getSender();

        sender.sendMessage(Component.text("--- AstradalTPAGui Help ---", NamedTextColor.GOLD));

        // Find the '/tpagui' command node in the command tree
        CommandNode<CommandSourceStack> tpaguiNode = dispatcher.getRoot().getChild("tpagui");

        if (tpaguiNode == null) {
            sender.sendMessage(Component.text("Error: Could not find command information.", NamedTextColor.RED));
            return 0;
        }

        // Iterate through all registered subcommands of /tpagui
        for (CommandNode<CommandSourceStack> node : tpaguiNode.getChildren()) {
            if (!(node instanceof LiteralCommandNode<?> literalNode)) continue;

            String subcommand = literalNode.getLiteral();

            // Only show commands the player has permission to use
            if (!TPAGuiPermissions.has(sender, subcommand)) continue;

            String description = DESCRIPTIONS.getOrDefault(subcommand, "No description available.");
            String usage = "/tpa " + subcommand;

            sender.sendMessage(
                Component.text(usage, NamedTextColor.YELLOW)
                    .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(description, NamedTextColor.GRAY))
            );
        }

        return Command.SINGLE_SUCCESS;
    }
}