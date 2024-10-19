package me.rexyiscool.betterSpawns;

import me.rexyiscool.betterSpawns.commands.SpawnMenuCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public final class BetterSpawns extends JavaPlugin {

    // Delay for teleportation in seconds
    public int teleportDelay;
    // Permission node for setting spawn
    public String setspawnPermission;
    // Permission node for reloading the plugin
    public String reloadPermission;
    // Task ID for teleportation delay task
    public int teleportTaskId;
    // Task ID for checking player movement
    public int movementCheckTaskId;

    // Map to associate players with their selected ChatColor
    private final Map<Player, ChatColor> playerColorMap = new HashMap<>();
    // GUI for color selection
    private ColorSelectionGUI colorSelectionGUI;

    @Override
    public void onEnable() {
        // Save and load the default configuration file
        saveDefaultConfig();
        reloadConfig();
        // Check if the configuration file is loaded successfully
        if (getConfig() == null) {
            getLogger().severe("Config file is missing!");
        } else {
            getLogger().info("Config file loaded successfully.");
        }

        // Registering commands and associating them with their executors
        getCommand("bsetspawn").setExecutor(new SetSpawnCommand(this)); // Command to set spawn point
        getCommand("bspawn").setExecutor(new SpawnCommand(this)); // Command to teleport to spawn point
        getCommand("spawnreload").setExecutor(new ReloadCommand(this)); // Command to reload the configuration
        getCommand("spawnmenu").setExecutor(new SpawnMenuCommand(this)); // Command to open the spawn menu

        // Load permissions and teleport delay from configuration
        reloadPermission = getConfig().getString("spawn-reload");
        teleportDelay = getConfig().getInt("teleport-delay");
        setspawnPermission = getConfig().getString("setspawnperm");

        // Initialize the color selection GUI and register its events
        colorSelectionGUI = new ColorSelectionGUI(this);
        Bukkit.getPluginManager().registerEvents(colorSelectionGUI, this);

        // Log that the plugin has been enabled
        getLogger().info("The Better Spawns Plugin is enabled");
    }

    @Override
    public void onDisable() {
        // Log that the plugin has been disabled
        getLogger().info("Better Spawns plugin has been shut down successfully");
    }

    // Method to set a player's color
    public void setPlayerColor(Player player, ChatColor color) {
        playerColorMap.put(player, color); // Store the player's color in the map
    }

    // Method to get a player's color, defaulting to white if not set
    public ChatColor getPlayerColor(Player player) {
        return playerColorMap.getOrDefault(player, ChatColor.WHITE);  // Default to white if no color is set
    }

    // Getter method for the ColorSelectionGUI
    public ColorSelectionGUI getColorSelectionGUI() {
        return colorSelectionGUI;
    }
}
