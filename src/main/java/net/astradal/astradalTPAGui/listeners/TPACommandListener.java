package net.astradal.astradalTPAGui.listeners;

import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.services.TPAScrollService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class TPACommandListener implements Listener {
    private final TPAScrollService scrollService;
    private final AstradalTPAGui plugin;

    public TPACommandListener(AstradalTPAGui plugin, TPAScrollService scrollService) {
        this.plugin = plugin;
        this.scrollService = scrollService;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] parts = event.getMessage().split("\\s+");
        if (parts.length < 3) return;

        String command = parts[0].toLowerCase();
        String subCommand = parts[1].toLowerCase();

        if (!command.equals("/cmi") || !subCommand.equals("tpaccept")) return;

        String teleportingPlayerName = parts[2];
        Player scrollUser = Bukkit.getPlayerExact(teleportingPlayerName);
        if (scrollUser != null && scrollService.usedScroll(scrollUser)) {
            scrollService.markTeleportAccepted(scrollUser);
            scrollUser.sendMessage(Component.text("Your teleport request was accepted.", NamedTextColor.GRAY));
            plugin.getLogger().info("Marking " + scrollUser.getName() + " for scroll consumption.");
        }
    }

}
