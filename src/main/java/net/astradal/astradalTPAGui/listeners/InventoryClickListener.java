package net.astradal.astradalTPAGui.listeners;

import net.astradal.astradalTPAGui.gui.GUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public final class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();
        if (inventory == null || !(inventory.getHolder(false) instanceof GUI gui)) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() != Material.PLAYER_HEAD ) return;

        Player clicker = (Player) event.getWhoClicked();

        ItemMeta meta = clicked.getItemMeta();
        if (!(meta instanceof SkullMeta skullMeta)) return;

        NamespacedKey key = new NamespacedKey(gui.plugin, "tpa_target");

        String uuidStr = skullMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (uuidStr == null) {
            clicker.sendMessage(Component.text("Could not find target player", NamedTextColor.RED));
            return;
        }

        UUID uuid = UUID.fromString(uuidStr);
        Player target = Bukkit.getPlayer(uuid);

        if (target != null && target.isOnline()) {
            clicker.sendMessage(Component.text("Request sent to ", NamedTextColor.YELLOW).
                append(Component.text(target.getName(), NamedTextColor.GOLD)));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cmi tpa " + target.getName() + " " + clicker.getName());
        }
    }
}
