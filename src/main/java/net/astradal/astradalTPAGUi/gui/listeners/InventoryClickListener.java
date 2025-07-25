package net.astradal.astradalTPAGUi.gui.listeners;

import net.astradal.astradalTPAGUi.gui.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();

        if (inventory == null || !(inventory.getHolder(false) instanceof GUI gui)) {
            return;
        }

        event.setCancelled(true);

        Player clicker = (Player) event.getWhoClicked();
    }
}
