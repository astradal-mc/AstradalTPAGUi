package net.astradal.astradalTPAGUi.gui;

import net.astradal.astradalTPAGUi.AstradalTPAGUi;
import net.kyori.adventure.text.Component;
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
        int playerCount = plugin.getServer().getOnlinePlayers().size();
        int nextMultipleOf9 = (((playerCount+8)/9)*9);

        this.inventory = plugin.getServer().createInventory(this, nextMultipleOf9, Component.text("Tpa Menu"));


        List<Player> players = plugin.getServer().getOnlinePlayers()
            .stream()
            .map(p -> (Player) p)
            .filter(p -> !p.getUniqueId().equals(viewer.getUniqueId()))
            .toList();

        NamespacedKey key = new NamespacedKey(plugin, "tpa_target");

        IntStream.range(0, players.size()).forEach(i -> {
            Player player = players.get(i);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();

            if(meta != null) {
                meta.setOwningPlayer(plugin.getServer().getOfflinePlayer(player.getName()));
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());
                meta.displayName(
                    Component.text("TPA to ")
                        .append(Component.text(player.getName() )));
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
