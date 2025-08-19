package net.astradal.astradalTPAGui;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.astradal.astradalTPAGui.commands.TPAGuiCommand;
import net.astradal.astradalTPAGui.listeners.CMITeleportListener;
import net.astradal.astradalTPAGui.listeners.InventoryClickListener;
import net.astradal.astradalTPAGui.listeners.OnScrollUseListener;
import net.astradal.astradalTPAGui.listeners.TPACommandListener;
import net.astradal.astradalTPAGui.services.TPAScrollService;
import net.astradal.astradalTPAGui.utils.ConfigMigrationUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class AstradalTPAGui extends JavaPlugin {

    @Override
    public void onEnable() {
        // --- 1. Configuration ---
        saveDefaultConfig();
        // This handles adding new keys
        ConfigMigrationUtil.migrateConfigDefaults(this);
        // This handles updating the version key
        ConfigMigrationUtil.updateVersionInConfig(this);

        TPAScrollService scrollService = new TPAScrollService(this);
        //register command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
            commands.registrar().register(TPAGuiCommand.create(this, scrollService)));

        getServer().getPluginManager().registerEvents(new OnScrollUseListener(this, scrollService), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new CMITeleportListener(this, scrollService), this);
        getServer().getPluginManager().registerEvents(new TPACommandListener(this, scrollService), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
