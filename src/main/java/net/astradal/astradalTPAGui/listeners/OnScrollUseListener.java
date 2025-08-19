package net.astradal.astradalTPAGui.listeners;

import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.services.TPAScrollService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnScrollUseListener implements Listener {

    private final AstradalTPAGui plugin;
    private final TPAScrollService scrollService;
    public OnScrollUseListener(AstradalTPAGui plugin, TPAScrollService scrollService) {
        this.plugin = plugin;
        this.scrollService = scrollService;
    }

    @EventHandler
    public void onScrollUse(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType().isAir()) return;

        Player player = event.getPlayer();

        if (scrollService.openGuiIfScroll(player, item)) {
            event.setCancelled(true); // Prevent default right-click behavior if scroll was used
            plugin.getLogger().info("Player " + player.getName() + " used a scroll.");
            scrollService.markUsedScroll(player);
        }
    }

}
