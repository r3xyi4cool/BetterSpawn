package me.rexyiscool.betterSpawns;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterSpawns extends JavaPlugin {

    public int teleportDelay;
    public String setspawnPermission;
    public String reloadPermission;
    public int teleportTaskId;
    public int movementCheckTaskId;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        if (getConfig() == null) {
            getLogger().severe("Config file is missing!");
        } else {
            getLogger().info("Config file loaded successfully.");
        }

        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("spawnreload").setExecutor(new ReloadCommand(this));

        reloadPermission = getConfig().getString("spawn-reload");
        teleportDelay = getConfig().getInt("teleport-delay");
        setspawnPermission = getConfig().getString("setspawnperm");
        getLogger().info("The Better Spawns Plugin is enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Better Spawns plugin has been shutdown successfully");
    }
}

