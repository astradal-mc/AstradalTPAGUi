package net.astradal.astradalTPAGui.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.astradal.astradalTPAGui.service.TPAScrollService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetScroll implements Command<CommandSourceStack> {
    private final TPAScrollService scrollService;

    public GetScroll(TPAScrollService scrollService) {
        this.scrollService = scrollService;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSender sender = context.getSource().getSender();
        if(!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only Players may use this command.", NamedTextColor.RED));
            return 0;
        }

        player.getInventory().addItem(scrollService.createTPAScrollItem());
        player.sendMessage(Component.text("You have been give na TPA Scroll.", NamedTextColor.GREEN));
        return Command.SINGLE_SUCCESS;
    }
}
