package net.astradal.astradalTPAGUi.gui;

import net.astradal.astradalTPAGUi.AstradalTPAGUi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public class GUI implements InventoryHolder{

    private final Inventory inventory;
    public final AstradalTPAGUi plugin;

    public GUI(AstradalTPAGUi plugin, Player viewer) {
        this.plugin = plugin;
        int playerCount = plugin.getServer().getOnlinePlayers().size()-1; // -1 to remove yourself from the list because your head is not displayed

        // Inventories are in multiples of nines. Add 8 to round up, and divide and multiply to get the nearest multiple.
        int nextMultipleOf9 = (((playerCount+8)/9)*9);

        // Initialize inventory
        this.inventory = plugin.getServer().createInventory(this, nextMultipleOf9, Component.text("               TPA Menu", NamedTextColor.BLACK));

        // Get all the online players as a list, filtering for yourself
        List<Player> players = plugin.getServer().getOnlinePlayers()
            .stream()
            .map(p -> (Player) p)
            .filter(p -> !p.getUniqueId().equals(viewer.getUniqueId()))
            .toList();

        // Setup Key for PDC
        NamespacedKey key = new NamespacedKey(plugin, "tpa_target");

        List<Component> lore = List.of(
            Component.text("Click to send TPA request", NamedTextColor.GREEN)
        );

        // Iterate over each player from the list and add their head to the GUI, saving there UUID as a string in the PDC
        IntStream.range(0, players.size()).forEach(i -> {
            Player player = players.get(i);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();

            if(meta != null) {
                meta.setOwningPlayer(plugin.getServer().getOfflinePlayer(player.getName()));
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());
                meta.displayName(
                    Component.text(player.getName(), NamedTextColor.GOLD));
                meta.lore(lore);
                head.setItemMeta(meta);

            }

            this.inventory.setItem(i, head);
        });
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
