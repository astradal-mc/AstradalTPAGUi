package net.astradal.astradalTPAGui.service;

import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.gui.GUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TPAGuiService {

    private final AstradalTPAGui plugin;

    public TPAGuiService(AstradalTPAGui plugin) {
        this.plugin = plugin;
    }

    public boolean openGuiFor(Player target) {
        if (plugin.getServer().getOnlinePlayers().size() == 1) {
            target.sendMessage(Component.text("Only you online. No one to teleport to.", NamedTextColor.RED));
            return false;
        }

        GUI gui = new GUI(plugin, target);
        target.openInventory(gui.getInventory());
        return true;
    }

    public Player requirePlayer(CommandSender sender) {
        if (sender instanceof Player player) {
            return player;
        } else {
            sender.sendMessage(Component.text("Only players can use this command.", NamedTextColor.RED));
            return null;
        }
    }
}