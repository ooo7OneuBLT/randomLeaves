package me.ooo7Oneu.ramdomLeaves;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ramdomLeaves extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("randomLeavesがロードされました");
        getLogger().info("pluginVersion" + checkPluginVersion());

        Listener eventhandler = new EventListener(this);
        getServer().getPluginManager().registerEvents(eventhandler, this);

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String checkPluginVersion() {
        return "0.1";
    }

}
