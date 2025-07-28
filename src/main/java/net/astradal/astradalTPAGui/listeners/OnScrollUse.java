package net.astradal.astradalTPAGui.listeners;

import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.service.TPAScrollService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnScrollUse implements Listener {

    private final AstradalTPAGui plugin;
    private final TPAScrollService scrollService;
    public OnScrollUse(AstradalTPAGui plugin, TPAScrollService scrollService) {
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
