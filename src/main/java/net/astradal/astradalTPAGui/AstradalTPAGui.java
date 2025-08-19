package net.astradal.astradalTPAGui;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.astradal.astradalTPAGui.commands.Root;
import net.astradal.astradalTPAGui.listeners.CMITeleportListener;
import net.astradal.astradalTPAGui.listeners.InventoryClickListener;
import net.astradal.astradalTPAGui.listeners.OnScrollUse;
import net.astradal.astradalTPAGui.listeners.TPACommandListener;
import net.astradal.astradalTPAGui.services.TPAScrollService;
import org.bukkit.plugin.java.JavaPlugin;

public final class AstradalTPAGui extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        TPAScrollService scrollService = new TPAScrollService(this);
        //register command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
            commands.registrar().register(Root.create(this, scrollService)));

        getServer().getPluginManager().registerEvents(new OnScrollUse(this, scrollService), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new CMITeleportListener(this, scrollService), this);
        getServer().getPluginManager().registerEvents(new TPACommandListener(this, scrollService), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
