package net.astradal.astradalTPAGui.services;

import com.Zrips.CMI.CMI;
import net.astradal.astradalTPAGui.AstradalTPAGui;
import net.astradal.astradalTPAGui.gui.GuiInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class TPAScrollService {
    private final AstradalTPAGui plugin;
    private final NamespacedKey key;

    private final Set<UUID> scrollUsed = new HashSet<>();
    private final Map<UUID, BukkitTask> teleportTimeouts = new HashMap<>();
    private final Set<UUID> acceptedTeleport = new HashSet<>();

    public TPAScrollService(AstradalTPAGui plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "tpa_scroll");
    }

    public boolean isTPAScroll(ItemStack item) {
        if (item == null || item.getType() != Material.PAPER) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public ItemStack createTPAScrollItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("TPA Scroll", NamedTextColor.LIGHT_PURPLE));
        meta.lore(List.of(
            Component.text("Right-click to open the TPA menu", NamedTextColor.GRAY),
            Component.text("Consumed on teleport", NamedTextColor.DARK_GRAY)
        ));
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public void markTeleportAccepted(Player player) {
        UUID uuid = player.getUniqueId();
        acceptedTeleport.add(uuid);

        // Cancel any existing timeout task
        if (teleportTimeouts.containsKey(uuid)) {
            teleportTimeouts.get(uuid).cancel();
        }

        // Get warmup duration from CMI config
        int warmupSeconds = CMI.getInstance().getConfig().getInt("Optimizations.Teleport.Tpa.Warmup", 5);
        long warmupTicks = warmupSeconds * 20L;

        // Add a small buffer to the timeout to avoid false negatives
        long timeoutTicks = warmupTicks + (20L);

        BukkitTask timeoutTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (shouldConsume(player)) {
                plugin.getLogger().info("Teleport timeout expired — clearing scroll state for " + player.getName());
                scrollUsed.remove(uuid);
                acceptedTeleport.remove(uuid);
            }
            teleportTimeouts.remove(uuid);
        }, timeoutTicks);

        teleportTimeouts.put(uuid, timeoutTask);
    }

    public void markUsedScroll(Player player) {
        scrollUsed.add(player.getUniqueId());
    }

    public boolean usedScroll(Player player) {
        return scrollUsed.contains(player.getUniqueId());
    }

    public boolean openGuiIfScroll(Player player, ItemStack item) {
        if (!isTPAScroll(item)) return false;

        GuiInventory gui = new GuiInventory(plugin, player);
        player.openInventory(gui.getInventory());
        markUsedScroll(player); // track the scroll usage
        return true;
    }

    public boolean shouldConsume(Player player) {
        return scrollUsed.contains(player.getUniqueId()) && acceptedTeleport.contains(player.getUniqueId());
    }

    public void consumeScroll(Player player) {
        scrollUsed.remove(player.getUniqueId());
        acceptedTeleport.remove(player.getUniqueId());

        PlayerInventory inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) continue;

            if (isTPAScroll(item)) {
                int amount = item.getAmount();
                if (amount > 1) {
                    item.setAmount(amount - 1);
                } else {
                    inv.setItem(i, null); // fully remove if only 1
                }
                player.sendMessage(Component.text("Your TPA Scroll has been used.", NamedTextColor.GRAY));
                break;
            }
        }
    }

}
