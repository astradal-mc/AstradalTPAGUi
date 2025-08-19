package net.astradal.astradalTPAGui.listeners;

import com.Zrips.CMI.events.CMIAsyncPlayerTeleportEvent;
import com.Zrips.CMI.events.CMICancellableEvent;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.services.TPAScrollService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class CMITeleportListener implements Listener {
    public CMITeleportListener(AstradalTPAGui plugin, TPAScrollService scrollService) {
        this.plugin = plugin;
        this.scrollService = scrollService;
    }
    private final AstradalTPAGui plugin;
    private final TPAScrollService scrollService;

    @EventHandler
    public void onTeleport(CMIAsyncPlayerTeleportEvent event) {
        plugin.getLogger().info("CMIAsyncPlayerTeleportEvent fired for " + event.getPlayer().getName());
        Player player = event.getPlayer();

        if (scrollService.shouldConsume(player)) {
            scrollService.consumeScroll(player);
        }
    }

    @EventHandler
    public void onTeleportCancelled(CMICancellableEvent event) {
        plugin.getLogger().info("CMICancellableEvent fired: " + event.getEventName());
    }

}
