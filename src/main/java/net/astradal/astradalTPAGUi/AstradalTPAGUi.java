package net.astradal.astradalTPAGUi;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.astradal.astradalTPAGUi.commands.CommandRootTPAGui;
import net.astradal.astradalTPAGUi.gui.listeners.InventoryClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AstradalTPAGUi extends JavaPlugin {

    @Override
    public void onEnable() {
        saveResource("config.yml", false);

        //register command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
            commands.registrar().register(CommandRootTPAGui.create(this)));

        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
