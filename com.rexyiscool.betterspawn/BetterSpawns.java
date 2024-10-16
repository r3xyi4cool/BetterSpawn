package me.rexyiscool.betterSpawns;

import me.rexyiscool.betterSpawns.commands.SpawnMenuCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public final class BetterSpawns extends JavaPlugin {

    public int teleportDelay;
    public String setspawnPermission;
    public String reloadPermission;
    public int teleportTaskId;
    public int movementCheckTaskId;

    private final Map<Player, ChatColor> playerColorMap = new HashMap<>();
    private ColorSelectionGUI colorSelectionGUI;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        if (getConfig() == null) {
            getLogger().severe("Config file is missing!");
        } else {
            getLogger().info("Config file loaded successfully.");
        }

        getCommand("bsetspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("bspawn").setExecutor(new SpawnCommand(this));
        getCommand("spawnreload").setExecutor(new ReloadCommand(this));
        getCommand("spawnmenu").setExecutor(new SpawnMenuCommand(this));  // Register the spawnmenu command

        reloadPermission = getConfig().getString("spawn-reload");
        teleportDelay = getConfig().getInt("teleport-delay");
        setspawnPermission = getConfig().getString("setspawnperm");

        colorSelectionGUI = new ColorSelectionGUI(this);
        Bukkit.getPluginManager().registerEvents(colorSelectionGUI, this);

        getLogger().info("The Better Spawns Plugin is enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Better Spawns plugin has been shut down successfully");
    }

    public void setPlayerColor(Player player, ChatColor color) {
        playerColorMap.put(player, color);
    }

    public ChatColor getPlayerColor(Player player) {
        return playerColorMap.getOrDefault(player, ChatColor.WHITE);  // Default to white if no color is set
    }

    public ColorSelectionGUI getColorSelectionGUI() {
        return colorSelectionGUI;
    }
}
